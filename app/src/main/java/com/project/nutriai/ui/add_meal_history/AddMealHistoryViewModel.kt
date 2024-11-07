package com.project.nutriai.ui.add_meal_history

import androidx.lifecycle.viewModelScope
import com.project.domain.model.HistoryMeal
import com.project.domain.use_case.meal.AddHistoryMealUseCase
import com.project.domain.use_case.meal.DeleteHistoryMealUseCase
import com.project.domain.use_case.meal.GetMealDetailsByIdUseCase
import com.project.domain.use_case.meal.SearchMealsUseCase
import com.project.domain.use_case.meal.UpdateHistoryMealUseCase
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.meal_detail.MealDetailsViewState
import com.project.nutriai.ui.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMealHistoryViewModel @Inject constructor(
    private val searchMealsUseCase: SearchMealsUseCase,
    private val getMealDetailsByIdUseCase: GetMealDetailsByIdUseCase,
    private val addHistoryMealUseCase: AddHistoryMealUseCase,
    private val deleteHistoryMealUseCase: DeleteHistoryMealUseCase,
    private val updateHistoryMealUseCase: UpdateHistoryMealUseCase
) : BaseViewModel() {

    private val _searchResult = MutableStateFlow(SearchState())
    val searchResult = _searchResult.asStateFlow()

    private val _mealDetails = MutableStateFlow(MealDetailsViewState())
    val mealDetails = _mealDetails.asStateFlow()

    private val _addMealHistory = MutableStateFlow<AddMealHistoryViewState?>(null)
    val addMealHistory = _addMealHistory.asStateFlow()

    fun searchMeals(name: String?) {
        _searchResult.value = SearchState(isLoading = true)
        viewModelScope.launch {
            val result = try {
                searchMealsUseCase(name = name)
            } catch (e: Exception) {
                emptyList()
            }
            _searchResult.value = SearchState(searchResult = result)
        }
    }

    fun getMealDetailsById(id: String) {
        _mealDetails.value = _mealDetails.value.copy(isLoading = true)
        viewModelScope.launch {
            _mealDetails.value = try {
                val mealDetails = getMealDetailsByIdUseCase(id)
                MealDetailsViewState(mealDetails = mealDetails)
            } catch (e: Exception) {
                MealDetailsViewState(error = e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun addHistoryMeal(historyMeal: HistoryMeal) {
        _addMealHistory.value = AddMealHistoryViewState(isLoading = true)
        viewModelScope.launch {
            try {
                addHistoryMealUseCase(historyMeal)
                _addMealHistory.value = AddMealHistoryViewState()
            } catch (e: Exception) {
                _addMealHistory.value =
                    AddMealHistoryViewState(error = e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun updateHistoryMeal(historyMeal: HistoryMeal) {
        _addMealHistory.value = AddMealHistoryViewState(isLoading = true)
        viewModelScope.launch {
            try {
                updateHistoryMealUseCase(historyMeal)
                _addMealHistory.value = AddMealHistoryViewState()
            } catch (e: Exception) {
                _addMealHistory.value =
                    AddMealHistoryViewState(error = e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun deleteHistoryMeal(id: String) {
        _addMealHistory.value = AddMealHistoryViewState(isLoading = true)
        viewModelScope.launch {
            try {
                deleteHistoryMealUseCase(id)
                _addMealHistory.value = AddMealHistoryViewState()
            } catch (e: Exception) {
                _addMealHistory.value =
                    AddMealHistoryViewState(error = e.message ?: "An unexpected error occurred")
            }
        }
    }
}

data class AddMealHistoryViewState(
    val isLoading: Boolean = false,
    val error: String = ""
)
