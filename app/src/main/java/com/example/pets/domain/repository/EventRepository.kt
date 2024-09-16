package com.example.pets.domain.repository

import com.example.pets.data.entities.EventEntity
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun getPetEvents(id: Int): Flow<List<EventEntity>>

    suspend fun getPetEventsWithin30Days(): Flow<List<EventEntity>>

    suspend fun getOverdueEvents(): Flow<List<EventEntity>>

    suspend fun deleteEvents(events: List<EventEntity>)

    suspend fun insertEvent(eventEntity: EventEntity)

    suspend fun deleteEvent(eventEntity: EventEntity)
}