package ru.hse.miem.yandexsmarthomeapi.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

//JsonObject = DeviceActionsObject
@Serializable
data class ManageDeviceCapabilitiesStateRequest(
    val devices: List<JsonObject>
)

//JsonObject = CapabilityObject
@Serializable
data class ManageGroupCapabilitiesStateRequest(
    val actions: List<JsonObject>
)