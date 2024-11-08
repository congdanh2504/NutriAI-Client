package com.project.data.mapper

import com.project.data.source.remote.model.UserDetailNetwork
import com.project.data.source.remote.model.UserDetailResponse
import com.project.data.source.remote.model.UserResponse
import com.project.domain.model.DietPreference
import com.project.domain.model.FoodAllergies
import com.project.domain.model.Gender
import com.project.domain.model.HealthConditions
import com.project.domain.model.NutritionGoal
import com.project.domain.model.PhysicalActivity
import com.project.domain.model.User
import com.project.domain.model.UserDetail

fun UserResponse.toDomain() = User(email, username, hasAnsweredSurvey)

fun UserDetailNetwork.toDomain() = UserDetail(
    fullName,
    age,
    Gender.valueOf(gender),
    weight,
    height,
    NutritionGoal.valueOf(nutritionGoal),
    DietPreference.valueOf(dietPreference),
    foodAllergies.map { FoodAllergies.valueOf(it) },
    PhysicalActivity.valueOf(physicalActivity),
    healthConditions.map { HealthConditions.valueOf(it) }
)

fun UserDetail.toNetwork() = UserDetailNetwork(
    fullName,
    age,
    gender.name,
    weight,
    height,
    nutritionGoal.name,
    dietPreference.name,
    foodAllergies.map { it.name },
    physicalActivity.name,
    healthConditions.map { it.name }
)

fun UserDetailResponse.toDomain() = UserDetail(
    fullName,
    age,
    Gender.valueOf(gender),
    weight,
    height,
    NutritionGoal.valueOf(nutritionGoal),
    DietPreference.valueOf(dietPreference),
    foodAllergies.map { FoodAllergies.valueOf(it) },
    PhysicalActivity.valueOf(physicalActivity),
    healthConditions.map { HealthConditions.valueOf(it) }
)