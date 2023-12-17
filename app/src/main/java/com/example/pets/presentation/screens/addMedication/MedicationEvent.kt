package com.example.pets.presentation.screens.addMedication

sealed interface MedicationEvent {

    object SaveMedication: MedicationEvent

    data class SetName(val name: String): MedicationEvent

    data class SetDescription(val description: String): MedicationEvent
}