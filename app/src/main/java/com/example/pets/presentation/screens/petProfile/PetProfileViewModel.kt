package com.example.pets.presentation.screens.petProfile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.toEvent
import com.example.pets.data.entities.toMedication
import com.example.pets.data.entities.toPet
import com.example.pets.domain.repository.EventRepository
import com.example.pets.domain.repository.MedicationRepository
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
    private val petRepository: PetsRepository,
    private val medicationRepository: MedicationRepository,
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _petRegistrationState = MutableStateFlow(PetRegistrationState())
    val petRegistrationState: MutableStateFlow<PetRegistrationState> get() = _petRegistrationState

    init {
        savedStateHandle.get<Int>("id")?.let {id ->
            _petRegistrationState.update { it.copy(id = id) }
            getPetInfo(id)

        }
    }

    private fun getPetInfo(id: Int) {
        viewModelScope.launch {
            petRepository.getSinglePet(id)
                .flowOn(Dispatchers.IO)
                .collect { entity ->
                    val pet = entity.toPet()
                    _petRegistrationState.update { petState ->
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
                    petRepository.deletePet(_petRegistrationState.value.id)
                }
            }

            is PetProfileEvent.DeleteEvent -> {
                viewModelScope.launch {
                    _petRegistrationState.value.eventToDelete?.let { eventRepository.deleteEvent(it) }
                }
            }

            is PetProfileEvent.DeleteMedication -> {
                viewModelScope.launch {
                    _petRegistrationState.value.medicationToDelete?.let { medicationRepository.deleteMedication(it) }
                }
            }

            is PetProfileEvent.SetDeleteEventItem -> {
                _petRegistrationState.update { it.copy(eventToDelete = event.eventEntity) }
            }

            is PetProfileEvent.SetDeleteMedicationItem -> {
                _petRegistrationState.update { it.copy(medicationToDelete = event.medicationEntity) }
            }
        }
    }


    private suspend fun getPetMedication(petId: Int) {
        medicationRepository.getPetMedication(petId)
            .flowOn(Dispatchers.IO)
            .collect { medication ->
                val medicationList = medication.map { medicationEntity -> medicationEntity.toMedication() }
                _petRegistrationState.update { it.copy(medications = medicationList) }
            }
    }

    private suspend fun getPetEvents(petId: Int) {
        eventRepository.getPetEvents(petId)
            .flowOn(Dispatchers.IO)
            .collect {entities ->
                val events = entities.map { eventEntity -> eventEntity.toEvent() }
                _petRegistrationState.update { it.copy(events = events) }
            }
    }
}