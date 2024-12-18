package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.project.domain.model.ChatRequest

data class ChatRequestNetwork(
    @SerializedName("chat_history")
    val chatHistory: List<String>,
    @SerializedName("question")
    val question: String
)

fun ChatRequest.toNetwork() = ChatRequestNetwork(
    chatHistory = chatHistory,
    question = question
)
