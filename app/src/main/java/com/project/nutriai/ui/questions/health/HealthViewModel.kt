package com.project.nutriai.ui.questions.health

import com.project.domain.model.HealthConditions
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.ui.questions.nutri_object.Answer
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor() : BaseViewModel() {

    private val _healths = MutableStateFlow(
        value = listOf(
            Answer(4, R.string.obesity),
            Answer(5, R.string.cardiovascular_diseases),
            Answer(6, R.string.irritable_bowel_syndrome),
            Answer(7, R.string.kidney_disease),
            Answer(8, R.string.osteoporosis),
            Answer(9, R.string.arthritis),
            Answer(10, R.string.anemia),
            Answer(11, R.string.lactose_intolerance)
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
        val healths = _healths.value.filter { it.isSelected }.map {
            when (it.id) {
                4 -> HealthConditions.OBESITY
                5 -> HealthConditions.CARDIOVASCULAR_DISEASE
                6 -> HealthConditions.IBS
                7 -> HealthConditions.KIDNEY_DISEASE
                8 -> HealthConditions.OSTEOPOROSIS
                9 -> HealthConditions.ARTHRITIS
                10 -> HealthConditions.ANEMIA
                else -> HealthConditions.LACTOSE_INTOLERANCE
            }
        }
        AppPref.userDetail = AppPref.userDetail.copy(healthConditions = healths)
    }

    fun onHealthSelected(healthConditions: List<HealthConditions>) {
        val ids = healthConditions.map {
            when (it) {
                HealthConditions.OBESITY -> 4
                HealthConditions.CARDIOVASCULAR_DISEASE -> 5
                HealthConditions.IBS -> 6
                HealthConditions.KIDNEY_DISEASE -> 7
                HealthConditions.OSTEOPOROSIS -> 8
                HealthConditions.ARTHRITIS -> 9
                HealthConditions.ANEMIA -> 10
                else -> 11
            }
        }
        _healths.value = _healths.value.map {
            it.copy(isSelected = ids.contains(it.id))
        }
    }
}