package com.example.pets.presentation.screens.addMedication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.domain.repository.PetsRepository
import com.example.pets.domain.validators.ValidateDescription
import com.example.pets.domain.validators.ValidateName
import com.example.pets.presentation.screens.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicationViewModel @Inject constructor(
    private val repository: PetsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val validateName = ValidateName()
    private val validateDescription = ValidateDescription()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(MedicationState())
    val state: MutableStateFlow<MedicationState> get() = _state

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            _state.update { it.copy(petId = id) }
        }
    }

    fun onEvent(event: MedicationEvent) {
        when(event) {
            MedicationEvent.ResetMedication -> {
                _state.update { it.resetState() }
            }
            MedicationEvent.ValidateMedication -> {
                val nameResult = validateName.execute(state.value.name)
                val descriptionResult = validateDescription.execute(state.value.description)

                val hasError = listOf(
                    nameResult,
                    descriptionResult
                ).any { !it.successful }

                if (hasError) {
                    _state.update { it.copy(
                        nameError = nameResult.errorMessage,
                        descriptionError = descriptionResult.errorMessage
                    ) }
                    return
                }

                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Success)
                }
            }
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