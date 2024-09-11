package com.example.pets.presentation.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.toEvent
import com.example.pets.domain.model.Event
import com.example.pets.domain.repository.EventRepository
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val petRepository: PetsRepository,
    private val eventRepository: EventRepository
): ViewModel() {
    private val _calendarEvent = MutableStateFlow<List<CalendarEvent>>(emptyList())
    val calendarEvent: MutableStateFlow<List<CalendarEvent>> get() = _calendarEvent

    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        viewModelScope.launch {
            eventRepository.getPetEventsWithin30Days()
                .flowOn(Dispatchers.IO)
                .collect{ events ->
                    val calendarEvents = events.map { event ->
                        async {
                            val photo = getPetPhoto(event.petId)
                            event.toEvent().toCalendarEvent(photo)
                        }
                    }.awaitAll()

                    _calendarEvent.value = calendarEvents
                }
        }
    }

    private suspend fun getPetPhoto(petId: Int): String? {
        return petRepository.getSinglePet(petId)
            .flowOn(Dispatchers.IO)
            .first().photo
    }

    private fun Event.toCalendarEvent(petPhoto: String?): CalendarEvent {
        return CalendarEvent(
            photo = petPhoto,
            eventName = this.name,
            date = this.date,
            time = this.time
        )
    }

    data class CalendarEvent(
        val photo: String?,
        val eventName: String,
        val date: LocalDate,
        val time: LocalTime
    )
}