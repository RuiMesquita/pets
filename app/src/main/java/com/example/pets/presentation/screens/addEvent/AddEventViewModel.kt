package com.example.pets.presentation.screens.addEvent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.EventEntity
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: PetsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state =  MutableStateFlow(EventState())
    val state: MutableStateFlow<EventState> get() = _state

    init {
        savedStateHandle.get<Int>("id")?.let {id ->
            _state.update { it.copy(petId = id) }
        }
    }

    fun onEvent(event: EventEvent) {
        when(event) {
            is EventEvent.SaveEvent -> {
                val eventName = _state.value.eventName
                val eventDate = _state.value.eventDate
                val eventHour = _state.value.eventHour
                val petId = _state.value.petId

                // TODO add validations

                val eventEntity = EventEntity(
                    name = eventName,
                    date = eventDate,
                    time = eventHour,
                    petId = petId
                )

                viewModelScope.launch {
                    repository.insertEvent(eventEntity)
                }
            }
            is EventEvent.ResetEvent -> {
                _state.update { it.resetState() }
            }
            is EventEvent.SetEventDate -> {
                _state.update { it.copy(eventDate = event.eventDate) }
            }
            is EventEvent.SetEventHour -> {
                _state.update { it.copy(eventHour = event.eventHour) }
            }
            is EventEvent.SetNameEvent -> {
                _state.update { it.copy(eventName = event.eventName) }
            }
        }
    }
}