package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateRequest
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueObjectHSV
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueObjectScene
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.GroupCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ToggleCapabilityStateObjectData
import kotlin.reflect.KProperty

class YandexApiRequestMapper {
    fun mapToManageGroupCapabilitiesStateRequest(groupCapabilityObjects: List<GroupCapabilityObject>): YandexManageGroupCapabilitiesStateRequest {
        val actions = groupCapabilityObjects.map { mapToGroupCapabilityAction(it) }
        return YandexManageGroupCapabilitiesStateRequest(actions)
    }

    fun mapToManageDeviceCapabilitiesStateRequest(deviceObjects: List<DeviceObject>): YandexManageDeviceCapabilitiesStateRequest {
        val devices = deviceObjects.map { mapToDeviceActions(it) }
        return YandexManageDeviceCapabilitiesStateRequest(devices)
    }

    private fun mapToGroupCapabilityAction(groupCapabilityObject: GroupCapabilityObject): JsonObject {
        val capabilityType = groupCapabilityObject.type.type.code()
        val state = groupCapabilityObject.state?.let { mapToCapabilityState(it, capabilityType) }

        return buildJsonObject {
            put("type", capabilityType)
            state?.let { put("state", it) }
        }
    }

    private fun mapToDeviceActions(deviceObject: DeviceObject): JsonObject {
        val deviceId = deviceObject.id
        val actions = deviceObject.capabilities.map { mapToDeviceCapabilityAction(it) }

        return buildJsonObject {
            put("id", deviceId)
            putJsonArray("actions") {
                actions.forEach { add(it) }
            }
        }
    }

    private fun mapToDeviceCapabilityAction(deviceCapabilityObject: DeviceCapabilityObject): JsonObject {
        val capabilityType = deviceCapabilityObject.type.type.code()
        val state = deviceCapabilityObject.state?.let { mapToCapabilityState(it, capabilityType) }

        return buildJsonObject {
            put("type", capabilityType)
            state?.let { put("state", it) }
        }
    }

    private fun mapToCapabilityState(stateData: CapabilityStateObjectData, capabilityType: String): JsonObject {
        return when (capabilityType) {
            "devices.capabilities.on_off" -> {
                val onOffState = stateData as OnOffCapabilityStateObjectData
                buildJsonObject {
                    put("instance", onOffState.instance.onOff.code())
                    put("value", onOffState.value.value)
                }
            }
            "devices.capabilities.color_setting" -> {
                val colorState = stateData as ColorSettingCapabilityStateObjectData
                buildJsonObject {
                    put("instance", colorState.instance.colorSetting.code())
                    when (val value = colorState.value) {
                        is ColorSettingCapabilityStateObjectValueInteger -> put("value", value.value)
                        is ColorSettingCapabilityStateObjectValueObjectHSV -> {
                            putJsonObject("value") {
                                put("h", value.value.h)
                                put("s", value.value.s)
                                put("v", value.value.v)
                            }
                        }
                        is ColorSettingCapabilityStateObjectValueObjectScene -> {
                            putJsonObject("value") {
                                put("type", "scene")
                                put("id", value.value.scene.code())
                            }
                        }
                    }
                }
            }
            "devices.capabilities.range" -> {
                val rangeState = stateData as RangeCapabilityStateObjectData
                buildJsonObject {
                    put("instance", rangeState.instance.range.code())
                    put("value", rangeState.value.value)
                    rangeState.relative?.let { put("relative", it) }
                }
            }
            "devices.capabilities.mode" -> {
                val modeState = stateData as ModeCapabilityStateObjectData
                buildJsonObject {
                    put("instance", modeState.instance.mode.code())
                    put("value", modeState.value.mode.code())
                }
            }
            "devices.capabilities.toggle" -> {
                val toggleState = stateData as ToggleCapabilityStateObjectData
                buildJsonObject {
                    put("instance", toggleState.instance.toggle.code())
                    put("value", toggleState.value.value)
                }
            }
            else -> error("Unsupported capability type: $capabilityType")
        }
    }
}