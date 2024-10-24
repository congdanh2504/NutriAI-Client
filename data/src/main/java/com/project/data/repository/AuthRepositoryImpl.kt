package com.project.data.repository

import com.project.data.source.remote.AppApi
import com.project.data.source.remote.dto.LoginRequest
import com.project.data.source.remote.dto.RegisterRequest
import com.project.data.source.remote.dto.TokenResponse
import com.project.data.source.remote.dto.UserDetailResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : AuthRepository {
    override suspend fun register(email: String, username: String, password: String): Boolean {
        try {
            appApi.register(RegisterRequest(email, username, password))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun login(email: String, password: String): TokenResponse {
        return appApi.login(LoginRequest(email, password))
    }

    override suspend fun getCurrentUser(): UserDetailResponse {
        return appApi.getCurrentUser()
    }
}