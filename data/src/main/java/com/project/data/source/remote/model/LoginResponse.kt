package com.project.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponseNetwork(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("has_answered_survey")
    val hasAnsweredSurvey: Boolean
)
