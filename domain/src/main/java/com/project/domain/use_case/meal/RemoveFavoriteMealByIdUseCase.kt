package com.project.domain.use_case.meal

import com.project.domain.repository.LocalRepository
import javax.inject.Inject

class RemoveFavoriteMealByIdUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(id: String) {
        localRepository.removeMealDetail(id)
    }
}