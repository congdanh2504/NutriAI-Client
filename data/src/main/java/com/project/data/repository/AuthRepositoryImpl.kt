package com.project.data.repository

import com.project.data.source.remote.AppApi
import com.project.data.source.remote.dto.LoginRequest
import com.project.data.source.remote.dto.LoginResponse
import com.project.data.source.remote.dto.RegisterRequest
import com.project.data.source.remote.dto.UserDetailNetwork
import com.project.data.source.remote.dto.UserResponse
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

    override suspend fun login(email: String, password: String): LoginResponse {
        return appApi.login(LoginRequest(email, password))
    }

    override suspend fun getCurrentUser(): UserResponse {
        return appApi.getCurrentUser()
    }

    override suspend fun updateUserDetail(userDetailNetwork: UserDetailNetwork) {
        return appApi.updateUserDetail(userDetailNetwork)
    }

    override suspend fun getUserDetail(): UserDetailNetwork {
        return appApi.getUserDetail()
    }
}