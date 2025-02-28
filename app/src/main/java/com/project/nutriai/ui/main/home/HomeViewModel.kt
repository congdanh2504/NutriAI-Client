package com.project.nutriai.ui.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.domain.model.Category
import com.project.domain.model.Meal
import com.project.domain.use_case.meal.GetAvoidedMealsUseCase
import com.project.domain.use_case.meal.GetRecommendedBasedOnHistoryMealsUseCase
import com.project.domain.use_case.meal.GetRecommendedMealsUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecommendedBasedOnHistoryMealsUseCase: GetRecommendedBasedOnHistoryMealsUseCase,
    private val getRecommendedMealsUseCase: GetRecommendedMealsUseCase,
    private val getAvoidedMealsUseCase: GetAvoidedMealsUseCase
) : BaseViewModel() {

    private val _recommendedMeals = MutableStateFlow(emptyList<Meal>())
    val recommendedMeals = _recommendedMeals.asStateFlow()

    private val _avoidedMeals = MutableStateFlow(emptyList<Meal>())
    val avoidedMeals = _avoidedMeals.asStateFlow()

    private val _recommendedHistoryMeals = MutableStateFlow(emptyList<Meal>())
    val recommendedHistoryMeals = _recommendedHistoryMeals.asStateFlow()

    init {
        getRecommendedBasedOnHistoryMeals()
        getRecommendedMeals()
        getAvoidedMeals()
    }

    private fun getRecommendedBasedOnHistoryMeals() = viewModelScope.launch {
        _recommendedHistoryMeals.value = try {
            getRecommendedBasedOnHistoryMealsUseCase()
        } catch (e: Exception) {
            Log.e("HomeViewModel", "getRecommendedBasedOnHistoryMeals: $e")
            emptyList()
        }
    }

    private fun getRecommendedMeals() = viewModelScope.launch {
        _recommendedMeals.value = try {
            Log.d("HomeViewModel", "ok")
            getRecommendedMealsUseCase()
        } catch (e: Exception) {
            Log.e("HomeViewModel", "getRecommendedMeals: $e")
            emptyList()
        }
    }

    private fun getAvoidedMeals() = viewModelScope.launch {
        _avoidedMeals.value = try {
            Log.d("HomeViewModel", "ok2")
            getAvoidedMealsUseCase()
        } catch (e: Exception) {
            Log.e("HomeViewModel", "getAvoidedMeals: $e")
            emptyList()
        }
    }
}

data class CategoryItem(
    val name: Int,
    val image: Int,
    val type: Category
)
