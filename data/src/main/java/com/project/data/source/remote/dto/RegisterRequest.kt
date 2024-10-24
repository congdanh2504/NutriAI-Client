package com.project.data.source.remote.dto

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)