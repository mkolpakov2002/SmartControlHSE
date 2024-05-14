package ru.hse.smart_control.domain.connection.http.yandex

import ru.hse.miem.yandexsmarthomeapi.entity.YandexApiResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateRequest

interface YandexSmartHomeApi {
    suspend fun getUserInfo(): YandexApiResponse
    suspend fun getDeviceState(deviceId: String): YandexApiResponse
    suspend fun manageDeviceCapabilitiesState(request: YandexManageDeviceCapabilitiesStateRequest): YandexApiResponse
    suspend fun manageGroupCapabilitiesState(groupId: String, request: YandexManageGroupCapabilitiesStateRequest): YandexApiResponse
    suspend fun getDeviceGroup(groupId: String): YandexApiResponse
}