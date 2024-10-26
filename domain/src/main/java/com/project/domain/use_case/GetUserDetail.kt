package com.project.domain.use_case

import com.project.data.repository.AuthRepository
import com.project.domain.model.toUserDetail
import javax.inject.Inject

class GetUserDetail @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = authRepository.getUserDetail().toUserDetail()

}