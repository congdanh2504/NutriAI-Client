package com.project.data.repository

import com.project.data.mapper.toDomain
import com.project.data.source.remote.AppApi
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : com.project.domain.repository.MealRepository {
    override suspend fun getAllMeals() = appApi.getAllMeals().map { it.toDomain() }

    override suspend fun getRecommendedMeals() = appApi.getRecommendedMeals().map { it.toDomain() }

    override suspend fun getAvoidedMeals() = appApi.getAvoidedMeals().map { it.toDomain() }

    override suspend fun getMealById(id: String) = appApi.getMealById(id).toDomain()

    override suspend fun searchMeals(category: String?, name: String?) =
        appApi.searchMeals(category, name).map { it.toDomain() }
}