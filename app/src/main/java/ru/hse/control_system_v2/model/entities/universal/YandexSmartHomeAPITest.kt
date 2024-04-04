package ru.hse.control_system_v2.model.entities.universal

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

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
}