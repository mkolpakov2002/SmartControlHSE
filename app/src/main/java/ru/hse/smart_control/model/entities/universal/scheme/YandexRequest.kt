package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.Serializable

@Serializable
data class ManageDeviceCapabilitiesStateRequest(
    val devices: DeviceActionsObject
)