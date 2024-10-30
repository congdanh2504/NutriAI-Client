package com.project.nutriai.extensions

import android.content.Context
import com.project.domain.model.DietPreference
import com.project.domain.model.FoodAllergies
import com.project.domain.model.Gender
import com.project.domain.model.HealthConditions
import com.project.domain.model.NutritionGoal
import com.project.domain.model.PhysicalActivity
import com.project.nutriai.R

fun Gender.toReadableString(context: Context): String {
    return when (this) {
        Gender.MALE -> context.getString(R.string.male)
        Gender.FEMALE -> context.getString(R.string.female)
    }
}

fun NutritionGoal.toReadableString(context: Context): String {
    return when (this) {
        NutritionGoal.GAIN_WEIGHT -> context.getString(R.string.weight_gain)
        NutritionGoal.LOSE_WEIGHT -> context.getString(R.string.losing_weight)
        NutritionGoal.MAINTAIN_WEIGHT -> context.getString(R.string.maintain_current_weight)
        NutritionGoal.IMPROVE_HEALTH -> context.getString(R.string.improve_overall_health)
        NutritionGoal.BUILD_MUSCLE -> context.getString(R.string.muscle_gain)
    }
}

fun FoodAllergies.toReadableString(context: Context): String {
    return when (this) {
        FoodAllergies.GLUTEN -> context.getString(R.string.gluten_allergy)
        FoodAllergies.LACTOSE -> context.getString(R.string.lactose_intolerance)
        FoodAllergies.SEAFOOD -> context.getString(R.string.seafood_allergy)
        FoodAllergies.PEANUTS -> context.getString(R.string.peanut_allergy)
        FoodAllergies.NUTS -> context.getString(R.string.nut_allergy)
        FoodAllergies.EGGS -> context.getString(R.string.egg_allergy)
        FoodAllergies.SOY -> context.getString(R.string.soy_allergy)
    }
}

fun DietPreference.toReadableString(context: Context): String {
    return when (this) {
        DietPreference.VEGETARIAN -> context.getString(R.string.vegetarian)
        DietPreference.VEGAN -> context.getString(R.string.be_vegetarian)
        DietPreference.KETO -> context.getString(R.string.keto)
        DietPreference.PALEO -> context.getString(R.string.paleo)
        else -> context.getString(R.string.there_is_no_specific_diet)
    }
}

fun HealthConditions.toReadableString(context: Context): String {
    return when (this) {
        HealthConditions.OBESITY -> context.getString(R.string.obesity)
        HealthConditions.CARDIOVASCULAR_DISEASE -> context.getString(R.string.cardiovascular_diseases)
        HealthConditions.IBS -> context.getString(R.string.irritable_bowel_syndrome)
        HealthConditions.KIDNEY_DISEASE -> context.getString(R.string.kidney_disease)
        HealthConditions.OSTEOPOROSIS -> context.getString(R.string.osteoporosis)
        HealthConditions.ARTHRITIS -> context.getString(R.string.arthritis)
        HealthConditions.ANEMIA -> context.getString(R.string.anemia)
        HealthConditions.LACTOSE_INTOLERANCE -> context.getString(R.string.lactose_intolerance)
    }
}

fun PhysicalActivity.toReadableString(context: Context): String {
    return when (this) {
        PhysicalActivity.NO_EXERCISE -> context.getString(R.string.don_t_exercise)
        PhysicalActivity.LIGHT -> context.getString(R.string.light_exercise)
        PhysicalActivity.MODERATE -> context.getString(R.string.moderate_exercise)
        PhysicalActivity.HEAVY -> context.getString(R.string.heavy_exercise)
    }
}
