package com.project.domain.use_case.meal

import com.project.domain.repository.MealRepository
import javax.inject.Inject

class GetAnalysisUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {

    suspend operator fun invoke(startDate: String?, endDate: String?) = mealRepository.getAnalysis(startDate, endDate)
}
