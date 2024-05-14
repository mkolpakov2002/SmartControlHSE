package ru.hse.smart_control.domain

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.hse.smart_control.domain.connection.http.nest.RetrofitInstance
import ru.hse.smart_control.model.repository.UserRepository
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel

//import kotlin.Result

class UserRepositoryImpl : UserRepository {

    override suspend fun register(user: RegisterModel): Flow<ApiResponse<Unit>> = flow {
        try {
            val response = RetrofitInstance.nestApi.register(user)
            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body()!!))
            } else {
                val gson = Gson()
                val type = object : TypeToken<ApiResponseError>() {}.type
                val errorResponse: ApiResponseError =
                    gson.fromJson(response.errorBody()!!.charStream(), type)
                emit(ApiResponse.Failure(response.code(), errorResponse.message.joinToString("\n")))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(null, "Неизвестная ошибка"))
        }
    }

    override suspend fun register2(user: RegisterModel): Result<RegisterModel, DataError.Network> {
        return try {
            val response = RetrofitInstance.nestApi.register(user)
            Result.Success(user)
        } catch (e: HttpException) {
            when (e.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                409 -> Result.Error(DataError.Network.SUCH_USERNAME_EMAIL_EXIST)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun loginUser(user: LoginModel): Flow<ApiResponse<TokenModel>> = flow {
        try {
            val response = RetrofitInstance.nestApi.login(user)
            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body()!!))
            } else {
                val gson = Gson()
                val type = object : TypeToken<ApiResponseError>() {}.type
                val errorResponse: ApiResponseError =
                    gson.fromJson(response.errorBody()!!.charStream(), type)
                emit(ApiResponse.Failure(response.code(), errorResponse.message.joinToString("\n")))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(null, "Неизвестная ошибка"))
        }
    }
}