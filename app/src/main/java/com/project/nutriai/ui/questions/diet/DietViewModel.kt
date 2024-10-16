package com.project.nutriai.ui.questions.diet

import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
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
    }

}
