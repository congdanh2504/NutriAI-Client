package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.project.data.mapper.toDomain
import com.project.domain.model.Category
import com.project.domain.model.MealPlan
import com.project.domain.model.MealsPlan

data class MealsPlanNetwork(
    @SerializedName("_id")
    val id: String,
    @SerializedName("meals")
    val meals: List<MealPlanNetwork>
)

data class MealPlanNetwork(
    @SerializedName("id")
    val id: String,
    @SerializedName("meal_name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("meal_image_url")
    val imageUrl: String,
    @SerializedName("ingredients")
    val ingredients: List<IngredientNetwork>
)

fun MealsPlanNetwork.toDomain() = MealsPlan(
    id = id,
    meals = meals.map { it.toDomain() }
)

fun MealPlanNetwork.toDomain() = MealPlan(
    id = id,
    name = name,
    category = Category.valueOf(category),
    imageUrl = imageUrl,
    ingredients = ingredients.map { it.toDomain() }
)
