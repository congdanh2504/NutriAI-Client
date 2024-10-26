package com.project.nutriai.ui.questions.weight_height

import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeightHeightViewModel @Inject constructor() : BaseViewModel() {

    fun updateWeight(weight: Int) {
        AppPref.userDetail = AppPref.userDetail.copy(weight = weight)
    }

    fun updateHeight(height: Int) {
        AppPref.userDetail = AppPref.userDetail.copy(height = height)
    }
}