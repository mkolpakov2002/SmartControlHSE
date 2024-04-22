package ru.hse.smart_control.domain.connection.http.yandex

import ru.hse.miem.yandexsmarthomeapi.entity.YandexApiResponse

sealed interface YandexSmartHomeAPIService {

    val yandexSmartHomeAPIClient: YandexSmartHomeAPIClient

    suspend fun getUserInfo(): YandexApiResponse?

    suspend fun getDeviceState(deviceId: String): YandexApiResponse?

}