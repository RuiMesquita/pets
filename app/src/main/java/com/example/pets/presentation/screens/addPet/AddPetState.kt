package com.example.pets.presentation.screens.addPet

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pets.domain.model.enums.Gender
import com.example.pets.domain.model.enums.Species

@RequiresApi(Build.VERSION_CODES.O)
data class AddPetState(
    val name: String = "",
    val nameError: String? = null,
    val breed: String = "",
    val breedError: String? = null,
    val species: Species = Species.DOG,
    val gender: Gender = Gender.MALE,
    val weight: String = "",
    val weightError: String? = null,
    val photo: Uri? = null,
    val photoError: String? = null,
    val dateOfBirth: String = "",
    val dateOfBirtheError: String? = null
) {
    fun resetState(): AddPetState {
        return AddPetState(
            name = "",
            breed = "",
            species = Species.DOG,
            gender = Gender.MALE,
            weight = "",
            photo = null,
            dateOfBirth = ""
        )
    }
}

