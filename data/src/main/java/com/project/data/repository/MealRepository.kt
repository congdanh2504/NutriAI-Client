package com.project.data.repository

import com.project.data.source.remote.dto.MealDetailNetwork
import com.project.data.source.remote.dto.MealNetwork

interface MealRepository {
    suspend fun getAllMeals(): List<MealNetwork>
    suspend fun getRecommendedMeals(): List<MealNetwork>
    suspend fun getAvoidedMeals(): List<MealNetwork>
    suspend fun getMealById(id: String): MealDetailNetwork
    suspend fun searchMeals(category: String?, name: String?): List<MealNetwork>
}