package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class HistoryMealNetwork(
    @SerializedName("_id")
    val id: String,
    @SerializedName("meal_name")
    val name: String,
    @SerializedName("nutrition_info")
    val nutritionInfo: NutritionInfoNetwork,
    @SerializedName("suitable_for")
    val suitableFor: List<String>,
    @SerializedName("health_warnings")
    val healthWarnings: List<String>,
    @SerializedName("category")
    val category: String,
    @SerializedName("date_time")
    val dateTime: String
)