package com.project.data.repository

import com.project.data.mapper.toDomain
import com.project.data.mapper.toNetwork
import com.project.data.source.remote.AppApi
import com.project.data.source.remote.model.LoginRequest
import com.project.data.source.remote.model.RegisterRequest
import com.project.data.utils.TokenPref
import com.project.domain.model.LoginResponse
import com.project.domain.model.User
import com.project.domain.model.UserDetail
import com.project.domain.repository.AuthRepository
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

    override suspend fun login(
        email: String,
        password: String
    ): LoginResponse {
        return appApi.login(LoginRequest(email, password)).toDomain()
    }

    override suspend fun getCurrentUser(): User {
        return appApi.getCurrentUser().toDomain()
    }

    override suspend fun updateUserDetail(userDetail: UserDetail) {
        return appApi.updateUserDetail(userDetail.toNetwork())
    }

    override suspend fun getUserDetail(): UserDetail {
        val user = appApi.getUserDetail()
        TokenPref.userId = user.userId
        return user.toDomain()
    }
}