package com.project.domain.model

import com.project.data.source.remote.dto.MealNetwork

data class Meal(
    val id: String,
    val name: String,
    val category: Category,
    val imageUrl: String
)

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