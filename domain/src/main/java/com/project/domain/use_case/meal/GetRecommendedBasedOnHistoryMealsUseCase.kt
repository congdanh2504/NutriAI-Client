package com.project.domain.use_case.meal

import com.project.domain.repository.MealRepository
import javax.inject.Inject

class GetRecommendedBasedOnHistoryMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke() = mealRepository.getRecommendedBasedOnHistoryMeals()
}