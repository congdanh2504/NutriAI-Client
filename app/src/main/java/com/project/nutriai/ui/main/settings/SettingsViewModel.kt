package com.project.nutriai.ui.main.settings

import com.project.data.utils.TokenPref
import com.project.domain.model.UserDetail
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel() {

    fun logout() {
        AppPref.userDetail = UserDetail.EMPTY
        TokenPref.accessToken = ""
        TokenPref.refreshToken = ""
    }
}