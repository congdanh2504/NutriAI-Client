package com.project.data.repository

import com.project.data.source.remote.AppApi
import com.project.data.source.remote.model.toDomain
import com.project.domain.model.FruitNutrition
import com.project.domain.repository.FruitIdentificationRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class FruitIdentificationRepositoryImpl @Inject constructor(
    private val appApi: AppApi
) : FruitIdentificationRepository {

    override suspend fun identifyFruit(file: File): FruitNutrition {
        val bodyObserve = MultipartBody.Part.createFormData(
            "file", file.name, file.asRequestBody("image/jpeg".toMediaType())
        )
        return appApi.predict(bodyObserve).toDomain()
    }
}
