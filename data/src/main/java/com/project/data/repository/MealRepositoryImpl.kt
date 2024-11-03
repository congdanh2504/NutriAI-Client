package com.project.data.repository

import com.project.data.source.remote.AppApi
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : MealRepository {
    override suspend fun getAllMeals() = appApi.getAllMeals()

    override suspend fun getRecommendedMeals() = appApi.getRecommendedMeals()

    override suspend fun getAvoidedMeals() = appApi.getAvoidedMeals()

    override suspend fun getMealById(id: String) = appApi.getMealById(id)

    override suspend fun searchMeals(category: String?, name: String?) =
        appApi.searchMeals(category, name)
}