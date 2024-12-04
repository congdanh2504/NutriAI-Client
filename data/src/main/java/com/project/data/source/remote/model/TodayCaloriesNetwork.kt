package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.project.domain.model.TodayCalories

data class TodayCaloriesNetwork(
    @SerializedName("daily_calories")
    val dailyCalories: Int,
    @SerializedName("today_calories")
    val todayCalories: Int
)

fun TodayCaloriesNetwork.toDomain() = TodayCalories(dailyCalories, todayCalories)
