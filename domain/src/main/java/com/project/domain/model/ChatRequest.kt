package com.project.domain.model

data class ChatRequest(
    val chatHistory: List<String>,
    val question: String
)
