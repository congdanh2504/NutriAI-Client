package com.project.domain.use_case.meal

import com.project.domain.model.MealDetails
import com.project.domain.repository.LocalRepository
import javax.inject.Inject

class AddFavoriteMealUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(mealDetails: MealDetails) {
        localRepository.insertMealDetail(mealDetails)
    }
}