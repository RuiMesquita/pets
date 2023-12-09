package com.example.pets.presentation.screens.add_event

data class EventState(
    val eventName: String = "",
    val eventDate: String = "",
    val eventHour: String = "",
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
