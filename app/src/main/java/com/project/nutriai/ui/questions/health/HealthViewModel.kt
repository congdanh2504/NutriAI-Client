package com.project.nutriai.ui.questions.health

import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor() : BaseViewModel() {

    private val _healths = MutableStateFlow(
        value = listOf(
            Answer(1, R.string.diabetes),
            Answer(2, R.string.high_blood_pressure),
            Answer(3, R.string.high_cholesterol),
            Answer(4, R.string.obesity),
            Answer(5, R.string.cardiovascular_diseases),
            Answer(6, R.string.irritable_bowel_syndrome),
            Answer(7, R.string.kidney_disease),
            Answer(8, R.string.osteoporosis),
            Answer(9, R.string.arthritis),
            Answer(10, R.string.anemia),
        )
    )
    val healths = _healths.asStateFlow()

    fun onHealthSelected(health: Answer) {
        _healths.value = _healths.value.map {
            if (it.id == health.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
    }
}