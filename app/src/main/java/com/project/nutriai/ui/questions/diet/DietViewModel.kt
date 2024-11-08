package com.project.nutriai.ui.questions.diet

import com.project.domain.model.DietPreference
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor() : BaseViewModel() {
    private val _diet = MutableStateFlow(
        value = listOf(
            Answer(1, R.string.vegetarian, true),
            Answer(2, R.string.be_vegetarian),
            Answer(3, R.string.keto),
            Answer(4, R.string.paleo),
            Answer(5, R.string.there_is_no_specific_diet)
        )
    )
    val diet = _diet.asStateFlow()

    fun onDietSelected(diet: Answer) {
        _diet.value = _diet.value.map {
            it.copy(isSelected = it.id == diet.id)
        }
        val diet = when (diet.id) {
            1 -> DietPreference.VEGETARIAN
            2 -> DietPreference.VEGAN
            3 -> DietPreference.KETO
            4 -> DietPreference.PALEO
            else -> DietPreference.NONE
        }
        AppPref.userDetail = AppPref.userDetail.copy(dietPreference = diet)
    }

    fun onDietSelected(dietPreference: DietPreference) {
        val id = when (dietPreference) {
            DietPreference.VEGETARIAN -> 1
            DietPreference.VEGAN -> 2
            DietPreference.KETO -> 3
            DietPreference.PALEO -> 4
            else -> 5
        }
        _diet.value = _diet.value.map {
            it.copy(isSelected = it.id == id)
        }
    }
}
