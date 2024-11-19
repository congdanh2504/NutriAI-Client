package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.project.data.mapper.toDomain
import com.project.domain.model.Analysis

data class AnalysisNetwork(
    @SerializedName("nutrient_totals")
    val nutrientTotals: List<NutritionInfoNetwork>
)

fun AnalysisNetwork.toDomain(): Analysis {
    return Analysis(
        nutritionInfos = nutrientTotals.map { it.toDomain() }
    )
}
