package ru.hse.smart_control.model.repository

import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel

interface UserRepository {

    // Network
    suspend fun register(user: RegisterModel)
    suspend fun loginUser(user: LoginModel): TokenModel

}