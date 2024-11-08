package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("nutrition_goal")
    val nutritionGoal: String,
    @SerializedName("diet_preference")
    val dietPreference: String,
    @SerializedName("food_allergies")
    val foodAllergies: List<String>,
    @SerializedName("physical_activity")
    val physicalActivity: String,
    @SerializedName("health_conditions")
    val healthConditions: List<String>
)
