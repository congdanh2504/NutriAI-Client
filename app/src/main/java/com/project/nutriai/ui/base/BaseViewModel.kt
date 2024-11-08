package com.project.nutriai.ui.base

import androidx.lifecycle.ViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _profile = MutableStateFlow(AppPref.userDetail)
    val profile = _profile.asStateFlow()

    fun onProfileUpdated() {
        _profile.value = AppPref.userDetail.copy()
    }
}