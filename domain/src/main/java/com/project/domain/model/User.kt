package com.project.domain.model

import com.project.data.source.remote.dto.UserDetailNetwork
import com.project.data.source.remote.dto.UserResponse

data class User(
    val email: String,
    val username: String,
    val hasAnsweredSurvey: Boolean
)

fun UserResponse.toUserDetail() = User(email, username, hasAnsweredSurvey)

data class UserDetail(
    val fullName: String,
    val age: Int,
    val gender: Gender,
    val weight: Int,
    val height: Int,
    val nutritionGoal: NutritionGoal,
    val dietPreference: DietPreference,
    val foodAllergies: List<FoodAllergies>,
    val physicalActivity: PhysicalActivity,
    val healthConditions: List<HealthConditions>
) {
    companion object {
        val EMPTY = UserDetail(
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

fun UserDetail.toUserDetailNetwork() = UserDetailNetwork(
    fullName,
    age,
    gender.name,
    weight,
    height,
    nutritionGoal.name,
    dietPreference.name,
    foodAllergies.map { it.name },
    physicalActivity.name,
    healthConditions.map { it.name }
)

fun UserDetailNetwork.toUserDetail() = UserDetail(
    fullName,
    age,
    Gender.valueOf(gender),
    weight,
    height,
    NutritionGoal.valueOf(nutritionGoal),
    DietPreference.valueOf(dietPreference),
    foodAllergies.map { FoodAllergies.valueOf(it) },
    PhysicalActivity.valueOf(physicalActivity),
    healthConditions.map { HealthConditions.valueOf(it) }
)

enum class Gender {
    MALE, FEMALE
}

enum class NutritionGoal {
    GAIN_WEIGHT, LOSE_WEIGHT, MAINTAIN_WEIGHT, IMPROVE_HEALTH, BUILD_MUSCLE
}

enum class FoodAllergies {
    GLUTEN, LACTOSE, SEAFOOD, PEANUTS, NUTS, EGGS, SOY
}

enum class DietPreference {
    VEGETARIAN, VEGAN, KETO, PALEO, NONE
}

enum class HealthConditions {
    OBESITY, CARDIOVASCULAR_DISEASE, IBS, KIDNEY_DISEASE, OSTEOPOROSIS, ARTHRITIS, ANEMIA, LACTOSE_INTOLERANCE
}

enum class PhysicalActivity {
    NO_EXERCISE, LIGHT, MODERATE, HEAVY
}