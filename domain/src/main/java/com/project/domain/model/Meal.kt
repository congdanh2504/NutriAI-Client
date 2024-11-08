package com.project.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Meal(
    val id: String,
    val name: String,
    val category: Category,
    val imageUrl: String
)

data class MealDetails(
    val id: String,
    val name: String,
    val ingredients: List<Ingredient>,
    val nutritionInfo: NutritionInfo,
    val suitableFor: List<SuitableForEnum>,
    val category: Category,
    val healthWarnings: List<HealthWarningsEnum>,
    val imageUrl: String,
    val instructions: String
)

data class Ingredient(
    val name: String,
    val quantity: Int,
    val unit: String
)

@Parcelize
data class NutritionInfo(
    val calories: Int,
    val protein: Int,
    val carbohydrates: Int,
    val fats: Int,
    val fiber: Int,
    val sugar: Int
): Parcelable

enum class SuitableForEnum {
    GLUTEN_FREE,
    LOW_CARB,
    VEGETARIAN,
    VEGAN,
    KETO,
    PALEO,
    DAIRY_FREE,
    HIGH_PROTEIN,
    LOW_FAT,
    DIABETIC_FRIENDLY,
    HEART_HEALTHY
}

enum class HealthWarningsEnum {
    HIGH_SODIUM,
    HIGH_SUGAR,
    HIGH_FAT,
    HIGH_CHOLESTEROL,
    CONTAINS_GLUTEN,
    CONTAINS_LACTOSE,
    CONTAINS_NUTS,
    CONTAINS_SOY,
    CONTAINS_EGGS,
    CONTAINS_SEAFOOD,
    CONTAINS_SHELLFISH,
    SPICY
}

enum class Category {
    NOODLE_SOUPS,
    RICE_DISHES,
    SOUPS,
    HOTPOTS,
    APPETIZERS_AND_STREET_FOOD,
    VEGETARIAN_DISHES,
    DESSERTS_AND_SWEET_SNACKS,
    SEAFOOD_DISHES,
    GRILLED_DISHES,
    STEAMED_DISHES,
    STREET_SNACKS_AND_BEVERAGES
}