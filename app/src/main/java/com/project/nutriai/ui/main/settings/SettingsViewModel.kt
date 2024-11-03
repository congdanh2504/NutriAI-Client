package com.project.nutriai.ui.main.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.project.data.utils.TokenPref
import com.project.domain.model.UserDetail
import com.project.nutriai.ui.base.BaseViewModel
import com.project.nutriai.utils.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel() {

    private val _languageStream = MutableStateFlow(LanguageEnum.ENGLISH)
    val languageStream = _languageStream.asStateFlow()

    init {
        val currentLanguageCode = if (!AppCompatDelegate.getApplicationLocales().isEmpty) {
            AppCompatDelegate.getApplicationLocales()[0]?.language
        } else {
            null
        }
        _languageStream.value = if (currentLanguageCode == "vi") {
            LanguageEnum.VIETNAMESE
        } else {
            LanguageEnum.ENGLISH
        }
    }

    fun changeLanguage() {
        val languageCode = if (_languageStream.value == LanguageEnum.ENGLISH) { "vi" } else { "en" }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        _languageStream.value = if (_languageStream.value == LanguageEnum.ENGLISH) {
            LanguageEnum.VIETNAMESE
        } else {
            LanguageEnum.ENGLISH
        }
    }

    fun logout() {
        AppPref.userDetail = UserDetail.EMPTY
        TokenPref.accessToken = ""
        TokenPref.refreshToken = ""
    }
}

enum class LanguageEnum {
    ENGLISH,
    VIETNAMESE
}
