package com.onopriienko.datingapp.dto

import com.onopriienko.datingapp.model.MongoUser
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class RegisterUserDto(
    @field:NotNull(message = "Name is required")
    @field:Length(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String? = null,
    @field:NotNull(message = "Date of birth is required")
    val dateOfBirth: String? = null,
    @field:NotNull(message = "City is required")
    val city: String? = null,
    @field:NotNull(message = "Phone number is required")
    val phoneNumber: String? = null,
    @field:NotNull(message = "Sex is required")
    val sex: MongoUser.Sex? = null,
    @field:NotNull(message = "Password is required")
    val passwordHash: String? = null,
    @field:NotNull(message = "\"About me\" is required")
    val about: String? = null,
    @field:NotNull(message = "Picture is required")
    val encodedPicture: String? = null,
)
