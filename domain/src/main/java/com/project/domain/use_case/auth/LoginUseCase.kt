package com.project.domain.use_case.auth

import com.project.domain.model.LoginResponse
import com.project.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): LoginResponse {
        return authRepository.login(email, password)
    }
}