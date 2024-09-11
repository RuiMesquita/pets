package com.example.pets.domain.repository

import com.example.pets.data.entities.EventEntity
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getPetEvents(id: Int): Flow<List<EventEntity>>

    fun getPetEventsWithin30Days(): Flow<List<EventEntity>>

    fun getOverdueEvents(): Flow<List<EventEntity>>

    suspend fun deleteEvents(events: List<EventEntity>)

    suspend fun insertEvent(eventEntity: EventEntity)

    suspend fun deleteEvent(eventEntity: EventEntity)
}