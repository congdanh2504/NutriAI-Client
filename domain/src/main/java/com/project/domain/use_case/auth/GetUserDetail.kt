package com.project.domain.use_case.auth

import com.project.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserDetail @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = authRepository.getUserDetail()

}