package com.onopriienko.datingapp.dto

import jakarta.validation.constraints.NotNull

data class LoginDto(
    @field:NotNull(message = "Phone number is required")
    val phoneNumber: String? = null,
    @field:NotNull(message = "Password is required")
    val passwordHash: String? = null,
)
