package com.example.pets.data.repository

import com.example.pets.data.data_source.PetsDao
import com.example.pets.data.entities.PetEntity
import com.example.pets.domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow

class PetsRepositoryImpl(
    private val dao: PetsDao
): PetsRepository {
    override fun getPets(): Flow<List<PetEntity>> {
        return dao.getPets()
    }

    override suspend fun insertPet(petEntity: PetEntity) {
        return dao.insertPet(petEntity)
    }

    override suspend fun deletePet(petEntity: PetEntity) {
        return dao.deletePet(petEntity)
    }

    override suspend fun updatePet(petEntity: PetEntity) {
        return dao.updatePet(petEntity)
    }
}
