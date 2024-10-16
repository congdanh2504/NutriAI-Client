package com.project.nutriai.ui.questions.nutri_object

import androidx.annotation.StringRes
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NutriObjectViewModel @Inject constructor() : BaseViewModel() {
    private val _answers = MutableStateFlow(
        listOf(
            Answer(1, R.string.weight_gain, true),
            Answer(2, R.string.losing_weight),
            Answer(3, R.string.maintain_current_weight),
            Answer(4, R.string.improve_overall_health),
            Answer(5, R.string.muscle_gain)
        )
    )
    val answers = _answers.asStateFlow()

    fun onAnswerSelected(answer: Answer) {
        _answers.value = _answers.value.map {
            it.copy(isSelected = it.id == answer.id)
        }
    }
}

data class Answer(
    val id: Int,
    @StringRes
    val name: Int,
    val isSelected: Boolean = false
)