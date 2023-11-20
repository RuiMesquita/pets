package com.example.pets.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "medications_table",
    foreignKeys = [ForeignKey(entity = PetEntity::class, parentColumns = ["id"], childColumns = ["pet_id"], onDelete = CASCADE)]
)
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "pet_id")
    val petId: Int
)
