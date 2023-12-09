package com.example.pets.presentation.screens.add_medication

data class MedicationState(

    val petId: Int = 0,
    val name: String = "",
    val description: String = ""

) {
    fun resetState(): MedicationState {
        return MedicationState(
            0,
            "",
            "")
    }
}


