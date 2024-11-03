package com.project.domain.use_case.auth

import com.project.data.repository.AuthRepository
import com.project.domain.model.User
import com.project.domain.model.toUserDetail
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): User {
        return authRepository.getCurrentUser().toUserDetail()
    }
}