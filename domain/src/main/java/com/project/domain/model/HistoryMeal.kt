package com.project.domain.model

enum class HistoryMealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}

data class HistoryMeal(
    val name: String,
    val nutritionInfo: NutritionInfo,
    val suitableFor: List<SuitableForEnum>,
    val healthWarnings: List<HealthWarningsEnum>,
    val category: HistoryMealType,
    val dateTime: String
)