package com.example.pets.domain.repository

import com.example.pets.data.entities.PetEntity
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    suspend fun getPets(): Flow<List<PetEntity>>

    suspend fun getSinglePet(id: Int): Flow<PetEntity>

    suspend fun insertPet(petEntity: PetEntity)

    suspend fun deletePet(id: Int)

    suspend fun updatePet(petEntity: PetEntity)
}