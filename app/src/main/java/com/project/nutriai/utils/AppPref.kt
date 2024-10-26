package com.project.nutriai.utils

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref
import com.project.domain.model.DietPreference
import com.project.domain.model.Gender
import com.project.domain.model.NutritionGoal
import com.project.domain.model.PhysicalActivity
import com.project.domain.model.UserDetail

object AppPref : KotprefModel() {
    var userDetail by gsonPref {
        UserDetail(
            fullName = "",
            age = 0,
            gender = Gender.MALE,
            weight = 0,
            height = 0,
            nutritionGoal = NutritionGoal.GAIN_WEIGHT,
            dietPreference = DietPreference.NONE,
            foodAllergies = emptyList(),
            physicalActivity = PhysicalActivity.HEAVY,
            healthConditions = emptyList()
        )
    }
}