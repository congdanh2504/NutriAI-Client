package com.project.domain.repository

import com.project.domain.model.FruitNutrition
import java.io.File

interface FruitIdentificationRepository {
    suspend fun identifyFruit(file: File): FruitNutrition
}
