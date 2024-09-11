package com.example.pets.data.repository

import com.example.pets.data.data_source.PetsDao
import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.data.entities.PetEntity
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
}
