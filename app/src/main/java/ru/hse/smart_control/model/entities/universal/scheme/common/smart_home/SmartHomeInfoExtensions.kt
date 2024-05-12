package ru.hse.smart_control.model.entities.universal.scheme.common.smart_home

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import org.slf4j.MDC.put
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.Capability
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityActionResultObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorModelWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorScene
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueObjectHSV
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueObjectScene
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.GroupCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.HSVObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapability
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapabilityInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapabilityMode
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapabilityModeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ModeObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.Range
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapability
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.Scene
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.SceneObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.SceneObjectWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.TemperatureK
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ToggleCapability
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ToggleCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ToggleCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ToggleCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ToggleCapabilityWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.VideoStreamCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.VideoStreamCapabilityParameterObjectStreamProtocolWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.DevicePropertyObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.EventObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.EventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.EventObjectValueWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.EventPropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.EventPropertyState
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.EventPropertyStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.FloatObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.FloatPropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.FloatPropertyState
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.FloatPropertyStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.MeasurementUnit
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.Property
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyFunction
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyFunctionWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyTypeWrapper

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    encodeDefaults = true
}

fun List<GroupCapabilityObject>.toYandexManageGroupCapabilitiesStateRequest(): YandexManageGroupCapabilitiesStateRequest {
    val actions = map { it.toYandexCapabilityJsonObject() }
    return YandexManageGroupCapabilitiesStateRequest(actions)
}

fun List<DeviceObject>.toYandexManageDeviceCapabilitiesStateRequest(): YandexManageDeviceCapabilitiesStateRequest {
    val devices = map { (it.toDeviceActionsObject() ).toYandexDeviceActionsJsonObject() }
    return YandexManageDeviceCapabilitiesStateRequest(devices)
}

fun DeviceObject.toDeviceActionsObject(): DeviceActionsObject {
    return DeviceActionsObject(
        id = this.id,
        actions = this.capabilities.map { capability ->
            CapabilityObject(
                type = capability.type,
                state = capability.state
            )
        }.toMutableList()
    )
}


fun GroupCapabilityObject.toYandexCapabilityJsonObject(): JsonObject {
    val capabilityType = type.type.code()
    val state = state?.toYandexCapabilityStateJsonObject(capabilityType)

    return buildJsonObject {
        put("type", capabilityType)
        state?.let { put("state", it) }
    }
}

fun DeviceActionsObject.toYandexDeviceActionsJsonObject(): JsonObject {
    return buildJsonObject {
        put("id", id)
        putJsonArray("actions") {
            actions.forEach {
                add(it.toYandexJson())
            }
        }
    }
}

fun DeviceCapabilityObject.toYandexCapabilityJsonObject(): JsonObject {
    val capabilityType = type.type.code()
    val state = state?.toYandexCapabilityStateJsonObject(capabilityType)

    return buildJsonObject {
        put("type", capabilityType)
        state?.let { put("state", it) }
    }
}

fun CapabilityStateObjectData.toYandexCapabilityStateJsonObject(capabilityType: String): JsonObject {
    return when (capabilityType) {
        "devices.capabilities.on_off" -> (this as OnOffCapabilityStateObjectData).toYandexOnOffCapabilityStateJsonObject()
        "devices.capabilities.color_setting" -> (this as ColorSettingCapabilityStateObjectData).toYandexColorSettingCapabilityStateJsonObject()
        "devices.capabilities.range" -> (this as RangeCapabilityStateObjectData).toYandexRangeCapabilityStateJsonObject()
        "devices.capabilities.mode" -> (this as ModeCapabilityStateObjectData).toYandexModeCapabilityStateJsonObject()
        "devices.capabilities.toggle" -> (this as ToggleCapabilityStateObjectData).toYandexToggleCapabilityStateJsonObject()
        else -> error("Unsupported capability type: $capabilityType")
    }
}

fun OnOffCapabilityStateObjectData.toYandexOnOffCapabilityStateJsonObject(): JsonObject {
    return buildJsonObject {
        put("instance", instance.onOff.code())
        put("value", value.value)
    }
}

fun ColorSettingCapabilityStateObjectData.toYandexColorSettingCapabilityStateJsonObject(): JsonObject {
    return buildJsonObject {
        put("instance", instance.colorSetting.code())
        when (val value = value) {
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

fun RangeCapabilityStateObjectData.toYandexRangeCapabilityStateJsonObject(): JsonObject {
    return buildJsonObject {
        put("instance", instance.range.code())
        put("value", value.value)
        relative?.let { put("relative", it) }
    }
}

fun ModeCapabilityStateObjectData.toYandexModeCapabilityStateJsonObject(): JsonObject {
    return buildJsonObject {
        put("instance", instance.mode.code())
        put("value", value.mode.code())
    }
}

fun ToggleCapabilityStateObjectData.toYandexToggleCapabilityStateJsonObject(): JsonObject {
    return buildJsonObject {
        put("instance", instance.toggle.code())
        put("value", value.value)
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun YandexUserInfoResponse.toUniversalSchemeJson(): String {
    val responseJson = buildJsonObject {
        put("status", status)
        put("request_id", requestId)
        putJsonArray("rooms") {
            rooms.forEach { room ->
                add(room)
            }
        }
        putJsonArray("groups") {
            groups.forEach { group ->
                add(group)
            }
        }
        putJsonArray("devices") {
            devices.forEach { device ->
                add(device)
            }
        }
        putJsonArray("scenarios") {
            scenarios.forEach { scenario ->
                add(scenario)
            }
        }
        putJsonArray("households") {
            households.forEach { household ->
                add(household)
            }
        }
    }
    return json.encodeToString(JsonElement.serializer(), responseJson)
}

fun JsonObject.toSmartHomeInfo(): SmartHomeInfo {

    val rooms = jsonObject["rooms"]?.jsonArray?.map { it.jsonObject } ?: emptyList()
    val groups = jsonObject["groups"]?.jsonArray?.map { it.jsonObject } ?: emptyList()
    val devices = jsonObject["devices"]?.jsonArray?.map { it.jsonObject } ?: emptyList()
    val scenarios = jsonObject["scenarios"]?.jsonArray?.map { it.jsonObject } ?: emptyList()
    val households = jsonObject["households"]?.jsonArray?.map { it.jsonObject } ?: emptyList()

    val roomObjects = rooms.map { roomJson ->
        RoomObject(
            id = roomJson["id"]?.jsonPrimitive?.content ?: "",
            name = roomJson["name"]?.jsonPrimitive?.content ?: "",
            devices = roomJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content }
                ?.toMutableList() ?: mutableListOf(),
            householdId = roomJson["household_id"]?.jsonPrimitive?.content ?: ""
        )
    }.toMutableList()
    val groupObjects = groups.map { groupJson ->
        GroupObject(
            id = groupJson["id"]?.jsonPrimitive?.content ?: "",
            name = groupJson["name"]?.jsonPrimitive?.content ?: "",
            aliases = groupJson["aliases"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
                ?: mutableListOf(),
            type = groupJson["type"]?.jsonPrimitive?.content?.let { DeviceTypeWrapper(it.codifiedEnum()) }
                ?: DeviceTypeWrapper(DeviceType.OTHER.codifiedEnum()),
            capabilities = groupJson["capabilities"]?.jsonArray?.map { it.jsonObject.toGroupCapabilityObject() }?.toMutableList()
                ?: mutableListOf(),
            devices = groupJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
                ?: mutableListOf(),
            householdId = groupJson["household_id"]?.jsonPrimitive?.content ?: ""
        )
    }.toMutableList()
    val deviceObjects = devices.map { deviceJson ->
        deviceJson.toDeviceObject()
    }.toMutableList()
    val scenarioObjects = scenarios.map { scenarioJson ->
        ScenarioObject(
            id = scenarioJson["id"]?.jsonPrimitive?.content ?: "",
            name = scenarioJson["name"]?.jsonPrimitive?.content ?: "",
            isActive = scenarioJson["is_active"]?.jsonPrimitive?.boolean ?: false
        )
    }.toMutableList()
    val householdObjects = households.map { householdJson ->
        HouseholdObject(
            id = householdJson["id"]?.jsonPrimitive?.content ?: "",
            name = householdJson["name"]?.jsonPrimitive?.content ?: "",
            type = householdJson["type"]?.jsonPrimitive?.content ?: ""
        )
    }.toMutableList()
    return SmartHomeInfo(roomObjects, groupObjects, deviceObjects, scenarioObjects, householdObjects)
}

fun SmartHomeInfo.toJson(): JsonObject {
    val roomsJson = buildJsonArray {
        rooms.forEach { room ->
            add(room.toYandexJson())
        }
    }
    val groupsJson = buildJsonArray {
        groups.forEach { group ->
            add(group.toYandexJson())
        }
    }
    val devicesJson = buildJsonArray {
        devices.forEach { device ->
            add(device.toYandexJson())
        }
    }
    val scenariosJson = buildJsonArray {
        scenarios.forEach { scenario ->
            add(scenario.toYandexJson())
        }
    }
    val householdsJson = buildJsonArray {
        households.forEach { household ->
            add(household.toYandexJson())
        }
    }
    return buildJsonObject {
        put("rooms", roomsJson)
        put("groups", groupsJson)
        put("devices", devicesJson)
        put("scenarios", scenariosJson)
        put("households", householdsJson)
    }
}

fun YandexUserInfoResponse.toSmartHomeInfo(): SmartHomeInfo {
    val roomObjects = rooms.map { roomJson ->
        RoomObject(
            id = roomJson["id"]?.jsonPrimitive?.content ?: "",
            name = roomJson["name"]?.jsonPrimitive?.content ?: "",
            devices = roomJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
                ?: mutableListOf(),
            householdId = roomJson["household_id"]?.jsonPrimitive?.content ?: ""
        )
    }.toMutableList()
    val groupObjects = groups.map { groupJson ->
        GroupObject(
            id = groupJson["id"]?.jsonPrimitive?.content ?: "",
            name = groupJson["name"]?.jsonPrimitive?.content ?: "",
            aliases = groupJson["aliases"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
                ?: mutableListOf(),
            type = groupJson["type"]?.jsonPrimitive?.content?.let { DeviceTypeWrapper(it.codifiedEnum()) }
                ?: DeviceTypeWrapper(DeviceType.OTHER.codifiedEnum()),
            capabilities = groupJson["capabilities"]?.jsonArray?.map { it.jsonObject.toGroupCapabilityObject() }?.toMutableList()
                ?: mutableListOf(),
            devices = groupJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
                ?: mutableListOf(),
            householdId = groupJson["household_id"]?.jsonPrimitive?.content ?: ""
        )
    }.toMutableList()
    val deviceObjects = devices.map { deviceJson ->
        deviceJson.toDeviceObject()
    }.toMutableList()
    val scenarioObjects = scenarios.map { scenarioJson ->
        ScenarioObject(
            id = scenarioJson["id"]?.jsonPrimitive?.content ?: "",
            name = scenarioJson["name"]?.jsonPrimitive?.content ?: "",
            isActive = scenarioJson["is_active"]?.jsonPrimitive?.boolean ?: false
        )
    }.toMutableList()
    val householdObjects = households.map { householdJson ->
        HouseholdObject(
            id = householdJson["id"]?.jsonPrimitive?.content ?: "",
            name = householdJson["name"]?.jsonPrimitive?.content ?: "",
            type = householdJson["type"]?.jsonPrimitive?.content ?: ""
        )
    }.toMutableList()
    return SmartHomeInfo(roomObjects, groupObjects, deviceObjects, scenarioObjects, householdObjects)
}

fun SmartHomeInfo.toYandexUserInfoResponse(): YandexUserInfoResponse {
    return YandexUserInfoResponse(
        rooms = rooms.map { it.toYandexJson() },
        groups = groups.map { it.toYandexJson() },
        devices = devices.map { it.toYandexJson() },
        scenarios = scenarios.map { it.toYandexJson() },
        households = households.map { it.toYandexJson() },
        requestId = "",
        status = ""
    )
}

fun YandexDeviceStateResponse.toDeviceObject(): DeviceObject {
    return DeviceObject(
        id = id,
        name = name,
        aliases = aliases.toMutableList(),
        type = DeviceTypeWrapper(type.content.codifiedEnum()),
        externalId = externalId,
        skillId = skillId,
        householdId = "",
        room = room,
        groups = groups.toMutableList(),
        capabilities = capabilities.mapNotNull { it.toDeviceCapabilityObject() }.toMutableList(),
        properties = properties.mapNotNull { it.toDevicePropertyObject() }.toMutableList(),
        quasarInfo = quasarInfo?.toQuasarInfo()
    )
}

@OptIn(ExperimentalSerializationApi::class, ExperimentalSerializationApi::class)
fun DeviceObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        putJsonArray("aliases") {
            aliases.forEach { alias ->
                add(JsonPrimitive(alias))
            }
        }
        put("type", type.type.code())
        put("external_id", externalId)
        put("skill_id", skillId)
        put("household_id", householdId)
        put("room", room)
        putJsonArray("groups") {
            groups.forEach { group ->
                add(JsonPrimitive(group))
            }
        }
        put("capabilities", buildJsonArray {
            capabilities.forEach { capability ->
                add(capability.toYandexJson())
            }
        })
        put("properties", buildJsonArray {
            properties.forEach { property ->
                add(property.toYandexJson())
            }
        })
        quasarInfo?.let { put("quasar_info", it.toYandexJson()) }
    }
}

fun JsonObject.toDeviceObject(): DeviceObject {
    return DeviceObject(
        id = this["id"]?.jsonPrimitive?.content ?: "",
        name = this["name"]?.jsonPrimitive?.content ?: "",
        aliases = this["aliases"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
            ?: mutableListOf(),
        type = this["type"]?.jsonPrimitive?.content?.let { DeviceTypeWrapper(it.codifiedEnum()) }
            ?: DeviceTypeWrapper(DeviceType.OTHER.codifiedEnum()),
        externalId = this["external_id"]?.jsonPrimitive?.content ?: "",
        skillId = this["skill_id"]?.jsonPrimitive?.content ?: "",
        householdId = this["household_id"]?.jsonPrimitive?.content ?: "",
        room = this["room"]?.jsonPrimitive?.contentOrNull,
        groups = this["groups"]?.jsonArray?.map { it.jsonPrimitive.content }?.toMutableList()
            ?: mutableListOf(),
        capabilities = this["capabilities"]?.jsonArray?.mapNotNull { it.jsonObject.toDeviceCapabilityObject() }?.toMutableList()
            ?: mutableListOf(),
        properties = this["properties"]?.jsonArray?.mapNotNull { it.jsonObject.toDevicePropertyObject() }?.toMutableList()
            ?: mutableListOf(),
        quasarInfo = this["quasar_info"]?.jsonObject?.toQuasarInfo()
    )
}

fun JsonObject?.toQuasarInfo(): QuasarInfo? {
    if (this == null) return null
    return QuasarInfo(
        deviceId = this["device_id"]?.jsonPrimitive?.content ?: "",
        platform = this["platform"]?.jsonPrimitive?.content ?: ""
    )
}

fun QuasarInfo.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("device_id", deviceId)
        put("platform", platform)
    }
}

fun RoomObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        putJsonArray("devices") {
            devices.forEach { deviceId ->
                add(JsonPrimitive(deviceId))
            }
        }
        put("household_id", householdId)
    }
}

fun GroupObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        putJsonArray("aliases") {
            aliases.forEach { alias ->
                add(JsonPrimitive(alias))
            }
        }
        put("type", type.type.code())
        putJsonArray("capabilities") {
            capabilities.forEach { capability ->
                add(capability.toYandexJson())
            }
        }
        putJsonArray("devices") {
            devices.forEach { deviceId ->
                add(JsonPrimitive(deviceId))
            }
        }
        put("household_id", householdId)
    }
}

fun JsonObject.toDeviceCapabilityObject(): DeviceCapabilityObject? {
    val type = CapabilityTypeWrapper(this["type"]?.jsonPrimitive?.content!!.codifiedEnum())
    val parameters = this["parameters"]?.jsonObject?.toCapabilityParameterObject(type)
    val state = if (this["state"] is JsonNull)
        null
    else
        this["state"]?.jsonObject?.toCapabilityStateObjectData(type)
    return if (parameters != null) {
        DeviceCapabilityObject(
            type = type,
            reportable = this["reportable"]?.jsonPrimitive?.boolean ?: false,
            retrievable = this["retrievable"]?.jsonPrimitive?.boolean ?: false,
            parameters = parameters,
            state = state,
            lastUpdated = this["last_updated"]?.jsonPrimitive?.float ?: 0.0f
        )
    } else {
        null
    }
}

fun DeviceCapabilityObject.toYandexJson(): JsonObject {
    val type = this.type
    val parametersJson = parameters.toYandexJson(type)
    val stateJson = state?.toYandexJson(type)

    return buildJsonObject {
        put("type", type.type.code())
        put("reportable", reportable)
        put("retrievable", retrievable)
        put("parameters", parametersJson)
        stateJson?.let { put("state", it) }
        put("last_updated", lastUpdated)
    }
}

fun JsonObject?.toColorSettingCapabilityParameterObject(): ColorSettingCapabilityParameterObject? {
    if (this == null) return null
    return ColorSettingCapabilityParameterObject(
        colorModel = this["color_model"]?.jsonPrimitive?.contentOrNull?.let { ColorModelWrapper(it.codifiedEnum()) },
        temperatureK = this["temperature_k"]?.jsonObject?.toTemperatureK(),
        colorScene = this["color_scene"]?.jsonObject?.toColorScene()
    )
}

fun JsonObject?.toTemperatureK(): TemperatureK? {
    if (this == null) return null
    return TemperatureK(
        min = this["min"]?.jsonPrimitive?.int ?: 0,
        max = this["max"]?.jsonPrimitive?.int ?: 0
    )
}

fun JsonObject?.toColorScene(): ColorScene? {
    if (this == null) return null
    return ColorScene(scenes = this["scenes"]?.jsonArray?.map { sceneJson ->
        Scene(id = SceneObjectWrapper(
            sceneJson.jsonObject["id"]?.jsonPrimitive?.content?.codifiedEnum()
                ?: SceneObject.ALARM.codifiedEnum()))
    } ?: emptyList())
}

fun JsonObject?.toOnOffCapabilityParameterObject(): OnOffCapabilityParameterObject? {
    if (this == null) return null
    return OnOffCapabilityParameterObject(
        split = this["split"]?.jsonPrimitive?.boolean ?: false
    )
}

fun JsonObject?.toModeCapabilityParameterObject(): ModeCapabilityParameterObject? {
    if (this == null) return null
    return ModeCapabilityParameterObject(
        instance = this["instance"]?.jsonPrimitive?.content?.let { ModeCapabilityInstanceWrapper(it.codifiedEnum()) }
            ?: ModeCapabilityInstanceWrapper(ModeCapability.THERMOSTAT.codifiedEnum()),
        modes = this["modes"]?.jsonArray?.map {
            ModeObject(value = ModeCapabilityModeWrapper(it.jsonObject["value"]?.jsonPrimitive?.content?.codifiedEnum()
                ?: ModeCapabilityMode.AUTO.codifiedEnum()))
        } ?: emptyList()
    )
}

fun JsonObject?.toRangeCapabilityParameterObject(): RangeCapabilityParameterObject? {
    if (this == null) return null
    return RangeCapabilityParameterObject(
        instance = this["instance"]?.jsonPrimitive?.content?.let { RangeCapabilityWrapper(it.codifiedEnum()) }
            ?: RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
        randomAccess = this["random_access"]?.jsonPrimitive?.boolean ?: false,
        range = this["range"]?.jsonObject?.toRange(),
        looped = this["looped"]?.jsonPrimitive?.boolean
    )
}

fun JsonObject?.toRange(): Range? {
    if (this == null) return null
    return Range(
        min = this["min"]?.jsonPrimitive?.float ?: 0.0f,
        max = this["max"]?.jsonPrimitive?.float ?: 0.0f,
        precision = this["precision"]?.jsonPrimitive?.float ?: 0.0f
    )
}

fun JsonObject?.toToggleCapabilityParameterObject(): ToggleCapabilityParameterObject? {
    if (this == null) return null
    return ToggleCapabilityParameterObject(
        instance = this["instance"]?.jsonPrimitive?.content?.let { ToggleCapabilityWrapper(it.codifiedEnum()) }
            ?: ToggleCapabilityWrapper(ToggleCapability.BACKLIGHT.codifiedEnum())
    )
}

fun JsonObject?.toVideoStreamCapabilityParameterObject(): VideoStreamCapabilityParameterObject? {
    if (this == null) return null
    return VideoStreamCapabilityParameterObject(
        protocols = this["protocols"]?.jsonArray?.map {
            VideoStreamCapabilityParameterObjectStreamProtocolWrapper(it.jsonPrimitive.content.codifiedEnum())
        } ?: emptyList()
    )
}

fun JsonObject?.toCapabilityParameterObject(typeWrapper: CapabilityTypeWrapper): CapabilityParameterObject? {
    return when (typeWrapper) {
        CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> this.toColorSettingCapabilityParameterObject()
        CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> this.toOnOffCapabilityParameterObject()
        CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> this.toModeCapabilityParameterObject()
        CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> this.toRangeCapabilityParameterObject()
        CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> this.toToggleCapabilityParameterObject()
        CapabilityTypeWrapper(CapabilityType.VIDEO_STREAM.codifiedEnum()) -> this.toVideoStreamCapabilityParameterObject()
        else -> error("Unsupported capability type")
    }
}

fun CapabilityParameterObject.toYandexJson(typeWrapper: CapabilityTypeWrapper): JsonObject {
    return when (typeWrapper) {
        CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) ->
            (this as? ColorSettingCapabilityParameterObject)?.toYandexJson() ?: error("Invalid capability parameters type")
        CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) ->
            (this as? OnOffCapabilityParameterObject)?.toYandexJson() ?: error("Invalid capability parameters type")
        CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) ->
            (this as? ModeCapabilityParameterObject)?.toYandexJson() ?: error("Invalid capability parameters type")
        CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) ->
            (this as? RangeCapabilityParameterObject)?.toYandexJson() ?: error("Invalid capability parameters type")
        CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) ->
            (this as? ToggleCapabilityParameterObject)?.toYandexJson() ?: error("Invalid capability parameters type")
        CapabilityTypeWrapper(CapabilityType.VIDEO_STREAM.codifiedEnum()) ->
            (this as? VideoStreamCapabilityParameterObject)?.toYandexJson() ?: error("Invalid capability parameters type")
        else -> error("Unsupported capability type")
    }
}
fun ColorSettingCapabilityParameterObject.toYandexJson(): JsonObject = buildJsonObject {
    colorModel?.let { put("color_model", it.colorModel.code()) }
    temperatureK?.let { put("temperature_k", it.toJson()) }
    colorScene?.let { put("color_scene", it.toJson()) }
}

fun TemperatureK.toJson(): JsonObject = buildJsonObject {
    put("min", min)
    put("max", max)
}

fun ColorScene.toJson(): JsonObject = buildJsonObject {
    putJsonArray("scenes") {
        scenes.forEach { scene ->
            add(scene.toJson())
        }
    }
}

fun Scene.toJson(): JsonObject = buildJsonObject {
    put("id", id.scene.code())
}

fun OnOffCapabilityParameterObject.toYandexJson(): JsonObject = buildJsonObject {
    put("split", split)
}

fun ModeCapabilityParameterObject.toYandexJson(): JsonObject = buildJsonObject {
    put("instance", instance.mode.code())
    putJsonArray("modes") {
        modes.forEach { mode ->
            add(mode.toJson())
        }
    }
}

fun ModeObject.toJson(): JsonObject = buildJsonObject {
    put("value", value.mode.code())
}

fun RangeCapabilityParameterObject.toYandexJson(): JsonObject = buildJsonObject {
    put("instance", instance.range.code())
    put("random_access", randomAccess)
    range?.let { put("range", it.toJson()) }
    put("looped", looped)
    put("unit", unit?.unit?.code())
}

fun Range.toJson(): JsonObject = buildJsonObject {
    put("min", min)
    put("max", max)
    put("precision", precision)
}

fun ToggleCapabilityParameterObject.toYandexJson(): JsonObject = buildJsonObject {
    put("instance", instance.toggle.code())
}

fun VideoStreamCapabilityParameterObject.toYandexJson(): JsonObject = buildJsonObject {
    putJsonArray("protocols") {
        protocols.forEach { protocol ->
            add(JsonPrimitive(protocol.streamProtocol.code()))
        }
    }
}

fun JsonObject.toCapabilityStateObjectData(typeWrapper: CapabilityTypeWrapper): CapabilityStateObjectData? {
    return when (typeWrapper) {
        CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> toOnOffCapabilityStateObjectData()
        CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> toColorSettingCapabilityStateObjectData()
        CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> toRangeCapabilityStateObjectData()
        CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> toModeCapabilityStateObjectData()
        CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> toToggleCapabilityStateObjectData()
        else -> null
    }
}

fun JsonObject.toOnOffCapabilityStateObjectData(): OnOffCapabilityStateObjectData {
    return OnOffCapabilityStateObjectData(
        instance = this["instance"]?.jsonPrimitive?.content?.let { OnOffCapabilityStateObjectInstanceWrapper(it.codifiedEnum()) }
            ?: OnOffCapabilityStateObjectInstanceWrapper(OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
        value = OnOffCapabilityStateObjectValue(value = this["value"]?.jsonPrimitive?.boolean ?: false)
    )
}

fun JsonObject.toColorSettingCapabilityStateObjectData(): ColorSettingCapabilityStateObjectData {
    return ColorSettingCapabilityStateObjectData(
        instance = this["instance"]?.jsonPrimitive?.content?.let { ColorSettingCapabilityStateObjectInstanceWrapper(it.codifiedEnum()) }
            ?: ColorSettingCapabilityStateObjectInstanceWrapper(ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
        value = this["value"].toColorSettingCapabilityStateObjectValue()
    )
}

fun JsonElement?.toColorSettingCapabilityStateObjectValue(): ColorSettingCapabilityStateObjectValue {
    return when (this) {
        is JsonPrimitive -> ColorSettingCapabilityStateObjectValueInteger(value = this.int)
        is JsonObject -> when (this["type"]?.jsonPrimitive?.content) {
            "scene" -> ColorSettingCapabilityStateObjectValueObjectScene(value = SceneObjectWrapper(this["id"]?.jsonPrimitive?.content?.codifiedEnum() ?: SceneObject.ALARM.codifiedEnum()))
            else -> ColorSettingCapabilityStateObjectValueObjectHSV(value = HSVObject(
                h = this["h"]?.jsonPrimitive?.int ?: 0,
                s = this["s"]?.jsonPrimitive?.int ?: 0,
                v = this["v"]?.jsonPrimitive?.int ?: 0
            ))
        }
        else -> ColorSettingCapabilityStateObjectValueInteger(0)
    }
}

fun JsonObject.toRangeCapabilityStateObjectData(): RangeCapabilityStateObjectData {
    return RangeCapabilityStateObjectData(
        instance = this["instance"]?.jsonPrimitive?.content?.let { RangeCapabilityWrapper(it.codifiedEnum()) }
            ?: RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
        value = RangeCapabilityStateObjectDataValue(value = this["value"]?.jsonPrimitive?.float ?: 0.0f),
        relative = this["relative"]?.jsonPrimitive?.boolean
    )
}

fun JsonObject.toModeCapabilityStateObjectData(): ModeCapabilityStateObjectData {
    return ModeCapabilityStateObjectData(
        instance = this["instance"]?.jsonPrimitive?.content?.let { ModeCapabilityInstanceWrapper(it.codifiedEnum()) }
            ?: ModeCapabilityInstanceWrapper(ModeCapability.THERMOSTAT.codifiedEnum()),
        value = this["value"]?.jsonPrimitive?.content?.let { ModeCapabilityModeWrapper(it.codifiedEnum()) }
            ?: ModeCapabilityModeWrapper(ModeCapabilityMode.AUTO.codifiedEnum())
    )
}

fun JsonObject.toToggleCapabilityStateObjectData(): ToggleCapabilityStateObjectData {
    return ToggleCapabilityStateObjectData(
        instance = this["instance"]?.jsonPrimitive?.content?.let { ToggleCapabilityWrapper(it.codifiedEnum()) }
            ?: ToggleCapabilityWrapper(ToggleCapability.BACKLIGHT.codifiedEnum()),
        value = ToggleCapabilityStateObjectDataValue(value = this["value"]?.jsonPrimitive?.boolean ?: false)
    )
}

fun CapabilityStateObjectData.toYandexJson(typeWrapper: CapabilityTypeWrapper): JsonObject? {
    return when (typeWrapper) {
        CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> (this as? OnOffCapabilityStateObjectData)?.toYandexJson()
        CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> (this as? ColorSettingCapabilityStateObjectData)?.toYandexJson()
        CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> (this as? RangeCapabilityStateObjectData)?.toYandexJson()
        CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> (this as? ModeCapabilityStateObjectData)?.toYandexJson()
        CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> (this as? ToggleCapabilityStateObjectData)?.toYandexJson()
        else -> null
    }
}

fun OnOffCapabilityStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.onOff.code())
        put("value", value.value)
    }
}

fun ColorSettingCapabilityStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.colorSetting.code())
        put("value", value.toYandexJson())
    }
}

fun ColorSettingCapabilityStateObjectValue.toYandexJson(): JsonElement = when (this) {
    is ColorSettingCapabilityStateObjectValueInteger -> JsonPrimitive(value)
    is ColorSettingCapabilityStateObjectValueObjectScene -> toJson()
    is ColorSettingCapabilityStateObjectValueObjectHSV -> toJson()
}

fun ColorSettingCapabilityStateObjectValueObjectScene.toJson(): JsonObject = buildJsonObject {
    put("type", "scene")
    put("id", value.scene.code())
}

fun ColorSettingCapabilityStateObjectValueObjectHSV.toJson(): JsonObject = buildJsonObject {
    put("type", "hsv")
    value.let {
        put("h", it.h)
        put("s", it.s)
        put("v", it.v)
    }
}

fun RangeCapabilityStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.range.code())
        put("value", value.value)
        relative?.let { put("relative", it) }
    }
}

fun ModeCapabilityStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.mode.code())
        put("value", value.mode.code())
    }
}

fun ToggleCapabilityStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.toggle.code())
        put("value", value.value)
    }
}

fun JsonObject.toDevicePropertyObject(): DevicePropertyObject? {
    val type = PropertyTypeWrapper(this["type"]?.jsonPrimitive?.content!!.codifiedEnum())
    val parameters = this["parameters"]?.jsonObject?.toPropertyParameterObject(type)
    val state = if (this["state"] !is JsonNull) this["state"]!!.jsonObject.toPropertyStateObjectData(type) else null
    return if (parameters != null) {
        DevicePropertyObject(
            type = type,
            reportable = this["reportable"]?.jsonPrimitive?.boolean ?: false,
            retrievable = this["retrievable"]?.jsonPrimitive?.boolean ?: false,
            parameters = parameters,
            state = state,
            lastUpdated = this["last_updated"]?.jsonPrimitive?.float ?: 0.0f
        )
    } else {
        null
    }
}

fun DevicePropertyObject.toYandexJson(): JsonObject {
    val type = this.type
    val parametersJson = parameters.toYandexJson(type)
    val stateJson = state?.toYandexJson(type)

    return buildJsonObject {
        put("type", type.type.code())
        put("reportable", reportable)
        put("retrievable", retrievable)
        put("parameters", parametersJson)
        stateJson?.let { put("state", it) } ?: put("state", JsonNull)
        put("last_updated", lastUpdated)
    }
}

fun JsonObject.toPropertyParameterObject(type: PropertyTypeWrapper): PropertyParameterObject {
    return when (type) {
        PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> toFloatPropertyParameterObject()
        PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> toEventPropertyParameterObject()
        else -> error("Unsupported property type")
    }
}

fun JsonObject.toFloatPropertyParameterObject(): FloatPropertyParameterObject {
    return FloatPropertyParameterObject(
        instance = this["instance"]?.toPropertyFunctionWrapper() ?: PropertyFunctionWrapper(PropertyFunction.HUMIDITY.codifiedEnum()),
        unit = this["unit"]?.toMeasurementUnitWrapper() ?: MeasurementUnitWrapper(MeasurementUnit.PERCENT.codifiedEnum())
    )
}

fun JsonObject.toEventPropertyParameterObject(): EventPropertyParameterObject {
    return EventPropertyParameterObject(
        instance = this["instance"]?.toPropertyFunctionWrapper() ?: PropertyFunctionWrapper(PropertyFunction.BUTTON.codifiedEnum()),
        events = this["events"]?.toEventObjectList() ?: emptyList()
    )
}

fun JsonElement.toMeasurementUnitWrapper(): MeasurementUnitWrapper {
    return this.jsonPrimitive.content.let { MeasurementUnitWrapper(it.codifiedEnum()) }
}

fun JsonElement.toEventObjectList(): List<EventObject> {
    return this.jsonArray.map { it.toEventObject() }
}

fun JsonElement.toEventObject(): EventObject {
    return EventObject(
        value = this.jsonObject["value"]?.toEventObjectValueWrapper() ?: EventObjectValueWrapper(EventObjectValue.CLICK.codifiedEnum())
    )
}

fun PropertyParameterObject.toYandexJson(type: PropertyTypeWrapper): JsonObject {
    return when (type) {
        PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> (this as? FloatPropertyParameterObject)?.toYandexJson() ?: error("Invalid property parameters type")
        PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> (this as? EventPropertyParameterObject)?.toYandexJson() ?: error("Invalid property parameters type")
        else -> error("Unsupported property type")
    }
}

fun FloatPropertyParameterObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.function.code())
        put("unit", unit.unit.code())
    }
}

fun EventPropertyParameterObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("instance", instance.function.code())
        put("events", events.toYandexJson())
    }
}

fun List<EventObject>.toYandexJson(): JsonArray {
    return buildJsonArray {
        forEach { event ->
            add(event.toYandexJson())
        }
    }
}

fun EventObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("value", value.value.code())
    }
}

fun JsonObject.toPropertyStateObjectData(type: PropertyTypeWrapper): PropertyStateObjectData? {
    return when (type) {
        PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> toFloatPropertyStateObjectData()
        PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> toEventPropertyStateObjectData()
        else -> null
    }
}

fun JsonObject.toFloatPropertyStateObjectData(): FloatPropertyStateObjectData {
    return FloatPropertyStateObjectData(
        state = this["state"]?.jsonObject?.toFloatPropertyState() ?: FloatPropertyState(
            propertyFunction = PropertyFunctionWrapper(PropertyFunction.CUSTOM.codifiedEnum()),
            propertyValue = FloatObjectValue(0.0f)
        )
    )
}

fun JsonObject.toEventPropertyStateObjectData(): EventPropertyStateObjectData {
    return EventPropertyStateObjectData(
        state = this["state"]?.jsonObject?.toEventPropertyState() ?: EventPropertyState(
            propertyFunction = PropertyFunctionWrapper(PropertyFunction.CUSTOM.codifiedEnum()),
            propertyValue = EventObject(value = EventObjectValueWrapper(EventObjectValue.NORMAL.codifiedEnum()))
        )
    )
}

fun JsonObject.toFloatPropertyState(): FloatPropertyState {
    return FloatPropertyState(
        propertyFunction = this["function"]?.toPropertyFunctionWrapper() ?: PropertyFunctionWrapper(PropertyFunction.CUSTOM.codifiedEnum()),
        propertyValue = FloatObjectValue(value = this["value"]?.jsonPrimitive?.float ?: 0.0f)
    )
}

fun JsonObject.toEventPropertyState(): EventPropertyState {
    return EventPropertyState(
        propertyFunction = this["function"]?.toPropertyFunctionWrapper() ?: PropertyFunctionWrapper(PropertyFunction.CUSTOM.codifiedEnum()),
        propertyValue = EventObject(value = this["value"]?.toEventObjectValueWrapper() ?: EventObjectValueWrapper(EventObjectValue.NORMAL.codifiedEnum()))
    )
}

fun JsonElement.toPropertyFunctionWrapper(): PropertyFunctionWrapper {
    return this.jsonPrimitive.content.let { PropertyFunctionWrapper(it.codifiedEnum()) }
}

fun JsonElement.toEventObjectValueWrapper(): EventObjectValueWrapper {
    return this.jsonPrimitive.content.let { EventObjectValueWrapper(it.codifiedEnum()) }
}

fun PropertyStateObjectData.toYandexJson(type: PropertyTypeWrapper): JsonObject? {
    return when (type) {
        PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> (this as? FloatPropertyStateObjectData)?.toYandexJson()
        PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> (this as? EventPropertyStateObjectData)?.toYandexJson()
        else -> null
    }
}

fun FloatPropertyStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("state", state.toYandexJson())
    }
}

fun EventPropertyStateObjectData.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("state", state.toYandexJson())
    }
}

fun FloatPropertyState.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("function", propertyFunction.function.code())
        put("value", propertyValue.value)
    }
}

fun EventPropertyState.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("function", propertyFunction.function.code())
        put("value", propertyValue.value.value.code())
    }
}

fun JsonObject.toGroupCapabilityObject(): GroupCapabilityObject {
    val type = CapabilityTypeWrapper(this["type"]?.jsonPrimitive?.content!!.codifiedEnum())
    val parameters = this["parameters"]?.jsonObject!!.toCapabilityParameterObject(type)
    return GroupCapabilityObject(
        type = type,
        retrievable = this["retrievable"]?.jsonPrimitive?.boolean ?: false,
        parameters = parameters,
        state = this["state"]?.jsonObject?.toCapabilityStateObjectData(type)
    )
}

fun GroupCapabilityObject.toYandexJson(): JsonObject {
    val parametersJson = parameters?.toYandexJson(type)
    val stateJson = state?.toYandexJson(type)

    return buildJsonObject {
        put("type", type.type.code())
        put("retrievable", retrievable)
        put("parameters", parametersJson?: JsonNull)
        stateJson?.let { put("state", it) }
    }
}

fun ScenarioObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("is_active", isActive)
    }
}

fun HouseholdObject.toYandexJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
    }
}

fun Capability.toYandexJson(): JsonObject {
    return when (this) {
        is DeviceCapabilityObject -> this.toYandexJson()
        is CapabilityObject -> buildJsonObject {
            put("type", type.type.code())
//            retrievable?.let { put("retrievable", it) }
//            parameters?.let { put("parameters", it.toYandexJson(type)) }
            state?.run { toYandexJson(type)?.let { put("state", it) } }
//            lastUpdated?.let { put("last_updated", it) }
        }
    }
}

fun JsonObject.toCapabilityObject(type: CapabilityTypeWrapper): CapabilityObject {
    return CapabilityObject(
        type = type,
//        retrievable = this["retrievable"]?.jsonPrimitive?.boolean,
//        parameters = this["parameters"]?.jsonObject?.toCapabilityParameterObject(type),
        state = this["state"]?.jsonObject?.toCapabilityStateObjectData(type),
//        lastUpdated = this["last_updated"]?.jsonPrimitive?.float
    )
}

fun JsonObject.toCapability(): Capability {
    val type = CapabilityTypeWrapper(this["type"]?.jsonPrimitive?.content!!.codifiedEnum())

    return when {
        "reportable" in this -> toDeviceCapabilityObject() ?: toCapabilityObject(type)
        else -> toCapabilityObject(type)
    }
}

fun Property.toYandexJson(): JsonObject {
    return when (this) {
        is DevicePropertyObject -> this.toYandexJson()
        is PropertyObject -> buildJsonObject {
            put("type", type.type.code())
            put("retrievable", retrievable)
            put("parameters", parameters.toYandexJson(type))
            state?.run { toYandexJson(type)?.let { put("state", it) } }
            put("last_updated", lastUpdated)
        }
    }
}

fun JsonObject.toPropertyObject(type: PropertyTypeWrapper): PropertyObject {
    return PropertyObject(
        type = type,
        retrievable = this["retrievable"]?.jsonPrimitive?.boolean ?: false,
        parameters = this["parameters"]?.jsonObject?.toPropertyParameterObject(type)!!,
        state = this["state"]?.jsonObject?.toPropertyStateObjectData(type),
        lastUpdated = this["last_updated"]?.jsonPrimitive?.float ?: 0f
    )
}

fun JsonObject.toProperty(): Property {
    val type = PropertyTypeWrapper(this["type"]?.jsonPrimitive?.content!!.codifiedEnum())

    return when {
        "reportable" in this -> toDevicePropertyObject() ?: toPropertyObject(type)
        else -> toPropertyObject(type)
    }
}