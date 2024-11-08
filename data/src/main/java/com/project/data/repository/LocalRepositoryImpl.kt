package com.project.data.repository

import com.project.data.mapper.toDomain
import com.project.data.mapper.toEntity
import com.project.data.source.local.MealDetailDao
import com.project.data.source.local.model.MealDetailEntity
import com.project.data.utils.TokenPref
import com.project.domain.model.MealDetails
import com.project.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val mealDetailDao: MealDetailDao
) : LocalRepository {
    override suspend fun insertMealDetail(mealDetail: MealDetails) {
        mealDetailDao.insertMealDetail(mealDetail.toEntity(TokenPref.userId))
    }

    override suspend fun removeMealDetail(id: String) {
        mealDetailDao.removeMealDetail(id)
    }

    override fun getMealDetails(): Flow<List<MealDetails>> {
        return mealDetailDao.getMealDetailByUserId(TokenPref.userId)
            .map { it.map(MealDetailEntity::toDomain) }
    }

    override fun getMealDetailById(id: String): Flow<MealDetails?> {
        return mealDetailDao.getMealDetailById(id).map { it?.toDomain() }
    }
}