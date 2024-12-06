package com.project.nutriai.ui.main.meal_plan

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.domain.model.MealsPlan
import com.project.domain.use_case.user.GetMealsPlanUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanViewModel @Inject constructor(
    private val getMealsPlanUseCase: GetMealsPlanUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(MealPlanViewState())
    val state = _state.asStateFlow()

    fun getMealsPlan(date: String) = viewModelScope.launch {
        try {
            _state.value = MealPlanViewState(isLoading = true)
            val result = getMealsPlanUseCase(date)
            _state.value = MealPlanViewState(mealsPlan = result)
            Log.d("MealPlanViewModel", "Meals plan: $result")
        } catch (e: Exception) {
            Log.e("MealPlanViewModel", "An error occurred", e)
            _state.value = MealPlanViewState(error = e.message ?: "An unexpected error occurred")
        }
    }
}

data class MealPlanViewState(
    val isLoading: Boolean = false,
    val mealsPlan: MealsPlan? = null,
    val error: String = ""
)
