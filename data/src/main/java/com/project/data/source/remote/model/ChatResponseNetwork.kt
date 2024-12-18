package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.project.domain.model.ChatResponse

data class ChatResponseNetwork(
    @SerializedName("response")
    val answer: String
)

fun ChatResponseNetwork.toDomain() = ChatResponse(
    answer = answer
)
