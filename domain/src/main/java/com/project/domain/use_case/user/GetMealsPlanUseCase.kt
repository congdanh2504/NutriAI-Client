package com.project.domain.use_case.user

import com.project.domain.repository.UserRepository
import javax.inject.Inject

class GetMealsPlanUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(date: String) = userRepository.getMealsPlan(date)
}
