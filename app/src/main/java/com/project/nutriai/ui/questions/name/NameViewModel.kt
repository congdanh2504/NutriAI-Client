package com.project.nutriai.ui.questions.name

import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(): BaseViewModel() {

    fun updateName(name: String) {
        AppPref.userDetail = AppPref.userDetail.copy(fullName = name)
    }

    fun updateAge(age: Int) {
        AppPref.userDetail = AppPref.userDetail.copy(age = age)
    }
}
