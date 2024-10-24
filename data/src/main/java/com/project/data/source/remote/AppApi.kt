package com.project.data.source.remote

import com.project.data.source.remote.dto.LoginRequest
import com.project.data.source.remote.dto.RegisterRequest
import com.project.data.source.remote.dto.TokenResponse
import com.project.data.source.remote.dto.UserDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): TokenResponse

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("auth/refresh-token")
    suspend fun refreshToken(refreshToken: String): TokenResponse

    @GET("me")
    suspend fun getCurrentUser(): UserDetailResponse
}