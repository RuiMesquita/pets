package com.example.pets.domain.model

import com.example.pets.data.entities.MedicationEntity

data class Medication(
    val id: Int?,
    val name: String,
    val description: String,
    val petId: Int
)

fun Medication.toMedicationEntity() = MedicationEntity(
    id = id,
    name = name,
    description = description,
    petId = petId
)