package com.project.data.source.remote

import com.project.data.source.remote.model.AnalysisNetwork
import com.project.data.source.remote.model.ChatRequestNetwork
import com.project.data.source.remote.model.ChatResponseNetwork
import com.project.data.source.remote.model.FruitNutritionNetwork
import com.project.data.source.remote.model.HistoryMealNetwork
import com.project.data.source.remote.model.LoginRequest
import com.project.data.source.remote.model.LoginResponseNetwork
import com.project.data.source.remote.model.MealDetailNetwork
import com.project.data.source.remote.model.MealNetwork
import com.project.data.source.remote.model.MealsPlanNetwork
import com.project.data.source.remote.model.RegisterRequest
import com.project.data.source.remote.model.TodayCaloriesNetwork
import com.project.data.source.remote.model.UserDetailNetwork
import com.project.data.source.remote.model.UserDetailResponse
import com.project.data.source.remote.model.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun updateUserDetail(@Body userDetailNetwork: UserDetailNetwork): UserDetailResponse

    @GET("user/user_detail")
    suspend fun getUserDetail(): UserDetailResponse

    @GET("user/today_calories")
    suspend fun getTodayCalories(): TodayCaloriesNetwork

    @GET("meal/")
    suspend fun getAllMeals(): List<MealNetwork>

    @GET("meal/recommendation-ml")
    suspend fun getRecommendedBasedOnHistoryMeals(): List<MealNetwork>

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

    @GET("meal/meal-history")
    suspend fun getHistoryMeals(
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?
    ): List<HistoryMealNetwork>

    @POST("meal/meal-history/{id}")
    suspend fun updateHistoryMeal(@Path("id") id: String, @Body historyMeal: HistoryMealNetwork)

    @DELETE("meal/meal-history/{id}")
    suspend fun deleteHistoryMeal(@Path("id") id: String)

    @GET("meal/analysis")
    suspend fun getMealAnalysis(
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?
    ): AnalysisNetwork

    @Multipart
    @POST("predict")
    suspend fun predict(@Part file: MultipartBody.Part): FruitNutritionNetwork

    @GET("meal/meal-plan")
    suspend fun getMealsPlan(@Query("start_date") date: String): MealsPlanNetwork

    @POST("chat")
    suspend fun sendMessage(@Body request: ChatRequestNetwork): ChatResponseNetwork
}