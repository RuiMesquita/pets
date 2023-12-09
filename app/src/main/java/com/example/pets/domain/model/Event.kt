package com.example.pets.domain.model

import com.example.pets.data.entities.EventEntity
import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Int?,
    val name: String,
    val date: LocalDate,
    val time: LocalTime,
    val petId: Int
)

fun Event.toEventEntity() = EventEntity(
    id = id,
    name = name,
    date = date.toString(),
    time = time.toString(),
    petId = petId
)