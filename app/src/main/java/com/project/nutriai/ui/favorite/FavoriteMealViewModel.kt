package com.project.nutriai.ui.favorite

import androidx.lifecycle.viewModelScope
import com.project.domain.use_case.meal.GetFavoriteMealsUseCase
import com.project.nutriai.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteMealViewModel @Inject constructor(
    private val getFavoriteMealsUseCase: GetFavoriteMealsUseCase
) : BaseViewModel() {

    val favoriteMeals = getFavoriteMealsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
}