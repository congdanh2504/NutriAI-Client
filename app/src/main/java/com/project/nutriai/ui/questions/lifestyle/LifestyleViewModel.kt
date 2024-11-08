package com.project.nutriai.ui.questions.lifestyle

import com.project.domain.model.PhysicalActivity
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LifestyleViewModel @Inject constructor() : BaseViewModel() {
    private val _lifestyles = MutableStateFlow(
        value = listOf(
            Answer(1, R.string.don_t_exercise, true),
            Answer(2, R.string.light_exercise),
            Answer(3, R.string.moderate_exercise),
            Answer(4, R.string.heavy_exercise),
        )
    )
    val lifestyles = _lifestyles.asStateFlow()

    fun onLifestyleSelected(lifestyle: Answer) {
        _lifestyles.value = _lifestyles.value.map {
            it.copy(isSelected = it.id == lifestyle.id)
        }
        val lifestyle = when (lifestyle.id) {
            1 -> PhysicalActivity.NO_EXERCISE
            2 -> PhysicalActivity.LIGHT
            3 -> PhysicalActivity.MODERATE
            else -> PhysicalActivity.HEAVY
        }
        AppPref.userDetail = AppPref.userDetail.copy(physicalActivity = lifestyle)
    }

    fun onLifestyleSelected(physicalActivity: PhysicalActivity) {
        val id = when (physicalActivity) {
            PhysicalActivity.NO_EXERCISE -> 1
            PhysicalActivity.LIGHT -> 2
            PhysicalActivity.MODERATE -> 3
            else -> 4
        }
        _lifestyles.value = _lifestyles.value.map {
            it.copy(isSelected = it.id == id)
        }
    }
}
