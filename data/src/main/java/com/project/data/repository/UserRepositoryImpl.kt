package com.project.data.repository

import com.project.data.source.remote.AppApi
import com.project.data.source.remote.model.toDomain
import com.project.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : UserRepository {

    override suspend fun getTodayCalories() = appApi.getTodayCalories().toDomain()
    override suspend fun getMealsPlan(date: String) = appApi.getMealsPlan(date).toDomain()
}
