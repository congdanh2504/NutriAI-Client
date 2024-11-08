package com.project.nutriai.ui.meal_detail

import androidx.lifecycle.viewModelScope
import com.project.domain.model.MealDetails
import com.project.domain.use_case.meal.AddFavoriteMealUseCase
import com.project.domain.use_case.meal.GetFavoriteMealByIdUseCase
import com.project.domain.use_case.meal.GetMealDetailsByIdUseCase
import com.project.domain.use_case.meal.RemoveFavoriteMealByIdUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val getMealDetailsByIdUseCase: GetMealDetailsByIdUseCase,
    private val addFavoriteMealUseCase: AddFavoriteMealUseCase,
    private val removeFavoriteMealByIdUseCase: RemoveFavoriteMealByIdUseCase,
    private val getFavoriteMealByIdUseCase: GetFavoriteMealByIdUseCase
) : BaseViewModel() {

    private val _mealDetails = MutableStateFlow(MealDetailsViewState())
    val mealDetails = _mealDetails.asStateFlow()

    private val _isExpand = MutableStateFlow(false)
    val isExpand = _isExpand.asStateFlow()

    private val _favoriteMeal = MutableStateFlow<MealDetails?>(null)
    val favoriteMeal = _favoriteMeal.asStateFlow()

    fun getFavoriteMealById(id: String) = viewModelScope.launch {
        getFavoriteMealByIdUseCase(id).collect {
            _favoriteMeal.value = it
        }
    }

    fun addFavoriteMeal(mealDetails: MealDetails) = viewModelScope.launch {
        addFavoriteMealUseCase(mealDetails)
    }

    fun removeFavoriteMealById(id: String) = viewModelScope.launch {
        removeFavoriteMealByIdUseCase(id)
    }

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