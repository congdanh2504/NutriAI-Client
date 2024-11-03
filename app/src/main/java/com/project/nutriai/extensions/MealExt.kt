package com.project.nutriai.extensions

import android.content.Context
import com.project.domain.model.Category
import com.project.nutriai.R

fun Category.toReadableString(context: Context): String {
    return when (this) {
        Category.NOODLE_SOUPS -> context.getString(R.string.noodle_soups)
        Category.RICE_DISHES -> context.getString(R.string.rice_dishes)
        Category.SOUPS -> context.getString(R.string.soups)
        Category.HOTPOTS -> context.getString(R.string.hotpots)
        Category.APPETIZERS_AND_STREET_FOOD -> context.getString(R.string.appetizers_and_street_food)
        Category.VEGETARIAN_DISHES -> context.getString(R.string.vegetarian_dishes)
        Category.DESSERTS_AND_SWEET_SNACKS -> context.getString(R.string.desserts_and_sweet_snacks)
        Category.SEAFOOD_DISHES -> context.getString(R.string.seafood_dishes)
        Category.GRILLED_DISHES -> context.getString(R.string.grilled_dishes)
        Category.STEAMED_DISHES -> context.getString(R.string.steamed_dishes)
        Category.STREET_SNACKS_AND_BEVERAGES -> context.getString(R.string.street_snacks_and_beverages)
    }
}

fun fromCategoryOrdinal(ordinal: Int): Category {
    return when (ordinal) {
        Category.NOODLE_SOUPS.ordinal -> Category.NOODLE_SOUPS
        Category.RICE_DISHES.ordinal -> Category.RICE_DISHES
        Category.SOUPS.ordinal -> Category.SOUPS
        Category.HOTPOTS.ordinal -> Category.HOTPOTS
        Category.APPETIZERS_AND_STREET_FOOD.ordinal -> Category.APPETIZERS_AND_STREET_FOOD
        Category.VEGETARIAN_DISHES.ordinal -> Category.VEGETARIAN_DISHES
        Category.DESSERTS_AND_SWEET_SNACKS.ordinal -> Category.DESSERTS_AND_SWEET_SNACKS
        Category.SEAFOOD_DISHES.ordinal -> Category.SEAFOOD_DISHES
        Category.GRILLED_DISHES.ordinal -> Category.GRILLED_DISHES
        Category.STEAMED_DISHES.ordinal -> Category.STEAMED_DISHES
        Category.STREET_SNACKS_AND_BEVERAGES.ordinal -> Category.STREET_SNACKS_AND_BEVERAGES
        else -> Category.NOODLE_SOUPS
    }
}