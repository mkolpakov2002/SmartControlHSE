package ru.hse.smart_control.domain

import android.app.Application
import ru.hse.smart_control.domain.connection.http.nest.RetrofitInstance
import ru.hse.smart_control.model.repository.UserRepository
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel

class UserRepositoryImpl: UserRepository {

    override suspend fun register(user: RegisterModel) {
        RetrofitInstance.nestApi.register(user)
    }

    override suspend fun loginUser(user: LoginModel): TokenModel {
        return RetrofitInstance.nestApi.login(user)
    }

}