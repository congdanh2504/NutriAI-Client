package com.project.nutriai.ui.login

import androidx.lifecycle.viewModelScope
import com.project.data.utils.TokenPref
import com.project.domain.use_case.auth.GetUserDetail
import com.project.domain.use_case.auth.LoginUseCase
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserDetail: GetUserDetail
) : BaseViewModel() {

    private val _loginStatus = MutableStateFlow(LoginViewState.EMPTY)
    val loginStatus = _loginStatus.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginStatus.value = LoginViewState(isLoading = true)
        var hasAnsweredSurvey = false
        val isSuccess = try {
            val loginResponse = loginUseCase(email, password)
            TokenPref.accessToken = loginResponse.accessToken
            TokenPref.refreshToken = loginResponse.refreshToken
            hasAnsweredSurvey = loginResponse.hasAnsweredSurvey
            if (hasAnsweredSurvey) {
                val userDetail = getUserDetail()
                AppPref.userDetail = userDetail
            }
            true
        } catch (e: Exception) {
            false
        }
        if (isSuccess) {
            _loginStatus.value =
                LoginViewState(isSuccess = true, hasAnsweredSurvey = hasAnsweredSurvey)
        } else {
            _loginStatus.value = LoginViewState(error = "Failed to login")
        }
    }
}

data class LoginViewState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val hasAnsweredSurvey: Boolean = false,
    val error: String = ""
) {
    companion object {
        val EMPTY = LoginViewState()
    }
}