package ru.hse.miem.yandexsmarthomeapi

import kotlinx.serialization.json.Json
import org.junit.Test
import ru.hse.miem.yandexsmarthomeapi.TestConstants.requestManageDeviceCapabilitiesJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.requestManageGroupCapabilitiesJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.responseDeviceStateJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.responseManageGroupCapabilitiesJson
import ru.hse.miem.yandexsmarthomeapi.TestConstants.responseUserInfoJson

import ru.hse.miem.yandexsmarthomeapi.entity.DeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.ManageDeviceCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.ManageGroupCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.ManageGroupCapabilitiesStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.UserInfoResponse

class JsonParserUnitTest {

    private val json = Json {
        prettyPrint = true
    }

    @Test
    fun addition_requestDeviceGroupStateJson_isCorrect() {
        val deviceGroupStateRequest =
            json.decodeFromString<ManageGroupCapabilitiesStateRequest>(requestManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_responseDeviceStateJson_isCorrect() {
        val deviceStateResponse =
            json.decodeFromString<DeviceStateResponse>(responseDeviceStateJson)
    }

    @Test
    fun addition_requestManageDeviceCapabilitiesJson_isCorrect() {
        val manageDeviceCapabilitiesRequest =
            json.decodeFromString<ManageDeviceCapabilitiesStateRequest>(requestManageDeviceCapabilitiesJson)
    }

    @Test
    fun addition_responseManageDeviceCapabilitiesJson_isCorrect() {
        val manageGroupCapabilitiesResponse =
            json.decodeFromString<ManageGroupCapabilitiesStateResponse>(responseManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_requestManageGroupCapabilitiesJson_isCorrect() {
        val manageGroupCapabilitiesRequest =
            json.decodeFromString<ManageGroupCapabilitiesStateRequest>(requestManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_responseManageGroupCapabilitiesJson_isCorrect() {
        val manageGroupCapabilitiesResponse =
            json.decodeFromString<ManageGroupCapabilitiesStateResponse>(responseManageGroupCapabilitiesJson)
    }

    @Test
    fun addition_requestUserInfoJson_isCorrect() {
        val userInfoResponse =
            json.decodeFromString<UserInfoResponse>(responseUserInfoJson)
    }
}