package com.project.domain.use_case.meal

import com.project.domain.repository.MealRepository
import javax.inject.Inject

class GetHistoryMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(startDate: String?, endDate: String?) = mealRepository.getHistoryMeals(startDate, endDate)
}