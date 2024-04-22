package ru.hse.miem.yandexsmarthomeapi

import kotlinx.serialization.json.Json
import org.junit.Test
import ru.hse.miem.yandexsmarthomeapi.TestConstants.requestManageDeviceCapabilitiesJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.requestManageGroupCapabilitiesJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.responseDeviceStateJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.responseManageGroupCapabilitiesJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.responseUserInfoJson

import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse

class JsonParserUnitTest {

    private val json = Json {
        prettyPrint = true
    }

    @Test
    fun addition_requestDeviceGroupStateJson_isCorrect() {
        val deviceGroupStateRequest =
            json.decodeFromString<YandexManageGroupCapabilitiesStateRequest>(requestManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_responseDeviceStateJson_isCorrect() {
        val yandexDeviceStateResponse =
            json.decodeFromString<YandexDeviceStateResponse>(responseDeviceStateJson)
    }

    @Test
    fun addition_requestManageDeviceCapabilitiesJson_isCorrect() {
        val manageDeviceCapabilitiesRequest =
            json.decodeFromString<YandexManageDeviceCapabilitiesStateRequest>(requestManageDeviceCapabilitiesJson)
    }

    @Test
    fun addition_responseManageDeviceCapabilitiesJson_isCorrect() {
        val manageGroupCapabilitiesResponse =
            json.decodeFromString<YandexManageGroupCapabilitiesStateResponse>(responseManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_requestManageGroupCapabilitiesJson_isCorrect() {
        val manageGroupCapabilitiesRequest =
            json.decodeFromString<YandexManageGroupCapabilitiesStateRequest>(requestManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_responseManageGroupCapabilitiesJson_isCorrect() {
        val manageGroupCapabilitiesResponse =
            json.decodeFromString<YandexManageGroupCapabilitiesStateResponse>(responseManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_requestUserInfoJson_isCorrect() {
        val userInfoResponse =
            json.decodeFromString<YandexUserInfoResponse>(responseUserInfoJson)
    }
}