package com.example.pets.presentation.screens.addMedication

sealed interface MedicationEvent {

    object SaveMedication: MedicationEvent
    object ValidateMedication: MedicationEvent

    object ResetMedication: MedicationEvent

    data class SetName(val name: String): MedicationEvent

    data class SetDescription(val description: String): MedicationEvent
}