package com.example.pets.domain.repository

import com.example.pets.data.entities.MedicationEntity
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {

    fun getPetMedication(id: Int): Flow<List<MedicationEntity>>

    suspend fun insertMedication(medicationEntity: MedicationEntity)

    suspend fun deleteMedication(medicationEntity: MedicationEntity)
}