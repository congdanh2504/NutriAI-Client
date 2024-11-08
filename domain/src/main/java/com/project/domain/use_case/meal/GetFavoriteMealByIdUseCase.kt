package com.project.domain.use_case.meal

import com.project.domain.repository.LocalRepository
import javax.inject.Inject

class GetFavoriteMealByIdUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    operator fun invoke(id: String) = localRepository.getMealDetailById(id)
}