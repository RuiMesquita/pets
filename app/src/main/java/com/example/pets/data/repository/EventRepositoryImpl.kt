package com.example.pets.data.repository

import com.example.pets.data.data_source.EventsDao
import com.example.pets.data.entities.EventEntity
import com.example.pets.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val dao: EventsDao
): EventRepository {

    override suspend fun getPetEvents(id: Int): Flow<List<EventEntity>> {
        return dao.getPetEvents(id)
    }

    override suspend fun getPetEventsWithin30Days(): Flow<List<EventEntity>> {
        return dao.getPetEventsWithin30Days()
    }

    override suspend fun getOverdueEvents(): Flow<List<EventEntity>> {
        return dao.getOverdueEvents()
    }

    override suspend fun deleteEvents(events: List<EventEntity>) {
        return dao.deleteEvents(events)
    }

    override suspend fun insertEvent(eventEntity: EventEntity) {
        return dao.insertEvent(eventEntity)
    }

    override suspend fun deleteEvent(eventEntity: EventEntity) {
        return dao.deleteEvent(eventEntity)
    }
}