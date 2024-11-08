package com.project.domain.repository

import com.project.domain.model.MealDetails
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertMealDetail(mealDetail: MealDetails)
    suspend fun removeMealDetail(id: String)
    fun getMealDetails(): Flow<List<MealDetails>>
    fun getMealDetailById(id: String): Flow<MealDetails?>
}