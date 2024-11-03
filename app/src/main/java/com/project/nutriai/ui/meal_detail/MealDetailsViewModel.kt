package com.project.nutriai.ui.meal_detail

import androidx.lifecycle.viewModelScope
import com.project.domain.model.MealDetails
import com.project.domain.use_case.meal.GetMealDetailsByIdUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val getMealDetailsByIdUseCase: GetMealDetailsByIdUseCase
) : BaseViewModel() {

    private val _mealDetails = MutableStateFlow(MealDetailsViewState())
    val mealDetails = _mealDetails.asStateFlow()

    private val _isExpand = MutableStateFlow(false)
    val isExpand = _isExpand.asStateFlow()

    fun getMealDetailsById(id: String) = viewModelScope.launch {
        _mealDetails.value = _mealDetails.value.copy(isLoading = true)
        _mealDetails.value = try {
            val mealDetails = getMealDetailsByIdUseCase(id)
            MealDetailsViewState(mealDetails = mealDetails)
        } catch (e: Exception) {
            MealDetailsViewState(error = e.message ?: "An unexpected error occurred")
        }
    }

    fun toggleExpand() {
        _isExpand.value = !_isExpand.value
    }
}

data class MealDetailsViewState(
    val mealDetails: MealDetails? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)