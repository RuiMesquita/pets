package com.example.pets.presentation.screens.add_pet

import android.net.Uri
import com.example.pets.domain.enums.Gender
import com.example.pets.domain.enums.Species
import java.time.LocalDate

sealed interface PetEvent {
    object SavePet: PetEvent
    data class SetName(val name: String): PetEvent
    data class SetSpecie(val specie: Species): PetEvent
    data class SetGender(val gender: Gender): PetEvent
    data class SetDateOfBirth(val dateOfBirth: String): PetEvent
    data class SetBreed(val breed: String): PetEvent
    data class SetPhoto(val photo: Uri?): PetEvent
    data class SetWeight(val weight: String): PetEvent
}
