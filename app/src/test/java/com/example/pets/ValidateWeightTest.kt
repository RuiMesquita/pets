package com.example.pets

import com.example.pets.domain.validators.ValidateWeight
import com.example.pets.domain.validators.ValidationResult
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidateWeightTest {

    private val validator: ValidateWeight = ValidateWeight()

    @Test
    fun validWeight() {
        // arrange
        val expected = ValidationResult(true)

        // act
        val result = validator.execute("12")

        // assert
        assertEquals(expected, result)
    }

    @Test
    fun invalidWeight() {
        // arrange
        val expected = ValidationResult(false, "Invalid weight value")

        // act
        val result = validator.execute("asdf")

        // assert
        assertEquals(expected, result)
    }

    @Test
    fun emptyWeight() {
        // arrange
        val expected = ValidationResult(false, "Weight field can't be empty")

        // act
        val result = validator.execute("")

        // assert
        assertEquals(expected, result)
    }
}