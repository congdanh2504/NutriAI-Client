package com.project.domain.model

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val hasAnsweredSurvey: Boolean
)