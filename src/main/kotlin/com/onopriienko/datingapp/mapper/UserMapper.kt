package com.onopriienko.datingapp.mapper

import com.onopriienko.datingapp.dto.UserDto
import com.onopriienko.datingapp.model.MongoUser

object UserMapper {

    fun toUserDto(mongoUser: MongoUser): UserDto = UserDto(
        id = mongoUser.id?.toHexString(),
        name = mongoUser.name,
        dateOfBirth = mongoUser.dateOfBirth,
        city = mongoUser.city,
        phoneNumber = mongoUser.phoneNumber,
        sex = mongoUser.sex,
        about = mongoUser.about,
        pictureBase64 = mongoUser.pictureBase64,
    )
}
