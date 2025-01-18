package com.onopriienko.datingapp.service

import com.onopriienko.datingapp.model.MongoReaction
import com.onopriienko.datingapp.model.MongoUser
import com.onopriienko.datingapp.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(private val userRepository: UserRepository) {

    fun saveUser(user: MongoUser): Mono<MongoUser> = userRepository.save(user)

    fun findAllUsers(): Mono<List<MongoUser>> = userRepository.findAll()

    fun findByPhoneNumber(phoneNumber: String): Mono<MongoUser> = userRepository.findByPhoneNumber(phoneNumber)

    fun saveReaction(reaction: MongoReaction): Mono<MongoReaction> = userRepository.saveReaction(reaction)

    fun findUsersToReactTo(userId: String): Mono<List<MongoUser>> =
        userRepository.findUsersToReactTo(userId)

    fun findMutualLikes(userId: String): Mono<List<MongoUser>> = userRepository.findMutualLikes(userId)
}
