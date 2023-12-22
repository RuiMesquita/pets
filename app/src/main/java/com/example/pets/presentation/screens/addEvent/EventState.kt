package com.example.pets.presentation.screens.addEvent

data class EventState(
    val eventName: String = "",
    val nameError: String? = null,
    val eventDate: String = "",
    val dateError: String? = null,
    val eventHour: String = "",
    val hourError: String? = null,
    val petId: Int = 0
) {
    fun resetState(): EventState {
        return EventState(
            eventName = "",
            eventDate = "",
            eventHour = "",
            petId = 0
        )
    }
}
