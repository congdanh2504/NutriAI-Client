package com.project.data.mapper

import com.project.data.source.remote.dto.IngredientNetwork
import com.project.data.source.remote.dto.MealDetailNetwork
import com.project.data.source.remote.dto.MealNetwork
import com.project.data.source.remote.dto.NutritionInfoNetwork
import com.project.domain.model.Category
import com.project.domain.model.HealthWarningsEnum
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
