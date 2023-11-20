package com.example.pets.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pets.data.entities.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {

    @Query("SELECT * FROM pets_table ORDER BY name ASC")
    fun getPets(): Flow<List<PetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    @Delete
    suspend fun deletePet(pet: PetEntity)

    @Update
    suspend fun updatePet(pet: PetEntity)
}