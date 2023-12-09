package com.example.pets.mappers

import com.example.pets.common.Constants
import com.example.pets.data.entities.PetEntity
import com.example.pets.data.entities.toPet
import com.example.pets.domain.enums.Gender
import com.example.pets.domain.enums.Species
import com.example.pets.domain.model.Pet
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class PetMapperTest {

    @Test
    fun pet_toEntity() {

    }

    @Test
    fun entity_toPet() {
        // Arrange
        val entity = PetEntity(
            id = 1,
            name = "Alex",
            specie = Species.CAT.toString(),
            gender = Gender.MALE.toString(),
            dateOfBirth = "12/12/2020",
            breed = "Gato",
            photo = null,
            weight = 12f,
        )

        val expectedPet = Pet(
            id = 1,
            name = "Alex",
            specie = Species.CAT,
            gender = Gender.MALE,
            dateOfBirth = LocalDate.parse("12/12/2020", Constants.DATE_FORMATTER),
            breed = "Gato",
            photo = null,
            weight = 12f,
            medications = emptyList(),
            events = emptyList()
        )

        // Act
        val result = entity.toPet()

        // Assert
        assert(result == expectedPet)
    }
}