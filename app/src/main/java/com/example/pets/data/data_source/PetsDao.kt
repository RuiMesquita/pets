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

    // Events table
    @Query("SELECT * FROM events_table WHERE pet_id=:id")
    fun getPetEvents(id: Int): Flow<List<EventEntity>>

    @Query("""
        SELECT * FROM events_table 
        WHERE DATE(substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2)) >= DATE('now')
        AND DATE(substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2)) <= DATE('now', '+30 days')
        ORDER BY substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2) ASC
    """)
    fun getPetEventsWithin30Days(): Flow<List<EventEntity>>

    @Query("""
        SELECT * FROM events_table
        WHERE DATE(substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2)) < DATE('now')
    """)
    fun getOverdueEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvents(events: List<EventEntity>)

    // Medication table
    @Query("SELECT * FROM medications_table WHERE pet_id=:id")
    fun getPetMedication(id: Int): Flow<List<MedicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationEntity)

    @Delete
    suspend fun deleteMedication(medication: MedicationEntity)
}