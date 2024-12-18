package com.project.domain.repository

import com.project.domain.model.ChatRequest
import com.project.domain.model.ChatResponse
import com.project.domain.model.MealsPlan
import com.project.domain.model.TodayCalories

interface UserRepository {
    suspend fun getTodayCalories(): TodayCalories
    suspend fun getMealsPlan(date: String): MealsPlan
    suspend fun sendMessage(request: ChatRequest): ChatResponse
}
