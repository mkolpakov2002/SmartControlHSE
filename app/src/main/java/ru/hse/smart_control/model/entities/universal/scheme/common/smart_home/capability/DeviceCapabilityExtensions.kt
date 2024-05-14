package ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability

import kotlinx.serialization.json.JsonObject

fun DeviceCapabilityObject.toCapabilityObject(): CapabilityObject {
    return CapabilityObject(
        type = type,
        state = state
    )
}

fun CapabilityObject.toDeviceCapabilityObject(old : DeviceCapabilityObject): DeviceCapabilityObject {
    return old.copy(
        type = type,
        state = state
    )
}