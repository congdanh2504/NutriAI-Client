package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.project.domain.model.FruitNutrition
import com.project.domain.model.FruitNutritionInfo

data class FruitNutritionNetwork(
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("nutrition_info_per_100g")
    val nutritionInfoPer100g: FruitNutritionInfoNetwork,
    @SerializedName("health_benefits")
    val healthBenefits: List<String>,
    @SerializedName("health_warnings")
    val healthWarnings: List<String>
)

data class FruitNutritionInfoNetwork(
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("protein")
    val protein: Double,
    @SerializedName("carbohydrates")
    val carbohydrates: Double,
    @SerializedName("fats")
    val fats: Double,
    @SerializedName("fiber")
    val fiber: Double,
    @SerializedName("sugar")
    val sugar: Double
)

fun FruitNutritionInfoNetwork.toDomain(): FruitNutritionInfo {
    return FruitNutritionInfo(
        calories = calories,
        protein = protein,
        fat = fats,
        carbs = carbohydrates,
        fiber = fiber,
        sugar = sugar
    )
}

fun FruitNutritionNetwork.toDomain(): FruitNutrition {
    return FruitNutrition(
        name = name,
        imageUrl = imageUrl,
        nutritionInfoPer100g = nutritionInfoPer100g.toDomain(),
        healthBenefits = healthBenefits,
        healthWarnings = healthWarnings
    )
}
