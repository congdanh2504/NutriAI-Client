package com.project.domain.use_case.auth

import com.project.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, username: String, password: String): Boolean {
        return authRepository.register(email, username, password)
    }
}