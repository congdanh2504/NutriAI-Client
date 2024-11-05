package com.project.domain.use_case.meal

import com.project.domain.model.Meal
import com.project.domain.repository.MealRepository
import javax.inject.Inject

class GetRecommendedMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(): List<Meal> {
        return mealRepository.getRecommendedMeals()
    }
}