package com.project.nutriai.ui.splash

import androidx.lifecycle.viewModelScope
import com.project.domain.model.UserDetail
import com.project.domain.use_case.GetCurrentUserUseCase
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {

    private val _isLogin = MutableStateFlow<Boolean?>(null)
    val isLogin = _isLogin.asStateFlow()

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val userDetail = getCurrentUserUseCase()
                AppPref.userDetail = userDetail
                _isLogin.value = true
            } catch (e: Exception) {
                _isLogin.value = false
            }
        }
    }
}
