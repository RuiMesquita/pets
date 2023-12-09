package com.example.pets.presentation.screens.add_medication

sealed interface MedicationEvent {

    object SaveMedication: MedicationEvent

    data class SetName(val name: String): MedicationEvent

    data class SetDescription(val description: String): MedicationEvent
}