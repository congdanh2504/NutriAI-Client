package com.project.domain.use_case

import com.project.data.repository.AuthRepository
import com.project.domain.model.UserDetail
import com.project.domain.model.toUserDetailNetwork
import javax.inject.Inject

class UpdateUserDetail @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(userDetail: UserDetail) {
        authRepository.updateUserDetail(userDetail.toUserDetailNetwork())
    }
}