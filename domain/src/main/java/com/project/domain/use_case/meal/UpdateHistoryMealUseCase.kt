package com.project.domain.use_case.meal

import com.project.domain.model.HistoryMeal
import com.project.domain.repository.MealRepository
import javax.inject.Inject

class UpdateHistoryMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(historyMeal: HistoryMeal) {
        mealRepository.updateHistoryMeal(historyMeal)
    }
}