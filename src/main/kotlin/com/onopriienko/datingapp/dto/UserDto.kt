package com.onopriienko.datingapp.dto

import com.onopriienko.datingapp.model.MongoUser.Sex
import java.time.LocalDate

data class UserDto(
    val id: String? = null,
    val name: String,
    val dateOfBirth: LocalDate,
    val city: String,
    val phoneNumber: String,
    val sex: Sex,
    val about: String,
    val pictureBase64: String,
)
