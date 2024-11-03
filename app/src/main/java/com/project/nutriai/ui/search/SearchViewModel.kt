package com.project.nutriai.ui.search

import androidx.lifecycle.viewModelScope
import com.project.domain.model.Category
import com.project.domain.model.Meal
import com.project.domain.use_case.meal.SearchMealsUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMealsUseCase: SearchMealsUseCase
) : BaseViewModel() {

    private val _searchResult = MutableStateFlow(SearchState())
    val searchResult = _searchResult.asStateFlow()

    fun searchMeals(category: Category?, query: String?) {
        _searchResult.value = SearchState(isLoading = true)
        viewModelScope.launch {
            val result = try {
                searchMealsUseCase(category, query)
            } catch (e: Exception) {
                emptyList()
            }
            _searchResult.value = SearchState(searchResult = result)
        }
    }
}

data class SearchState(
    val searchResult: List<Meal> = emptyList(),
    val isLoading: Boolean = false
)