package com.onopriienko.datingapp.dto

import com.onopriienko.datingapp.model.MongoReaction.ReactionType
import jakarta.validation.constraints.NotNull

data class ReactionDto(
    @field:NotNull
    val fromUserId: String? = null,
    @field:NotNull
    val toUserId: String? = null,
    @field:NotNull
    val reactionType: ReactionType? = null,
)
