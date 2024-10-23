package com.project.nutriai.ui.questions.allergies

import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
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
    }

}