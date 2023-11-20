package com.example.pets.domain.repository

import com.example.pets.data.entities.PetEntity
import kotlinx.coroutines.flow.Flow

interface PetsRepository {

    fun getPets(): Flow<List<PetEntity>>

    suspend fun insertPet(petEntity: PetEntity)

    suspend fun deletePet(petEntity: PetEntity)

    suspend fun updatePet(petEntity: PetEntity)
}