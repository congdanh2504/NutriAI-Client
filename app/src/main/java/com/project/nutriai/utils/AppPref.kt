package com.project.nutriai.utils

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref
import com.project.domain.model.UserDetail

object AppPref : KotprefModel() {
    var userDetail by gsonPref {
        UserDetail.EMPTY
    }
}