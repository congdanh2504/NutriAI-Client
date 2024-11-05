package com.project.domain.use_case.meal

import com.project.domain.model.Category
import com.project.domain.repository.MealRepository
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(category: Category?, name: String?) =
        mealRepository.searchMeals(category?.name, name)
}