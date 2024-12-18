package com.project.domain.use_case.user

import com.project.domain.model.ChatRequest
import com.project.domain.repository.UserRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(request: ChatRequest) = userRepository.sendMessage(request)
}
