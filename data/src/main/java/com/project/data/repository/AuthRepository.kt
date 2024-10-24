package com.project.data.repository

import com.project.data.source.remote.dto.TokenResponse
import com.project.data.source.remote.dto.UserDetailResponse

interface AuthRepository {
    suspend fun register(email: String, username: String, password: String): Boolean
    suspend fun login(email: String, password: String): TokenResponse
    suspend fun getCurrentUser(): UserDetailResponse
}