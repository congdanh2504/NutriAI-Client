package com.project.domain.use_case

import com.project.data.repository.AuthRepository
import com.project.domain.model.UserDetail
import com.project.domain.model.toUserDetail
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): UserDetail {
        return authRepository.getCurrentUser().toUserDetail()
    }
}