package com.example.pets.domain.model

import android.net.Uri
import com.example.pets.data.entities.PetEntity
import com.example.pets.domain.model.enums.Gender
import com.example.pets.domain.model.enums.Species
import java.time.LocalDate

data class Pet(
    val id: Int?,
    val name: String,
    val specie: Species,
    val gender: Gender,
    val dateOfBirth: LocalDate,
    val breed: String?,
    val photo: Uri?,
    val weight: Float,
    val medications: List<Medication>,
    val events: List<Event>
) {
    fun doesMatchSearchCriteria(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            specie.toString(),
            gender.toString(),
            "$breed"

        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

fun Pet.toPetEntity() = PetEntity(
    id = null,
    name = name,
    specie = specie.toString(),
    gender = gender.toString(),
    dateOfBirth = dateOfBirth.toString(),
    breed = breed,
    photo = photo.toString(),
    weight = weight
)
