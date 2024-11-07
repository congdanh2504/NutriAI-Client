package com.project.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

enum class HistoryMealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}

@Parcelize
data class HistoryMeal(
    val id: String,
    val name: String,
    val nutritionInfo: NutritionInfo,
    val suitableFor: List<SuitableForEnum>,
    val healthWarnings: List<HealthWarningsEnum>,
    val category: HistoryMealType,
    val dateTime: String
): Parcelable {

    @IgnoredOnParcel
    val date = dateTime.split("T").first()
}