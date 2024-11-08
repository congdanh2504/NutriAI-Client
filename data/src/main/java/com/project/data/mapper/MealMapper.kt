package com.project.data.mapper

import com.project.data.source.local.model.MealDetailEntity
import com.project.data.source.remote.model.HistoryMealNetwork
import com.project.data.source.remote.model.IngredientNetwork
import com.project.data.source.remote.model.MealDetailNetwork
import com.project.data.source.remote.model.MealNetwork
import com.project.data.source.remote.model.NutritionInfoNetwork
import com.project.domain.model.Category
import com.project.domain.model.HealthWarningsEnum
import com.project.domain.model.HistoryMeal
import com.project.domain.model.HistoryMealType
import com.project.domain.model.Ingredient
import com.project.domain.model.Meal
import com.project.domain.model.MealDetails
import com.project.domain.model.NutritionInfo
import com.project.domain.model.SuitableForEnum

fun MealDetailNetwork.toDomain() = MealDetails(
    id = id,
    name = name,
    ingredients = ingredients.map { it.toDomain() },
    nutritionInfo = nutritionInfo.toDomain(),
    suitableFor = suitableFor.map { SuitableForEnum.valueOf(it) },
    category = Category.valueOf(category),
    healthWarnings = healthWarnings.map { HealthWarningsEnum.valueOf(it) },
    imageUrl = imageUrl,
    instructions = instructions
)

fun IngredientNetwork.toDomain() = Ingredient(
    name = name,
    quantity = quantity,
    unit = unit
)

fun NutritionInfoNetwork.toDomain() = NutritionInfo(
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fats = fats,
    fiber = fiber,
    sugar = sugar
)

fun MealNetwork.toDomain() = Meal(
    id = id,
    name = name,
    category = Category.valueOf(category),
    imageUrl = imageUrl
)

fun NutritionInfo.toNetwork() = NutritionInfoNetwork(
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fats = fats,
    fiber = fiber,
    sugar = sugar
)

fun HistoryMeal.toNetwork() = HistoryMealNetwork(
    id = id,
    name = name,
    nutritionInfo = nutritionInfo.toNetwork(),
    suitableFor = suitableFor.map { it.name },
    healthWarnings = healthWarnings.map { it.name },
    category = category.name,
    dateTime = dateTime
)

fun HistoryMealNetwork.toDomain() = HistoryMeal(
    id = id,
    name = name,
    nutritionInfo = nutritionInfo.toDomain(),
    suitableFor = suitableFor.map { SuitableForEnum.valueOf(it) },
    healthWarnings = healthWarnings.map { HealthWarningsEnum.valueOf(it) },
    category = HistoryMealType.valueOf(category),
    dateTime = dateTime
)

fun MealDetails.toEntity(userId: String) = MealDetailEntity(
    id = id,
    userId = userId,
    name = name,
    ingredients = ingredients,
    nutritionInfo = nutritionInfo,
    suitableFor = suitableFor.map { it.name },
    category = category.name,
    healthWarnings = healthWarnings.map { it.name },
    imageUrl = imageUrl,
    instructions = instructions
)

fun MealDetailEntity.toDomain() = MealDetails(
    id = id,
    name = name,
    ingredients = ingredients,
    nutritionInfo = nutritionInfo,
    suitableFor = suitableFor.map { SuitableForEnum.valueOf(it) },
    category = Category.valueOf(category),
    healthWarnings = healthWarnings.map { HealthWarningsEnum.valueOf(it) },
    imageUrl = imageUrl,
    instructions = instructions
)
