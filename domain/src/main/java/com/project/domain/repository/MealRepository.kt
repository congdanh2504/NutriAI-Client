package com.project.domain.repository

import com.project.domain.model.HistoryMeal
import com.project.domain.model.Meal
import com.project.domain.model.MealDetails

interface MealRepository {
    suspend fun getAllMeals(): List<Meal>
    suspend fun getRecommendedMeals(): List<Meal>
    suspend fun getAvoidedMeals(): List<Meal>
    suspend fun getMealById(id: String): MealDetails
    suspend fun searchMeals(category: String?, name: String?): List<Meal>
    suspend fun addHistoryMeal(historyMeal: HistoryMeal)
}