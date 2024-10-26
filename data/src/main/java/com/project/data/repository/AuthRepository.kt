package com.project.data.repository

import com.project.data.source.remote.dto.LoginResponse
import com.project.data.source.remote.dto.UserDetailNetwork
import com.project.data.source.remote.dto.UserResponse

interface AuthRepository {
    suspend fun register(email: String, username: String, password: String): Boolean
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun getCurrentUser(): UserResponse
    suspend fun updateUserDetail(userDetailNetwork: UserDetailNetwork)
    suspend fun getUserDetail(): UserDetailNetwork
}