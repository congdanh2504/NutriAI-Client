package com.project.domain.model

import com.project.data.source.remote.dto.IngredientNetwork
import com.project.data.source.remote.dto.MealDetailNetwork
import com.project.data.source.remote.dto.MealNetwork
import com.project.data.source.remote.dto.NutritionInfoNetwork

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

fun MealDetailNetwork.toMealDetails() = MealDetails(
    id = id,
    name = name,
    ingredients = ingredients.map { it.toIngredient() },
    nutritionInfo = nutritionInfo.toNutritionInfo(),
    suitableFor = suitableFor.map { SuitableForEnum.valueOf(it) },
    category = Category.valueOf(category),
    healthWarnings = healthWarnings.map { HealthWarningsEnum.valueOf(it) },
    imageUrl = imageUrl,
    instructions = instructions
)

data class Ingredient(
    val name: String,
    val quantity: Int,
    val unit: String
)

fun IngredientNetwork.toIngredient() = Ingredient(
    name = name,
    quantity = quantity,
    unit = unit
)

data class NutritionInfo(
    val calories: Int,
    val protein: Int,
    val carbohydrates: Int,
    val fats: Int,
    val fiber: Int,
    val sugar: Int
)

fun NutritionInfoNetwork.toNutritionInfo() = NutritionInfo(
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fats = fats,
    fiber = fiber,
    sugar = sugar
)

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

fun MealNetwork.toMeal() = Meal(
    id = id,
    name = name,
    category = Category.valueOf(category),
    imageUrl = imageUrl
)

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