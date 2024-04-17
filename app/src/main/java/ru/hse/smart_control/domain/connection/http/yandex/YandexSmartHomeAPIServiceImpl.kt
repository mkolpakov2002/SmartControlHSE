package ru.hse.smart_control.domain.connection.http.yandex

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.hse.miem.yandexsmarthomeapi.entity.ApiResponse
import ru.hse.miem.yandexsmarthomeapi.entity.DeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.ErrorModelResponse
import ru.hse.miem.yandexsmarthomeapi.entity.UserInfoResponse

class YandexSmartHomeAPIServiceImpl(accessToken: String, baseUrl: String):
    YandexSmartHomeAPIService {
    override val yandexSmartHomeAPIClient: YandexSmartHomeAPIClient =
        YandexSmartHomeAPIClient(accessToken = accessToken, baseUrl = baseUrl)
    private val client = yandexSmartHomeAPIClient.httpClient
    private val baseUrl = yandexSmartHomeAPIClient.baseUrl
    private val logger = KotlinLogging.logger{}

    override suspend fun getUserInfo(): ApiResponse? {
        return try {
            val response = client.get("$baseUrl/user/info")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val successResponse = response.body<UserInfoResponse>()
                    logger.debug { "Success response: $successResponse" }
                    ApiResponse.SuccessUserInfo(successResponse)
                }
                else -> {
                    val errorResponse = response.body<ErrorModelResponse>()
                    logger.debug { "Error response: $errorResponse" }
                    ApiResponse.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            logger.error { "Error getting user info: $e" }
            null
        }
    }

    override suspend fun getDeviceState(deviceId: String): ApiResponse? {
        return try {
            val response = client.get("$baseUrl/devices/$deviceId")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val successResponse = response.body<DeviceStateResponse>()
                    logger.debug { "Success response: $successResponse" }
                    val result = ApiResponse.SuccessDeviceState(successResponse)
                    result
                }
                else -> {
                    val errorResponse = response.body<ErrorModelResponse>()
                    logger.debug { "Error response: $errorResponse" }
                    ApiResponse.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            logger.error { "Error getting device state info: $e" }
            null
        }
    }

}