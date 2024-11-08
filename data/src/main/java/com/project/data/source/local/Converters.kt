package com.project.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.domain.model.Ingredient
import com.project.domain.model.NutritionInfo

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String = gson.toJson(value)

    @TypeConverter
    fun toIngredientList(value: String): List<Ingredient> =
        gson.fromJson(value, object : TypeToken<List<Ingredient>>() {}.type)

    @TypeConverter
    fun fromNutritionInfo(value: NutritionInfo): String = gson.toJson(value)

    @TypeConverter
    fun toNutritionInfo(value: String): NutritionInfo =
        gson.fromJson(value, NutritionInfo::class.java)

    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
}