package com.example.pets.data.entities

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pets.common.Constants.DATE_FORMATTER
import com.example.pets.domain.model.enums.Gender
import com.example.pets.domain.model.enums.Species
import com.example.pets.domain.model.Pet
import java.time.LocalDate

@Entity(tableName = "pets_table")
data class PetEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "species")
    val specie: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,

    @ColumnInfo(name = "breed")
    val breed: String?,

    @ColumnInfo(name = "photo")
    val photo: String?,

    @ColumnInfo(name =  "weight")
    val weight: Float,
)


@RequiresApi(Build.VERSION_CODES.O)
fun PetEntity.toPet() = Pet(
    id = id,
    name = name,
    specie = Species.valueOf(specie),
    gender = Gender.valueOf(gender),
    dateOfBirth = LocalDate.parse(dateOfBirth, DATE_FORMATTER),
    breed = breed,
    photo = Uri.parse(photo),
    weight = weight,
    medications = emptyList(),
    events = emptyList()
)