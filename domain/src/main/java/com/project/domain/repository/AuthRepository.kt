package com.project.domain.repository

import com.project.domain.model.LoginResponse
import com.project.domain.model.User
import com.project.domain.model.UserDetail

interface AuthRepository {
    suspend fun register(email: String, username: String, password: String): Boolean
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun getCurrentUser(): User
    suspend fun updateUserDetail(userDetail: UserDetail): UserDetail
    suspend fun getUserDetail(): UserDetail
}