package com.example.pets.presentation.screens.addMedication

data class MedicationState(

    val petId: Int = 0,
    val name: String = "",
    val nameError: String? = null,
    val description: String = "",
    val descriptionError: String? = null

) {
    fun resetState(): MedicationState {
        return MedicationState(
            0,
            "",
            "")
    }
}


