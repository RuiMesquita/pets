package com.example.pets.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "events_table",
    foreignKeys = [ForeignKey(entity = PetEntity::class, parentColumns = ["id"], childColumns = ["pet_id"], onDelete = CASCADE)]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date_time")
    val dateTime: String,

    @ColumnInfo(name = "pet_id")
    val petId: Int
)
