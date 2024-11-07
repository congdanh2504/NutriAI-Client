package com.project.domain.use_case.meal

import com.project.domain.repository.MealRepository
import javax.inject.Inject

class DeleteHistoryMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(id: String) {
        mealRepository.deleteHistoryMeal(id)
    }
}