package com.project.nutriai.ui.questions

import androidx.lifecycle.viewModelScope
import com.project.domain.use_case.auth.UpdateUserDetail
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val updateUserDetail: UpdateUserDetail
) : BaseViewModel() {

    val questions = listOf(
        R.string.question_full_name_age,
        R.string.question_gender,
        R.string.question_weight_height,
        R.string.question_health_goal,
        R.string.question_diet_plan,
        R.string.question_allergies,
        R.string.question_physical_activity,
        R.string.question_health_issues
    )

    private val _updateStatus = MutableStateFlow<Boolean?>(null)
    val updateStatus = _updateStatus.asStateFlow()

    fun updateUserDetail() = viewModelScope.launch {
        try {
            updateUserDetail(AppPref.userDetail)
            _updateStatus.value = true
        } catch (e: Exception) {
            _updateStatus.value = false
        }
    }
}