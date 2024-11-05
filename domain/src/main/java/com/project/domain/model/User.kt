package com.project.domain.model

data class User(
    val email: String,
    val username: String,
    val hasAnsweredSurvey: Boolean
)

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