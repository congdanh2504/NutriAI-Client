package com.project.nutriai.ui.questions.gender

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.nutriai.R
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor() : BaseViewModel() {
    private val _genderList = MutableStateFlow(
        listOf(
            Gender(R.string.male, R.drawable.img_male, true),
            Gender(R.string.female, R.drawable.img_female)
        )
    )
    val genderList = _genderList.asStateFlow()

    fun setGenderSelected(type: Int) {
        _genderList.value = _genderList.value.map {
            it.copy(selected = it.type == type)
        }
        val gender = if (type == R.string.male)
            com.project.domain.model.Gender.MALE
        else
            com.project.domain.model.Gender.FEMALE
        AppPref.userDetail = AppPref.userDetail.copy(gender = gender)
    }
}

data class Gender(
    @StringRes val type: Int,
    @DrawableRes val img: Int,
    val selected: Boolean = false
)