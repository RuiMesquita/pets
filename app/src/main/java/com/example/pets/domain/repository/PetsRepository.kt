package com.example.pets.domain.repository

import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.data.entities.PetEntity
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    fun getPets(): Flow<List<PetEntity>>

    fun getSinglePet(id: Int): Flow<PetEntity>

    suspend fun insertPet(petEntity: PetEntity)

    suspend fun deletePet(id: Int)

    suspend fun updatePet(petEntity: PetEntity)

    // Events table
    fun getPetEvents(id: Int): Flow<List<EventEntity>>

    fun getPetEventsWithin30Days(): Flow<List<EventEntity>>

    fun getOverdueEvents(): Flow<List<EventEntity>>

    suspend fun deleteEvents(events: List<EventEntity>)

    suspend fun insertEvent(eventEntity: EventEntity)

    suspend fun deleteEvent(eventEntity: EventEntity)

    // Medication table
    fun getPetMedication(id: Int): Flow<List<MedicationEntity>>

    suspend fun insertMedication(medicationEntity: MedicationEntity)

    suspend fun deleteMedication(medicationEntity: MedicationEntity)
}