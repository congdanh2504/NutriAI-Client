package com.project.data.repository

import com.project.data.mapper.toDomain
import com.project.data.mapper.toNetwork
import com.project.data.source.remote.AppApi
import com.project.data.source.remote.model.toDomain
import com.project.domain.model.Analysis
import com.project.domain.model.HistoryMeal
import com.project.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : MealRepository {
    override suspend fun getAllMeals() = appApi.getAllMeals().map { it.toDomain() }

    override suspend fun getRecommendedBasedOnHistoryMeals() =
        appApi.getRecommendedBasedOnHistoryMeals().map { it.toDomain() }

    override suspend fun getRecommendedMeals() = appApi.getRecommendedMeals().map { it.toDomain() }

    override suspend fun getAvoidedMeals() = appApi.getAvoidedMeals().map { it.toDomain() }

    override suspend fun getMealById(id: String) = appApi.getMealById(id).toDomain()

    override suspend fun searchMeals(category: String?, name: String?) =
        appApi.searchMeals(category, name).map { it.toDomain() }

    override suspend fun addHistoryMeal(historyMeal: HistoryMeal) {
        appApi.addHistoryMeal(historyMeal.toNetwork())
    }

    override suspend fun getHistoryMeals(startDate: String?, endDate: String?) =
        appApi.getHistoryMeals(startDate, endDate).map { it.toDomain() }

    override suspend fun updateHistoryMeal(historyMeal: HistoryMeal) {
        appApi.updateHistoryMeal(historyMeal.id, historyMeal.toNetwork())
    }

    override suspend fun deleteHistoryMeal(id: String) {
        appApi.deleteHistoryMeal(id)
    }

    override suspend fun getAnalysis(startDate: String?, endDate: String?): Analysis {
        return appApi.getMealAnalysis(startDate, endDate).toDomain()
    }
}