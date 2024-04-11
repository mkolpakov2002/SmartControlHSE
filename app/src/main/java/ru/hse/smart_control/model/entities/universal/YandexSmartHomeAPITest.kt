package ru.hse.smart_control.model.entities.universal

import kotlinx.serialization.json.Json
import ru.hse.smart_control.model.entities.universal.scheme.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceType

class YandexSmartHomeAPITest {
    private val accessToken = "y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
//        classDiscriminator = "#class"
    }

    suspend fun testGetUserInfo() {
        val userInfo = YandexSmartHomeAPI.getUserInfo(accessToken)
        println("User Info: $userInfo")
//        userInfo?.let {
//            val dataUserInfo = json.encodeToJsonElement(UserHomeInfoApiResponse.serializer(), it)
//            println("User Info in data object: $dataUserInfo")
//        }
    }

    suspend fun testAll() {
        testGetUserInfo()
    }

//    fun createTestUserHomeResponse(): UserInfoResponse {
//        val device = DeviceObject(
//            id = "d7e57431-7953-49aa-b46e-589495b71986",
//            name = "Лампочка",
//            aliases = emptyList(),
//            type = DeviceType.Light,
//            externalId = "bf6aa28ee7199c5068ab3l",
//            skillId = "35e2897a-c583-495a-9e33-f5d6f0f4cb49",
//            householdId = "f80b6641-8880-49d5-be31-1b35745c321a",
//            room = "d490747d-862a-4c06-9182-c56641dc00e5",
//            groups = listOf("d7eded8d-bdb4-4541-beba-7bbf88fea853"),
//            capabilities = listOf(
//                DeviceCapabilityObject(
//                    type = "devices.capabilities.on_off",
//                    reportable = true,
//                    retrievable = true,
//                    parameters = OnOffCapabilityParameterObject(split = false),
//                    state = OnOffCapabilityStateObjectData(instance = OnOffCapabilityStateObjectInstance.ON, value = OnOffCapabilityStateObjectValue(true)),
//                    lastUpdated = 1.71162573E9f
//                )
//            ),
//            properties = emptyList()
//        )
//
//        return UserInfoResponse(
//            requestId = "test-request-id",
//            status = "ok",
//            rooms = emptyList(),
//            groups = emptyList(),
//            devices = listOf(device),
//            scenarios = emptyList(),
//            households = emptyList()
//        )
//    }
}