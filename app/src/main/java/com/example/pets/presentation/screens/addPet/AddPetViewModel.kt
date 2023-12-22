package com.example.pets.presentation.screens.addPet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.PetEntity
import com.example.pets.domain.repository.PetsRepository
import com.example.pets.domain.validators.ValidateBreed
import com.example.pets.domain.validators.ValidateDateOfBirth
import com.example.pets.domain.validators.ValidateName
import com.example.pets.domain.validators.ValidateWeight
import com.example.pets.presentation.screens.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val repository: PetsRepository
): ViewModel() {
    private val validateName: ValidateName = ValidateName()
    private val validateBreed: ValidateBreed = ValidateBreed()
    private val validateWeight: ValidateWeight = ValidateWeight()
    private val validateDateOfBirth: ValidateDateOfBirth = ValidateDateOfBirth()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(AddPetState())
    val state: MutableStateFlow<AddPetState> get() = _state

    fun onEvent(event: PetEvent) {
        when(event) {
            is PetEvent.ValidatePetData -> {
                val nameResult = validateName.execute(_state.value.name)
                val breedResult = validateBreed.execute(_state.value.breed)
                val weightResult = validateWeight.execute(_state.value.weight)
                val dateResult = validateDateOfBirth.execute(_state.value.dateOfBirth)

                val hasError = listOf(nameResult, breedResult, weightResult, dateResult)
                    .any { !it.successful }

                if (hasError) {
                    _state.update { it.copy(
                        nameError = nameResult.errorMessage,
                        breedError = breedResult.errorMessage,
                        weightError = weightResult.errorMessage,
                        dateOfBirtheError = dateResult.errorMessage,
                    )}
                    return
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Success)
                }
            }
            is PetEvent.SavePet -> {
                val name = _state.value.name
                val breed = _state.value.breed
                val weight = _state.value.weight
                val specie = _state.value.species
                val gender = _state.value.gender
                val date = _state.value.dateOfBirth
                val photo = _state.value.photo

                val petEntity = PetEntity(
                    name = name,
                    breed = breed,
                    weight = weight.toFloat(),
                    specie = specie.toString(),
                    gender = gender.toString(),
                    dateOfBirth = date,
                    photo = photo.toString()
                )

                viewModelScope.launch {
                    repository.insertPet(petEntity)
                }
            }
            is PetEvent.ResetPet -> _state.update {
                it.resetState()
            }
            is PetEvent.SetBreed -> {
                _state.update { it.copy(breed = event.breed) }
            }
            is PetEvent.SetDateOfBirth -> {
                _state.update { it.copy(dateOfBirth = event.dateOfBirth) }
            }
            is PetEvent.SetGender -> {
                _state.update { it.copy(gender = event.gender) }
            }
            is PetEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
            is PetEvent.SetPhoto -> {
                _state.update { it.copy(photo = event.photo) }
            }
            is PetEvent.SetSpecie -> {
                _state.update { it.copy(species = event.specie) }
            }
            is PetEvent.SetWeight -> {
                _state.update { it.copy(weight = event.weight) }
            }
        }
    }
}
