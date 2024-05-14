package ru.hse.smart_control.model.repository

import kotlinx.coroutines.flow.Flow
import ru.hse.smart_control.domain.ApiResponse
import ru.hse.smart_control.domain.DataError
import ru.hse.smart_control.domain.Result
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel

interface UserRepository {

    // Network
    suspend fun register(user: RegisterModel): Flow<ApiResponse<Unit>>

    suspend fun register2(user: RegisterModel): Result<RegisterModel, DataError.Network>

    suspend fun loginUser(user: LoginModel): Flow<ApiResponse<TokenModel>>

}