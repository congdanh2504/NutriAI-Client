package com.project.data.source.remote

import com.project.data.source.remote.dto.LoginRequest
import com.project.data.source.remote.dto.LoginResponse
import com.project.data.source.remote.dto.RegisterRequest
import com.project.data.source.remote.dto.UserDetailNetwork
import com.project.data.source.remote.dto.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponse

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/refresh-token")
    suspend fun refreshToken(refreshToken: String): LoginResponse

    @GET("user/me")
    suspend fun getCurrentUser(): UserResponse

    @POST("user/user_detail")
    suspend fun updateUserDetail(@Body userDetailNetwork: UserDetailNetwork)

    @GET("user/user_detail")
    suspend fun getUserDetail(): UserDetailNetwork
}