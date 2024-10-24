package com.project.domain.model

import com.project.data.source.remote.dto.UserDetailResponse

data class UserDetail(
    val email: String,
    val username: String
)

fun UserDetailResponse.toUserDetail() = UserDetail(email, username)