package com.onopriienko.datingapp.repository

import com.onopriienko.datingapp.model.MongoReaction
import com.onopriienko.datingapp.model.MongoReaction.ReactionType.DISLIKE
import com.onopriienko.datingapp.model.MongoReaction.ReactionType.LIKE
import com.onopriienko.datingapp.model.MongoUser
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

@Repository
class UserRepository(private val mongoTemplate: ReactiveMongoTemplate) {

    fun save(user: MongoUser): Mono<MongoUser> = mongoTemplate.save(user)

    fun findAll(): Mono<List<MongoUser>> = mongoTemplate.findAll(MongoUser::class.java).collectList()

    fun findByPhoneNumber(phoneNumber: String): Mono<MongoUser> =
        mongoTemplate.findOne(
            Query(Criteria.where(MongoUser::phoneNumber.name).`is`(phoneNumber)),
            MongoUser::class.java
        )

    fun saveReaction(reaction: MongoReaction): Mono<MongoReaction> = mongoTemplate.save(reaction)

    @Transactional
    fun findUsersToReactTo(userId: String): Mono<List<MongoUser>> {
        val usersToExclude = Flux.merge(
            mongoTemplate.find(
                Query(Criteria.where(MongoReaction::fromUserId.name).`is`(userId)),
                MongoReaction::class.java,
            ).mapNotNull { ObjectId(it.toUserId) },
            mongoTemplate.find(
                Query(
                    Criteria.where(MongoReaction::toUserId.name).`is`(userId)
                        .and(MongoReaction::type.name).`is`(DISLIKE)
                ),
                MongoReaction::class.java,
            ).mapNotNull { ObjectId(it.fromUserId) },
        ).collectList().map { it.toSet() }

        return Mono.zip(
            mongoTemplate.findById(ObjectId(userId), MongoUser::class.java),
            usersToExclude,
        ).flatMap { (user, usersToExclude) ->
            mongoTemplate.find(
                Query(
                    Criteria.where(MongoUser::city.name).`is`(user.city)
                        .and(MongoUser::id.name).nin(usersToExclude)
                        .and(MongoUser::sex.name).ne(user.sex)
                ),
                MongoUser::class.java,
            ).collectList()
        }
    }

    @Transactional
    fun findMutualLikes(userId: String): Mono<List<MongoUser>> =
        mongoTemplate.find(
            Query(
                Criteria.where(MongoReaction::fromUserId.name).`is`(userId)
                    .and(MongoReaction::type.name).`is`(LIKE)
            ),
            MongoReaction::class.java,
        )
            .mapNotNull { it.toUserId }
            .collectList()
            .flatMapMany { likedUsers ->
                mongoTemplate.find(
                    Query(
                        Criteria.where(MongoReaction::toUserId.name).`is`(userId)
                            .and(MongoReaction::fromUserId.name).`in`(likedUsers)
                            .and(MongoReaction::type.name).`is`(LIKE)
                    ),
                    MongoReaction::class.java,
                ).mapNotNull { ObjectId(it.fromUserId) }
            }
            .collectList()
            .flatMap { mutuallyLiked ->
                mongoTemplate.find(
                    Query(Criteria.where(MongoUser::id.name).`in`(mutuallyLiked)),
                    MongoUser::class.java,
                ).collectList()
            }
}
