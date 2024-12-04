package com.project.domain.model

data class FruitNutrition(
    val name: String,
    val imageUrl: String,
    val nutritionInfoPer100g: FruitNutritionInfo,
    val healthBenefits: List<String>,
    val healthWarnings: List<String>
)

data class FruitNutritionInfo(
    val calories: Int,
    val protein: Double,
    val fat: Double,
    val carbs: Double,
    val fiber: Double,
    val sugar: Double
)
