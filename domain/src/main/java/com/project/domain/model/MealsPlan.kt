package com.project.domain.model

data class MealsPlan(
    val id: String,
    val meals: List<MealPlan>
)

data class MealPlan(
    val id: String,
    val name: String,
    val category: Category,
    val imageUrl: String,
    val ingredients: List<Ingredient>
)
