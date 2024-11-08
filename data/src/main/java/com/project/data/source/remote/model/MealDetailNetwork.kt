package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class MealDetailNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("meal_name")
    val name: String,
    @SerializedName("ingredients")
    val ingredients: List<IngredientNetwork>,
    @SerializedName("nutrition_info")
    val nutritionInfo: NutritionInfoNetwork,
    @SerializedName("suitable_for")
    val suitableFor: List<String>,
    @SerializedName("category")
    val category: String,
    @SerializedName("health_warnings")
    val healthWarnings: List<String>,
    @SerializedName("meal_image_url")
    val imageUrl: String,
    @SerializedName("instructions")
    val instructions: String
)

data class IngredientNetwork(
    @SerializedName("ingredient_name")
    val name: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("unit")
    val unit: String
)

data class NutritionInfoNetwork(
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("protein")
    val protein: Int,
    @SerializedName("carbohydrates")
    val carbohydrates: Int,
    @SerializedName("fats")
    val fats: Int,
    @SerializedName("fiber")
    val fiber: Int,
    @SerializedName("sugar")
    val sugar: Int
)