package com.project.nutriai.ui.fruit_identifier

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.domain.model.FruitNutrition
import com.project.domain.use_case.fruit.FruitIdentifyUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FruitIdentifierViewModel @Inject constructor(
    private val fruitIdentifyUseCase: FruitIdentifyUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(IdentificationState())
    val state = _state.asStateFlow()

    fun identifyFruit(file: File) {
        viewModelScope.launch {
            _state.value = IdentificationState.LOADING
            try {
                val fruitNutrition = fruitIdentifyUseCase(file)
                _state.value = IdentificationState(fruitNutrition = fruitNutrition)
            } catch (e: Exception) {
                Log.e("FruitIdentifierViewModel", "Error identifying fruit", e)
                _state.value = IdentificationState(error = e.message ?: "An error occurred")
            }
        }
    }
}

data class IdentificationState(
    val isLoading: Boolean = false,
    val fruitNutrition: FruitNutrition? = null,
    val error: String = ""
) {
    companion object {
        val LOADING = IdentificationState(isLoading = true)
    }
}
