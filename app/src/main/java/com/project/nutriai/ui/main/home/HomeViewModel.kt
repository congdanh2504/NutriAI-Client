package com.project.nutriai.ui.main.home

import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    val category = listOf(
        CategoryItem("Noodle Soups", R.drawable.noodle_soups),
        CategoryItem("Rice Dishes", R.drawable.rice_dishes),
        CategoryItem("Soups", R.drawable.soups),
        CategoryItem("Hotpots", R.drawable.hotpots),
        CategoryItem("Appetizers and Street Food", R.drawable.appetizers_and_street_food),
        CategoryItem("Vegetarian Dishes", R.drawable.vegetarian_dishes),
        CategoryItem("Desserts and Sweet Snacks", R.drawable.desserts_and_sweet_snacks),
        CategoryItem("Seafood Dishes", R.drawable.seafood_dishes),
        CategoryItem("Grilled Dishes", R.drawable.grilled_dishes),
        CategoryItem("Steamed Dishes", R.drawable.steamed_dishes),
        CategoryItem("Street Snacks and Beverages", R.drawable.street_snacks_and_beverages)
    )
}

data class CategoryItem(
    val name: String,
    val image: Int
)
