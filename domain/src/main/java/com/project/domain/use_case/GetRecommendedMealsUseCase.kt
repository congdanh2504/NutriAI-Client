package com.project.domain.use_case

import com.project.data.repository.MealRepository
import com.project.domain.model.Meal
import com.project.domain.model.toMeal
import javax.inject.Inject

class GetRecommendedMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(): List<Meal> {
        return mealRepository.getRecommendedMeals().map { it.toMeal() }
    }
}