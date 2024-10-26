package com.project.domain.use_case

import com.project.data.repository.AuthRepository
import com.project.data.source.remote.dto.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): LoginResponse {
        return authRepository.login(email, password)
    }
}