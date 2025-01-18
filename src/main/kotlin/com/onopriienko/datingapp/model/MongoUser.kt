package com.onopriienko.datingapp.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "user")
data class MongoUser(
    @Id
    val id: ObjectId? = null,
    val name: String,
    val dateOfBirth: LocalDate,
    @Indexed
    val city: String,
    @Indexed(unique = true)
    val phoneNumber: String,
    val sex: Sex,
    val passwordHash: String,
    val about: String,
    val pictureBase64: String,
) {

    enum class Sex {
        MALE, FEMALE // it lies, it is used
    }
}
