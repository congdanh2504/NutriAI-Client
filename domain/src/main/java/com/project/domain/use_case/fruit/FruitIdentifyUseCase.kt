package com.project.domain.use_case.fruit

import com.project.domain.repository.FruitIdentificationRepository
import java.io.File
import javax.inject.Inject

class FruitIdentifyUseCase @Inject constructor(
    private val fruitIdentificationRepository: FruitIdentificationRepository
) {
    suspend operator fun invoke(file: File) =
        fruitIdentificationRepository.identifyFruit(file)
}
