package com.project.data.utils

import com.chibatching.kotpref.KotprefModel

object TokenPref : KotprefModel() {
    var accessToken by stringPref("")
    var refreshToken by stringPref("")
}