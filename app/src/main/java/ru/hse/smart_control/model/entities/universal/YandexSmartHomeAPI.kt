package ru.hse.smart_control.model.entities.universal

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder

import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.hse.smart_control.model.entities.universal.scheme.ApiResponse
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceStateResponse
import ru.hse.smart_control.model.entities.universal.scheme.ErrorModel
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.UserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityParameterObject

object YandexSmartHomeAPI {
    private const val BASE_URL = "https://api.iot.yandex.net/v1.0"
    private val client = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
//        install(ContentNegotiation) {
//            json
//        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
    private val json = Json

    suspend fun getUserInfo(accessToken: String): ApiResponse? {
        return try {
            val request = HttpRequestBuilder().apply {
                method = HttpMethod.Get
                url("$BASE_URL/user/info")
                header("Authorization", "Bearer $accessToken")
            }
            Log.d("SmartHomeAPI", "Outgoing request: $request")
            val response = client.request(request)
            val responseBody = response.bodyAsText()
            Log.d("SmartHomeAPI", "User info response: $responseBody")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val successResponse = json.decodeFromString<UserInfoResponse>(responseBody)
                    ApiResponse.SuccessUserInfo(successResponse)
                }
                else -> {
                    val errorResponse = json.decodeFromString<ErrorModel>(responseBody)
                    ApiResponse.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            Log.e("YandexSmartHomeAPI", "Error getting user info: $e")
            null
        }
    }

    suspend fun getDeviceState(accessToken: String, deviceId: String): ApiResponse? {
        return try {
            val request = HttpRequestBuilder().apply {
                method = HttpMethod.Get
                url("$BASE_URL/devices/$deviceId")
                header("Authorization", "Bearer $accessToken")
            }
            Log.d("SmartHomeAPI", "Outgoing request: $request")
            val response = client.request(request)
            val responseBody = response.bodyAsText()
            Log.d("SmartHomeAPI", "User info response: $responseBody")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val successResponse = json.decodeFromString<DeviceStateResponse>(responseBody)
                    ApiResponse.SuccessDeviceState(successResponse)
                }
                else -> {
                    val errorResponse = json.decodeFromString<ErrorModel>(responseBody)
                    ApiResponse.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            Log.e("YandexSmartHomeAPI", "Error getting user info: $e")
            null
        }
    }

}