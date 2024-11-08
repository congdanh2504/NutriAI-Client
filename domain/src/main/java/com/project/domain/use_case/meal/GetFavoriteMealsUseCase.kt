package com.project.domain.use_case.meal

import com.project.domain.repository.LocalRepository
import javax.inject.Inject

class GetFavoriteMealsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    operator fun invoke() = localRepository.getMealDetails()
}