package com.example.pets.presentation.screens.addEvent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.EventEntity
import com.example.pets.domain.repository.PetsRepository
import com.example.pets.domain.validators.ValidateEventDate
import com.example.pets.domain.validators.ValidateHour
import com.example.pets.domain.validators.ValidateName
import com.example.pets.presentation.screens.ValidationEvent
import com.example.pets.presentation.screens.addPet.AddPetViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: PetsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val validateName: ValidateName = ValidateName()
    private val validateHour: ValidateHour = ValidateHour()
    private val validateEventDate: ValidateEventDate = ValidateEventDate()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state =  MutableStateFlow(EventState())
    val state: MutableStateFlow<EventState> get() = _state

    init {
        savedStateHandle.get<Int>("id")?.let {id ->
            _state.update { it.copy(petId = id) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: EventEvent) {
        when(event) {
            is EventEvent.ValidateEvent -> {
                val eventNameResult = validateName.execute(state.value.eventName)
                val eventDateResult = validateEventDate.execute(state.value.eventDate)
                val eventHourResult = validateHour.execute(state.value.eventHour)

                val hasError = listOf(
                    eventDateResult,
                    eventHourResult,
                    eventNameResult
                ).any { !it.successful }

                if (hasError) {
                    _state.update {it.copy(
                        nameError = eventNameResult.errorMessage,
                        hourError = eventHourResult.errorMessage,
                        dateError = eventDateResult.errorMessage
                    )}
                    return
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Success)
                }
            }
            is EventEvent.SaveEvent -> {
                val eventName = _state.value.eventName
                val eventDate = _state.value.eventDate
                val eventHour = _state.value.eventHour
                val petId = _state.value.petId

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