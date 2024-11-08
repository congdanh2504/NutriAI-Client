package com.project.nutriai.ui.questions.allergies

import com.project.domain.model.FoodAllergies
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AllergyViewModel @Inject constructor() : BaseViewModel() {

    private val _allergies = MutableStateFlow(
        value = listOf(
            Answer(1, R.string.gluten_allergy),
            Answer(2, R.string.lactose_intolerance),
            Answer(3, R.string.seafood_allergy),
            Answer(4, R.string.peanut_allergy),
            Answer(5, R.string.nut_allergy),
            Answer(6, R.string.egg_allergy),
            Answer(7, R.string.soy_allergy),
        )
    )
    val allergies = _allergies.asStateFlow()

    fun onAllergySelected(allergy: Answer) {
        _allergies.value = _allergies.value.map {
            if (it.id == allergy.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        val allergies = _allergies.value.filter { it.isSelected }.map {
            when (it.id) {
                1 -> FoodAllergies.GLUTEN
                2 -> FoodAllergies.LACTOSE
                3 -> FoodAllergies.SEAFOOD
                4 -> FoodAllergies.PEANUTS
                5 -> FoodAllergies.NUTS
                6 -> FoodAllergies.EGGS
                else -> FoodAllergies.SOY
            }
        }
        AppPref.userDetail = AppPref.userDetail.copy(foodAllergies = allergies)
    }

    fun onAllergySelected(foodAllergies: List<FoodAllergies>) {
        val ids = foodAllergies.map {
            when (it) {
                FoodAllergies.GLUTEN -> 1
                FoodAllergies.LACTOSE -> 2
                FoodAllergies.SEAFOOD -> 3
                FoodAllergies.PEANUTS -> 4
                FoodAllergies.NUTS -> 5
                FoodAllergies.EGGS -> 6
                else -> 7
            }
        }
        _allergies.value = _allergies.value.map {
            it.copy(isSelected = ids.contains(it.id))
        }
    }
}