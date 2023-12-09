package com.example.pets.presentation.screens.add_medication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicationViewModel @Inject constructor(
    private val repository: PetsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(MedicationState())
    val state: MutableStateFlow<MedicationState> get() = _state

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            _state.update { it.copy(petId = id) }
        }
    }

    fun onEvent(event: MedicationEvent) {
        when(event) {
            is MedicationEvent.SaveMedication -> {
                val medicationName = _state.value.name
                val medicationDescription = _state.value.description
                val petId = _state.value.petId

                // TODO add validations

                val medicationEntity = MedicationEntity(
                    name = medicationName,
                    description = medicationDescription,
                    petId = petId
                )

                viewModelScope.launch {
                    repository.insertMedication(medicationEntity)
                }
            }
            is MedicationEvent.SetDescription -> {
                _state.update { it.copy(description = event.description) }
            }
            is MedicationEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}