package com.example.pets.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.data.entities.PetEntity
import com.example.pets.domain.model.Event
import com.example.pets.domain.model.Medication
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {

    // Pets table
    @Query("SELECT * FROM pets_table ORDER BY name ASC")
    fun getPets(): Flow<List<PetEntity>>

    @Query("SELECT * FROM pets_table WHERE id=:id")
    fun getSinglePet(id: Int): Flow<PetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    @Query("DELETE FROM pets_table WHERE id=:id")
    suspend fun deletePet(id: Int)

    @Update
    suspend fun updatePet(pet: PetEntity)
}