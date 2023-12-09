package com.example.pets.presentation.screens.add_event

sealed interface EventEvent {
    object SaveEvent: EventEvent
    object ResetEvent: EventEvent
    data class SetNameEvent(val eventName: String): EventEvent
    data class SetEventDate(val eventDate: String): EventEvent
    data class SetEventHour(val eventHour: String): EventEvent
}