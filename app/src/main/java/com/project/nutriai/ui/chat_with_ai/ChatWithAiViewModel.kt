package com.project.nutriai.ui.chat_with_ai

import androidx.lifecycle.viewModelScope
import com.project.domain.model.ChatRequest
import com.project.domain.use_case.user.SendMessageUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ChatWithAiViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase
) : BaseViewModel() {

//    private val generativeModel = GenerativeModel(
//        modelName = "gemini-1.5-flash",
//        apiKey = GEMINI_API_KEY,
//        safetySettings = listOf(
//            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.ONLY_HIGH),
//            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH),
//            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.ONLY_HIGH),
//            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.ONLY_HIGH)
//        )
//    )

//    private val chat: Chat = generativeModel.startChat()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _conversation = MutableStateFlow(mutableListOf<Message>())
    val conversation = _conversation.asStateFlow()

    private val chatHistory = mutableListOf<String>()

    fun sendMessage(message: String) = viewModelScope.launch {
        _loading.emit(true)
        addUserMessage(message)
        try {
            val response = sendMessageUseCase(
                ChatRequest(
                    chatHistory = chatHistory,
                    question = message
                )
            )
            replaceLastMessage(
                Message(
                    content = response.answer,
                    isSentByUser = false,
                    status = MessageStatus.SENT
                )
            )
            addMessagesToChatHistory(message, response.answer)
        } catch (e: Exception) {
            delay(1.seconds)
            replaceLastMessage(
                Message(
                    content = "",
                    isSentByUser = false,
                    status = MessageStatus.FAILED
                )
            )
        }
        _loading.emit(false)
    }

    private fun addMessagesToChatHistory(userMessage: String, assistantMessage: String) {
        chatHistory.add("User: $userMessage")
        chatHistory.add("Assistant: $assistantMessage")

        if (chatHistory.size > 10) {
            chatHistory.removeAt(0)
            chatHistory.removeAt(0)
        }
    }

//    fun sendMessage(message: String) = viewModelScope.launch {
//        _loading.emit(true)
//        addUserMessage(message)
//        try {
//            val response = chat.sendMessage(message)
//            replaceLastMessage(
//                Message(
//                    content = response.text!!,
//                    isSentByUser = false,
//                    status = MessageStatus.SENT
//                )
//            )
//        } catch (e: Exception) {
//            delay(1.seconds)
//            replaceLastMessage(
//                Message(
//                    content = "",
//                    isSentByUser = false,
//                    status = MessageStatus.FAILED
//                )
//            )
//        }
//        _loading.emit(false)
//    }

    private fun addUserMessage(message: String) {
        val userMessage =
            Message(content = message, isSentByUser = true, status = MessageStatus.SENT)
        val loadingMessage =
            Message(content = "", isSentByUser = false, status = MessageStatus.SENDING)
        _conversation.getAndUpdate { currentList ->
            (currentList + userMessage + loadingMessage).toMutableList()
        }
    }

    private fun replaceLastMessage(message: Message) {
        _conversation.getAndUpdate { currentList ->
            (currentList.dropLast(1) + message).toMutableList()
        }
    }
}
