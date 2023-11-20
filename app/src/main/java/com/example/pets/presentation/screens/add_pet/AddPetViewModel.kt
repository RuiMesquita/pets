package com.example.pets.presentation.screens.add_pet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.PetEntity
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val repository: PetsRepository
): ViewModel() {

    private val _state = MutableStateFlow(PetState())
    val state: MutableStateFlow<PetState> get() = _state


    fun onEvent(event: PetEvent) {
        when(event) {
            is PetEvent.SavePet -> {
                val name = _state.value.name
                val breed = _state.value.breed
                val weight = _state.value.weight
                val specie = _state.value.species
                val gender = _state.value.gender
                val date = _state.value.dateOfBirth
                val photo = _state.value.photo

                // TODO add validations to not allow invalid fields

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