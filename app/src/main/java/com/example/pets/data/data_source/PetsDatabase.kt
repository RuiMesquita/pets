package com.example.pets.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pets.data.entities.EventEntity
import com.example.pets.data.entities.MedicationEntity
import com.example.pets.data.entities.PetEntity


@Database(
    entities = [PetEntity::class, EventEntity::class, MedicationEntity::class],
    version = 5
)
abstract class PetsDatabase: RoomDatabase() {
    abstract fun petsDao(): PetsDao
    abstract fun eventsDao(): EventsDao
    abstract fun medicationDao(): MedicationDao
}