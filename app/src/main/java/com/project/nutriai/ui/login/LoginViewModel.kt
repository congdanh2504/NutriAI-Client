package com.project.nutriai.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.data.utils.TokenPref
import com.project.domain.use_case.LoginUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginStatus = MutableStateFlow(LoginViewState.EMPTY)
    val loginStatus = _loginStatus.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginStatus.value = LoginViewState(isLoading = true)
        val isSuccess = try {
            Log.d("LoginViewModel", "login: $email $password")
            val token = loginUseCase(email, password)
            TokenPref.accessToken = token.accessToken
            TokenPref.refreshToken = token.refreshToken
            true
        } catch (e: Exception) {
            false
        }
        if (isSuccess) {
            _loginStatus.value = LoginViewState(isSuccess = true)
        } else {
            _loginStatus.value = LoginViewState(error = "Failed to login")
        }
    }
}

data class LoginViewState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = ""
) {
    companion object {
        val EMPTY = LoginViewState()
    }
}