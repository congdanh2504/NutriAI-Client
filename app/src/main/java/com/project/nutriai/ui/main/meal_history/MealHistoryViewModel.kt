package com.project.nutriai.ui.main.meal_history

import androidx.lifecycle.viewModelScope
import com.project.domain.model.HistoryMeal
import com.project.domain.repository.MealRepository
import com.project.domain.use_case.meal.GetHistoryMealUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MealHistoryViewModel @Inject constructor(
    private val getHistoryMealUseCase: GetHistoryMealUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(MealHistoryViewState())
    val state = _state.asStateFlow()

    fun getHistoryMeals(startDate: String?, endDate: String?) = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val historyMeals = try {
            getHistoryMealUseCase(startDate, endDate)
        } catch (e: Exception) {
            emptyList()
        }
        val mealHistoryByDays = historyMeals.groupBy { it.date }
            .map { (date, historyMeals) -> MealHistoryByDay(date, historyMeals) }
            .sortedByDescending {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = sdf.parse(it.date)
                date?.time ?: 0
            }

        _state.value = _state.value.copy(isLoading = false, historyMeals = mealHistoryByDays)
    }
}

data class MealHistoryViewState(
    val isLoading: Boolean = false,
    val historyMeals: List<MealHistoryByDay> = emptyList()
)

data class MealHistoryByDay(
    val date: String,
    val historyMeals: List<HistoryMeal>
) {
    val totalCalories: Int
        get() = historyMeals.sumOf { it.nutritionInfo.calories }
}
