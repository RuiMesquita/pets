package com.example.pets.data.repository

import com.example.pets.data.data_source.MedicationDao
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.Flow

class MedicationRepositoryImpl(
    private val dao: MedicationDao
): MedicationRepository {
    override fun getPetMedication(id: Int): Flow<List<MedicationEntity>> {
        return dao.getPetMedication(id)
    }

    override suspend fun insertMedication(medicationEntity: MedicationEntity) {
        return dao.insertMedication(medicationEntity)
    }

    override suspend fun deleteMedication(medicationEntity: MedicationEntity) {
        return dao.deleteMedication(medicationEntity)
    }
}