package ru.hse.miem.yandexsmarthomeapi.entity

import kotlinx.serialization.Serializable

@Serializable
data class ManageDeviceCapabilitiesStateRequest(
    val devices: List<DeviceActionsObject>
)

@Serializable
data class ManageGroupCapabilitiesStateRequest(
    val actions: List<CapabilityObject>
)