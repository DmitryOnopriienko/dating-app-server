package com.onopriienko.datingapp.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "reaction")
data class MongoReaction(
    @Id
    val id: ObjectId? = null,
    val fromUserId: String,
    val toUserId: String,
    val type: ReactionType,
) {
    enum class ReactionType {
        LIKE, DISLIKE
    }
}
