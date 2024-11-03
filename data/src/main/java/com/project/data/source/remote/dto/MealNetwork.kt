package com.project.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class MealNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("meal_name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("meal_image_url")
    val imageUrl: String
)