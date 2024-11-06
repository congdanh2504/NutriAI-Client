package com.project.data.repository

import com.project.data.mapper.toDomain
import com.project.data.mapper.toNetwork
import com.project.data.source.remote.AppApi
import com.project.domain.model.HistoryMeal
import com.project.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : MealRepository {
    override suspend fun getAllMeals() = appApi.getAllMeals().map { it.toDomain() }

    override suspend fun getRecommendedMeals() = appApi.getRecommendedMeals().map { it.toDomain() }

    override suspend fun getAvoidedMeals() = appApi.getAvoidedMeals().map { it.toDomain() }

    override suspend fun getMealById(id: String) = appApi.getMealById(id).toDomain()

    override suspend fun searchMeals(category: String?, name: String?) =
        appApi.searchMeals(category, name).map { it.toDomain() }

    override suspend fun addHistoryMeal(historyMeal: HistoryMeal) {
        appApi.addHistoryMeal(historyMeal.toNetwork())
    }
}