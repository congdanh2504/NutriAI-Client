package com.project.nutriai.ui.chat_with_ai

data class Message(
    val id: Long = System.currentTimeMillis(),
    val content: String,
    val isSentByUser: Boolean,
    val status: MessageStatus,
)

enum class MessageStatus {
    SENDING,
    SENT,
    FAILED
}
