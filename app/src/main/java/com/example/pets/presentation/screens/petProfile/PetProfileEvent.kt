package com.example.pets.presentation.screens.petProfile

import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity

sealed interface PetProfileEvent {

    object DeletePet: PetProfileEvent

    object DeleteEvent: PetProfileEvent

    object DeleteMedication: PetProfileEvent

    data class SetDeleteEventItem(val eventEntity: EventEntity): PetProfileEvent

    data class SetDeleteMedicationItem(val medicationEntity: MedicationEntity): PetProfileEvent
}