package com.example.pets.presentation.screens.pet_profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.toEvent
import com.example.pets.data.entities.toMedication
import com.example.pets.data.entities.toPet
import com.example.pets.domain.repository.PetsRepository
import com.example.pets.utils.DateUtils.Companion.calculateAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class PetProfileViewModel @Inject constructor(
    private val repository: PetsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _petState = MutableStateFlow(PetState())
    val petState: MutableStateFlow<PetState> get() = _petState

    init {
        savedStateHandle.get<Int>("id")?.let {id ->
            _petState.update { it.copy(id = id) }
            getPetInfo(id)

        }
    }

    private fun getPetInfo(id: Int) {
        viewModelScope.launch {
            repository.getSinglePet(id)
                .flowOn(Dispatchers.IO)
                .collect { entity ->
                    val pet = entity.toPet()
                    _petState.update { petState ->
                        petState.copy(
                            photo = pet.photo,
                            name = pet.name,
                            gender = pet.gender,
                            weight = pet.weight,
                            age = calculateAge(pet.dateOfBirth)
                        )
                    }

                    val medicationDeferred = async { getPetMedication(id) }
                    val eventsDeferred = async { getPetEvents(id) }

                    medicationDeferred.await()
                    eventsDeferred.await()
                }
        }
    }

    fun onEvent(event: PetProfileEvent) {
        when(event) {
            is PetProfileEvent.DeletePet -> {
                viewModelScope.launch {
                    repository.deletePet(_petState.value.id)
                }
            }

            is PetProfileEvent.DeleteEvent -> {
                viewModelScope.launch {
                    _petState.value.eventToDelete?.let { repository.deleteEvent(it) }
                }
            }

            is PetProfileEvent.DeleteMedication -> {
                viewModelScope.launch {
                    _petState.value.medicationToDelete?.let { repository.deleteMedication(it) }
                }
            }

            is PetProfileEvent.SetDeleteEventItem -> {
                _petState.update { it.copy(eventToDelete = event.eventEntity) }
            }

            is PetProfileEvent.SetDeleteMedicationItem -> {
                _petState.update { it.copy(medicationToDelete = event.medicationEntity) }
            }
        }
    }


    private suspend fun getPetMedication(petId: Int) {
        repository.getPetMedication(petId)
            .flowOn(Dispatchers.IO)
            .collect { medication ->
                val medicationList = medication.map { medicationEntity -> medicationEntity.toMedication() }
                _petState.update { it.copy(medications = medicationList) }
            }
    }

    private suspend fun getPetEvents(petId: Int) {
        repository.getPetEvents(petId)
            .flowOn(Dispatchers.IO)
            .collect {entities ->
                val events = entities.map { eventEntity -> eventEntity.toEvent() }
                _petState.update { it.copy(events = events) }
            }
    }
}