package com.example.pets.presentation.screens.add_pet

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pets.domain.enums.Gender
import com.example.pets.domain.enums.Species

@RequiresApi(Build.VERSION_CODES.O)
data class AddPetState(
    val name: String = "",
    val breed: String = "",
    val species: Species = Species.DOG,
    val gender: Gender = Gender.MALE,
    val weight: String = "",
    val photo: Uri? = null,
    val dateOfBirth: String = ""
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

