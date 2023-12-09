package com.example.pets.data.entities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.pets.common.Constants
import com.example.pets.common.Constants.DATE_FORMATTER
import com.example.pets.domain.model.Event
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "events_table",
    foreignKeys = [ForeignKey(entity = PetEntity::class, parentColumns = ["id"], childColumns = ["pet_id"], onDelete = CASCADE)]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "pet_id")
    val petId: Int
)

@RequiresApi(Build.VERSION_CODES.O)
fun EventEntity.toEvent() = Event(
    id = id,
    name = name,
    date = LocalDate.parse(date, DATE_FORMATTER),
    time = LocalTime.parse(time),
    petId = petId
)
