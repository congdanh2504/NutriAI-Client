package com.project.data.mapper

import com.project.data.source.remote.dto.LoginResponseNetwork
import com.project.domain.model.LoginResponse

fun LoginResponseNetwork.toDomain() = LoginResponse(
    accessToken = accessToken,
    refreshToken = refreshToken,
    hasAnsweredSurvey = hasAnsweredSurvey
)