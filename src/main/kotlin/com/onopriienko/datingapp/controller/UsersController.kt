package com.onopriienko.datingapp.controller

import com.onopriienko.datingapp.dto.ReactionDto
import com.onopriienko.datingapp.dto.RegisterUserDto
import com.onopriienko.datingapp.dto.UserDto
import com.onopriienko.datingapp.mapper.UserMapper
import com.onopriienko.datingapp.model.MongoReaction
import com.onopriienko.datingapp.model.MongoUser
import com.onopriienko.datingapp.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UserService) {

    @GetMapping("/test")
    fun findAllUsers(): Mono<List<UserDto>> = userService.findAllUsers()
        .map { userList -> userList.map { UserMapper.toUserDto(it) } }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterUserDto): Mono<MongoUser> = userService.saveUser(
        MongoUser(
            name = request.name!!,
            dateOfBirth = LocalDate.parse(request.dateOfBirth!!),
            city = request.city!!,
            phoneNumber = request.phoneNumber!!,
            sex = request.sex!!,
            passwordHash = request.passwordHash!!,
            about = request.about!!,
            pictureBase64 = request.encodedPicture!!,
        )
    )

    @GetMapping("/{phoneNumber}")
    fun getByPhoneNumber(@PathVariable phoneNumber: String): Mono<UserDto> =
        userService.findByPhoneNumber(phoneNumber)
            .map { UserMapper.toUserDto(it) }

    @PostMapping("/react")
    fun react(@Valid @RequestBody request: ReactionDto): Mono<Unit> =
        userService.saveReaction(
            MongoReaction(
                fromUserId = request.fromUserId!!,
                toUserId = request.toUserId!!,
                type = request.reactionType!!,
            )
        ).thenReturn(Unit)

    @GetMapping("/{userId}/notReacted")
    fun findAllUsersToReactTo(@PathVariable userId: String): Mono<List<UserDto>> =
        userService.findUsersToReactTo(userId)
            .map { userList -> userList.map { UserMapper.toUserDto(it) } }

    @GetMapping("/{userId}/mutualLikes")
    fun findMutualLikes(@PathVariable userId: String): Mono<List<UserDto>> = userService.findMutualLikes(userId)
        .map { userList -> userList.map { UserMapper.toUserDto(it) } }
}
