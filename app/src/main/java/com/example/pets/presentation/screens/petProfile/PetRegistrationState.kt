package com.example.pets.presentation.screens.petProfile

import android.net.Uri
import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.domain.enums.Gender
import com.example.pets.domain.model.Event
import com.example.pets.domain.model.Medication

data class PetRegistrationState (
    val isLoading: Boolean = false,
    val error: String = "",
    val id: Int = 0,
    val photo: Uri? = null,
    val name: String = "",
    val age: Int = 0,
    val gender: Gender = Gender.MALE,
    val weight: Float = 0f,
    val events: List<Event> = emptyList(),
    val medications: List<Medication> = emptyList(),

    // delete items
    val eventToDelete: EventEntity? = null,
    val medicationToDelete: MedicationEntity? = null
)