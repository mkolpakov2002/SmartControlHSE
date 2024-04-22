package ru.hse.smart_control.domain.connection.http.yandex

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.hse.miem.yandexsmarthomeapi.entity.YandexApiResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexErrorModelResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse

class YandexSmartHomeAPIServiceImpl(accessToken: String, baseUrl: String):
    YandexSmartHomeAPIService {
    override val yandexSmartHomeAPIClient: YandexSmartHomeAPIClient =
        YandexSmartHomeAPIClient(accessToken = accessToken, baseUrl = baseUrl)
    private val client = yandexSmartHomeAPIClient.httpClient
    private val baseUrl = yandexSmartHomeAPIClient.baseUrl
    private val logger = KotlinLogging.logger{}

    override suspend fun getUserInfo(): YandexApiResponse? {
        return try {
            val response = client.get("$baseUrl/user/info")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val successResponse = response.body<YandexUserInfoResponse>()
                    logger.debug { "Success response: $successResponse" }
                    YandexApiResponse.SuccessUserInfo(successResponse)
                }
                else -> {
                    val errorResponse = response.body<YandexErrorModelResponse>()
                    logger.debug { "Error response: $errorResponse" }
                    YandexApiResponse.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            logger.error { "Error getting user info: $e" }
            null
        }
    }

    override suspend fun getDeviceState(deviceId: String): YandexApiResponse? {
        return try {
            val response = client.get("$baseUrl/devices/$deviceId")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val successResponse = response.body<YandexDeviceStateResponse>()
                    logger.debug { "Success response: $successResponse" }
                    val result = YandexApiResponse.SuccessDeviceState(successResponse)
                    result
                }
                else -> {
                    val errorResponse = response.body<YandexErrorModelResponse>()
                    logger.debug { "Error response: $errorResponse" }
                    YandexApiResponse.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            logger.error { "Error getting device state info: $e" }
            null
        }
    }

}