package com.project.domain.use_case.auth

import com.project.domain.model.UserDetail
import com.project.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateUserDetail @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(userDetail: UserDetail): UserDetail {
        return authRepository.updateUserDetail(userDetail)
    }
}