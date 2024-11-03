package com.project.nutriai.ui.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.domain.use_case.auth.GetCurrentUserUseCase
import com.project.domain.use_case.auth.GetUserDetail
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserDetail: GetUserDetail
) : BaseViewModel() {

    private val _isLogin = MutableStateFlow<Boolean?>(null)
    val isLogin = _isLogin.asStateFlow()

    private val _hasAnsweredSurvey = MutableStateFlow<Boolean?>(null)
    val hasAnsweredSurvey = _hasAnsweredSurvey.asStateFlow()

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val user = getCurrentUserUseCase()
                if (user.hasAnsweredSurvey) {
                    val userDetail = getUserDetail()
                    AppPref.userDetail = userDetail
                    Log.d("SplashViewModel", "getCurrentUser: $userDetail")
                    _hasAnsweredSurvey.value = true
                } else {
                    _hasAnsweredSurvey.value = false
                }
            } catch (e: Exception) {
                Log.e("SplashViewModel", "getCurrentUser: $e")
                _isLogin.value = false
            }
        }
    }
}
