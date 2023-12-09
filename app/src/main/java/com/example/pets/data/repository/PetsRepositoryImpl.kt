package com.example.pets.data.repository

import com.example.pets.data.data_source.PetsDao
import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.data.entities.PetEntity
import com.example.pets.domain.model.Medication
import com.example.pets.domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow

class PetsRepositoryImpl(
    private val dao: PetsDao
): PetsRepository {
    override fun getPets(): Flow<List<PetEntity>> {
        return dao.getPets()
    }

    override fun getSinglePet(id: Int): Flow<PetEntity> {
        return dao.getSinglePet(id)
    }

    override suspend fun insertPet(petEntity: PetEntity) {
        return dao.insertPet(petEntity)
    }

    override suspend fun deletePet(id: Int) {
        return dao.deletePet(id)
    }

    override suspend fun updatePet(petEntity: PetEntity) {
        return dao.updatePet(petEntity)
    }

    override fun getPetEvents(id: Int): Flow<List<EventEntity>> {
        return dao.getPetEvents(id)
    }

    override suspend fun insertEvent(eventEntity: EventEntity) {
        return dao.insertEvent(eventEntity)
    }

    override suspend fun deleteEvent(eventEntity: EventEntity) {
        return dao.deleteEvent(eventEntity)
    }

    override fun getPetMedication(id: Int): Flow<List<MedicationEntity>> {
        return dao.getPetMedication(id)
    }

    override suspend fun insertMedication(medication: MedicationEntity) {
        return dao.insertMedication(medication)
    }

    override suspend fun deleteMedication(medication: MedicationEntity) {
        return dao.deleteMedication(medication)
    }
}
