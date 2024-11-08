package com.project.data.source.remote.model

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)