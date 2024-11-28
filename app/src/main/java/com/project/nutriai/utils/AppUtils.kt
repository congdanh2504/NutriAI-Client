package com.project.nutriai.utils

import com.project.domain.model.Category
import com.project.nutriai.R
import com.project.nutriai.ui.main.home.CategoryItem

object AppUtils {
    const val GEMINI_API_KEY = "AIzaSyA7c-8zjMK3RqE1cdWYrCdm1RiVj_B5Wuk"
    val category = listOf(
        CategoryItem(R.string.noodle_soups, R.drawable.noodle_soups, Category.NOODLE_SOUPS),
        CategoryItem(R.string.rice_dishes, R.drawable.rice_dishes, Category.RICE_DISHES),
        CategoryItem(R.string.soups, R.drawable.soups, Category.SOUPS),
        CategoryItem(R.string.hotpots, R.drawable.hotpots, Category.HOTPOTS),
        CategoryItem(
            R.string.appetizers_and_street_food,
            R.drawable.appetizers_and_street_food,
            Category.APPETIZERS_AND_STREET_FOOD
        ),
        CategoryItem(
            R.string.vegetarian_dishes,
            R.drawable.vegetarian_dishes,
            Category.VEGETARIAN_DISHES
        ),
        CategoryItem(
            R.string.desserts_and_sweet_snacks,
            R.drawable.desserts_and_sweet_snacks,
            Category.DESSERTS_AND_SWEET_SNACKS
        ),
        CategoryItem(R.string.seafood_dishes, R.drawable.seafood_dishes, Category.SEAFOOD_DISHES),
        CategoryItem(R.string.grilled_dishes, R.drawable.grilled_dishes, Category.GRILLED_DISHES),
        CategoryItem(R.string.steamed_dishes, R.drawable.steamed_dishes, Category.STEAMED_DISHES),
        CategoryItem(
            R.string.street_snacks_and_beverages,
            R.drawable.street_snacks_and_beverages,
            Category.STREET_SNACKS_AND_BEVERAGES
        ),
    )
}