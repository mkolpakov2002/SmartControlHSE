package ru.hse.smart_control.model.user

data class TokenModel(
    val accessToken: String,
    val expiresIn: Long
)