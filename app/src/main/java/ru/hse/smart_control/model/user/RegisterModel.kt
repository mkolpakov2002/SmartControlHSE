package ru.hse.smart_control.model.user

data class RegisterModel(
    val username: String,
    val email: String,
    val password: String
)