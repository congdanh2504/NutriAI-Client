package com.project.domain.repository

import com.project.domain.model.TodayCalories

interface UserRepository {
    suspend fun getTodayCalories(): TodayCalories
}
