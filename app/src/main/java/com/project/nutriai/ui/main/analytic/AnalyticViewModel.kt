package com.project.nutriai.ui.main.analytic

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.domain.model.Analysis
import com.project.domain.use_case.meal.GetAnalysisUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticViewModel @Inject constructor(
    private val getAnalysisUseCase: GetAnalysisUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(AnalyticViewState())
    val state = _state.asStateFlow()

    fun getAnalysis(startDate: String?, endDate: String?) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val analysis = try {
                getAnalysisUseCase(startDate, endDate)
            } catch (e: Exception) {
                Log.e("AnalyticViewModel", "getAnalysis: $e")
                null
            }
            _state.value = _state.value.copy(isLoading = false, analysis = analysis)
        }
    }

}

data class AnalyticViewState(
    val isLoading: Boolean = false,
    val analysis: Analysis? = null
)
