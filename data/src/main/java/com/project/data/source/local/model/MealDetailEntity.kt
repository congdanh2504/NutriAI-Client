package com.project.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.domain.model.Ingredient
import com.project.domain.model.NutritionInfo

@Entity(tableName = "meal_detail")
data class MealDetailEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val ingredients: List<Ingredient>,
    val nutritionInfo: NutritionInfo,
    val suitableFor: List<String>,
    val category: String,
    val healthWarnings: List<String>,
    val imageUrl: String,
    val instructions: String
)