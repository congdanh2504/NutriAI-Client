package com.project.nutriai.ui.questions

import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor() : BaseViewModel() {

    val questions = listOf(
        R.string.question_full_name_age,
        R.string.question_gender,
        R.string.question_weight_height,
        R.string.question_health_goal,
        R.string.question_diet_plan,
        R.string.question_physical_activity,
        R.string.question_health_issues
    )
}