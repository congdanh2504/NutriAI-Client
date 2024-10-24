package com.project.nutriai.ui.register

import androidx.lifecycle.viewModelScope
import com.project.domain.use_case.RegisterUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    private val _registerViewState = MutableStateFlow(RegisterViewState.EMPTY)
    val registerViewState = _registerViewState

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            _registerViewState.value = RegisterViewState(isLoading = true)
            val isSuccess = registerUseCase(email, username, password)
            if (isSuccess) {
                _registerViewState.value = RegisterViewState(isSuccess = true)
            } else {
                _registerViewState.value = RegisterViewState(error = "Failed to register")
            }
        }
    }
}

data class RegisterViewState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = ""
) {
    companion object {
        val EMPTY = RegisterViewState()
    }
}