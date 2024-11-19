package com.project.domain.repository

import com.project.domain.model.Analysis
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
    suspend fun getHistoryMeals(startDate: String?, endDate: String?): List<HistoryMeal>
    suspend fun updateHistoryMeal(historyMeal: HistoryMeal)
    suspend fun deleteHistoryMeal(id: String)
    suspend fun getAnalysis(startDate: String?, endDate: String?): Analysis
}