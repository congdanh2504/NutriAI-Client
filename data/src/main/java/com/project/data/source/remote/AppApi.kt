package com.project.data.source.remote

import com.project.data.source.remote.dto.HistoryMealNetwork
import com.project.data.source.remote.dto.LoginRequest
import com.project.data.source.remote.dto.LoginResponseNetwork
import com.project.data.source.remote.dto.MealDetailNetwork
import com.project.data.source.remote.dto.MealNetwork
import com.project.data.source.remote.dto.RegisterRequest
import com.project.data.source.remote.dto.UserDetailNetwork
import com.project.data.source.remote.dto.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponseNetwork

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponseNetwork

    @POST("auth/refresh-token")
    suspend fun refreshToken(refreshToken: String): LoginResponseNetwork

    @GET("user/me")
    suspend fun getCurrentUser(): UserResponse

    @POST("user/user_detail")
    suspend fun updateUserDetail(@Body userDetailNetwork: UserDetailNetwork)

    @GET("user/user_detail")
    suspend fun getUserDetail(): UserDetailNetwork

    @GET("meal/")
    suspend fun getAllMeals(): List<MealNetwork>

    @GET("meal/recommendation")
    suspend fun getRecommendedMeals(): List<MealNetwork>

    @GET("meal/avoid")
    suspend fun getAvoidedMeals(): List<MealNetwork>

    @GET("meal/{id}")
    suspend fun getMealById(@Path("id") id: String): MealDetailNetwork

    @GET("meal/")
    suspend fun searchMeals(
        @Query("category") category: String?,
        @Query("meal_name") name: String?
    ): List<MealNetwork>

    @POST("meal/add-meal-history")
    suspend fun addHistoryMeal(@Body historyMeal: HistoryMealNetwork)
}