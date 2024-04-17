package ru.hse.smart_control.domain.connection.http.yandex

import ru.hse.miem.yandexsmarthomeapi.entity.ApiResponse

sealed interface YandexSmartHomeAPIService {

    val yandexSmartHomeAPIClient: YandexSmartHomeAPIClient

    suspend fun getUserInfo(): ApiResponse?

    suspend fun getDeviceState(deviceId: String): ApiResponse?

}