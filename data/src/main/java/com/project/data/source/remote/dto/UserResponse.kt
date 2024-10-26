package com.project.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val email: String,
    val username: String,
    @SerializedName("has_answered_survey")
    val hasAnsweredSurvey: Boolean = false
)