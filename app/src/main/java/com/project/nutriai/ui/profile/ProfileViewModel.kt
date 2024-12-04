package com.project.nutriai.ui.profile

import androidx.lifecycle.viewModelScope
import com.project.domain.model.TodayCalories
import com.project.domain.use_case.user.GetTodayCaloriesUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getTodayCaloriesUseCase: GetTodayCaloriesUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        getTodayCalories()
    }

    private fun getTodayCalories() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                _state.value =
                    _state.value.copy(todayCalories = getTodayCaloriesUseCase())
            } catch (e: Exception) {
                _state.value =
                    _state.value.copy(error = e.message ?: "An error occurred")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
    val todayCalories: TodayCalories? = null,
    val error: String = ""
)
