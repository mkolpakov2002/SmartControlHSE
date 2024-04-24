package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse

class YandexApiResponseMapper {
    fun mapUserInfoResponse(userInfoResponse: YandexUserInfoResponse): UserInfo {
        val roomObjects = mapRooms(userInfoResponse.rooms)
        val groupObjects = mapGroups(userInfoResponse.groups)
        val deviceObjects = mapDevices(userInfoResponse.devices)
        val scenarioObjects = mapScenarios(userInfoResponse.scenarios)
        val householdObjects = mapHouseholds(userInfoResponse.households)
        return UserInfo(roomObjects, groupObjects, deviceObjects, scenarioObjects, householdObjects)
    }

    fun mapDeviceStateResponse(deviceStateResponse: YandexDeviceStateResponse): DeviceObject {
        return DeviceObject(
            id = deviceStateResponse.id,
            name = deviceStateResponse.name,
            aliases = deviceStateResponse.aliases,
            type = DeviceTypeWrapper(deviceStateResponse.type.content.codifiedEnum()),
            externalId = deviceStateResponse.externalId,
            skillId = deviceStateResponse.skillId,
            householdId = "",
            room = deviceStateResponse.room,
            groups = deviceStateResponse.groups,
            capabilities = mapCapabilities(deviceStateResponse.capabilities),
            properties = mapProperties(deviceStateResponse.properties),
            quasarInfo = null
        )
    }

    private fun mapRooms(rooms: List<JsonObject>): List<RoomObject> {
        return rooms.map { roomJson ->
            RoomObject(
                id = roomJson["id"]?.jsonPrimitive?.content ?: "",
                name = roomJson["name"]?.jsonPrimitive?.content ?: "",
                devices = roomJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                householdId = roomJson["household_id"]?.jsonPrimitive?.content ?: ""
            )
        }
    }

    private fun mapGroups(groups: List<JsonObject>): List<GroupObject> {
        return groups.map { groupJson ->
            GroupObject(
                id = groupJson["id"]?.jsonPrimitive?.content ?: "",
                name = groupJson["name"]?.jsonPrimitive?.content ?: "",
                aliases = groupJson["aliases"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                type = groupJson["type"]?.jsonPrimitive?.content?.let { DeviceTypeWrapper(it.codifiedEnum()) }
                    ?: DeviceTypeWrapper(DeviceType.OTHER.codifiedEnum()),
                capabilities = groupJson["capabilities"]?.jsonArray?.map { mapGroupCapability(it.jsonObject) } ?: emptyList(),
                devices = groupJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                householdId = groupJson["household_id"]?.jsonPrimitive?.content ?: ""
            )
        }
    }

    private fun mapDevices(devices: List<JsonObject>): List<DeviceObject> {
        return devices.map { deviceJson ->
            DeviceObject(
                id = deviceJson["id"]?.jsonPrimitive?.content ?: "",
                name = deviceJson["name"]?.jsonPrimitive?.content ?: "",
                aliases = deviceJson["aliases"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                type = deviceJson["type"]?.jsonPrimitive?.content?.let { DeviceTypeWrapper(it.codifiedEnum()) }
                    ?: DeviceTypeWrapper(DeviceType.OTHER.codifiedEnum()),
                externalId = deviceJson["external_id"]?.jsonPrimitive?.content ?: "",
                skillId = deviceJson["skill_id"]?.jsonPrimitive?.content ?: "",
                householdId = deviceJson["household_id"]?.jsonPrimitive?.content ?: "",
                room = deviceJson["room"]?.jsonPrimitive?.contentOrNull,
                groups = deviceJson["groups"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                capabilities = deviceJson["capabilities"]?.jsonArray?.mapNotNull { mapCapability(it.jsonObject) } ?: emptyList(),
                properties = deviceJson["properties"]?.jsonArray?.mapNotNull { mapProperty(it.jsonObject) } ?: emptyList(),
                quasarInfo = null
            )
        }
    }

    private fun mapCapabilities(capabilities: List<JsonObject>): List<DeviceCapabilityObject> {
        return capabilities.mapNotNull { mapCapability(it) }
    }

    private fun mapCapability(capabilityJson: JsonObject): DeviceCapabilityObject? {
        val type = CapabilityTypeWrapper(capabilityJson["type"]?.jsonPrimitive?.content!!.codifiedEnum())
        val parameters = capabilityJson["parameters"]?.jsonObject?.let { mapCapabilityParameters(type, it) }
        val state = if(capabilityJson["state"] is JsonNull)
            null
        else
            capabilityJson["state"]?.jsonObject?.let { mapCapabilityState(type, it) }
        return if (parameters != null) {
            DeviceCapabilityObject(
                type = type,
                reportable = capabilityJson["reportable"]?.jsonPrimitive?.boolean ?: false,
                retrievable = capabilityJson["retrievable"]?.jsonPrimitive?.boolean ?: false,
                parameters = parameters,
                state = state,
                lastUpdated = capabilityJson["last_updated"]?.jsonPrimitive?.float ?: 0.0f
            )
        } else {
            null
        }
    }

    private fun mapCapabilityParameters(typeWrapper: CapabilityTypeWrapper, parametersJson: JsonObject): CapabilityParameterObject {
        return when (typeWrapper) {
            CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> ColorSettingCapabilityParameterObject(
                colorModel = parametersJson["color_model"]?.jsonPrimitive?.contentOrNull?.let { ColorModelWrapper(it.codifiedEnum()) },
                temperatureK = parametersJson["temperature_k"]?.jsonObject?.let {
                    TemperatureK(
                        min = it["min"]?.jsonPrimitive?.int ?: 0,
                        max = it["max"]?.jsonPrimitive?.int ?: 0
                    )
                },
                colorScene = parametersJson["color_scene"]?.jsonObject?.let {
                    ColorScene(scenes = it["scenes"]?.jsonArray?.map { sceneJson ->
                        Scene(id = SceneObjectWrapper(sceneJson.jsonObject["id"]?.jsonPrimitive?.content?.codifiedEnum() ?: SceneObject.ALARM.codifiedEnum()))
                    } ?: emptyList())
                }
            )
            CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> OnOffCapabilityParameterObject(
                split = parametersJson["split"]?.jsonPrimitive?.boolean ?: false
            )
            CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> ModeCapabilityParameterObject(
                instance = parametersJson["instance"]?.jsonPrimitive?.content?.let { ModeCapabilityInstanceWrapper(it.codifiedEnum()) }
                    ?: ModeCapabilityInstanceWrapper(ModeCapability.THERMOSTAT.codifiedEnum()),
                modes = parametersJson["modes"]?.jsonArray?.map {
                    ModeObject(value = ModeCapabilityModeWrapper(it.jsonObject["value"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: ModeCapabilityMode.AUTO.codifiedEnum()))
                } ?: emptyList()
            )
            CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> RangeCapabilityParameterObject(
                instance = parametersJson["instance"]?.jsonPrimitive?.content?.let { RangeCapabilityWrapper(it.codifiedEnum()) }
                    ?: RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
                randomAccess = parametersJson["random_access"]?.jsonPrimitive?.boolean ?: false,
                range = parametersJson["range"]?.jsonObject?.let {
                    Range(
                        min = it["min"]?.jsonPrimitive?.float ?: 0.0f,
                        max = it["max"]?.jsonPrimitive?.float ?: 0.0f,
                        precision = it["precision"]?.jsonPrimitive?.float ?: 0.0f
                    )
                },
                looped = parametersJson["looped"]?.jsonPrimitive?.boolean
            )
            CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> ToggleCapabilityParameterObject(
                instance = parametersJson["instance"]?.jsonPrimitive?.content?.let { ToggleCapabilityWrapper(it.codifiedEnum()) }
                    ?: ToggleCapabilityWrapper(ToggleCapability.BACKLIGHT.codifiedEnum())
            )
            CapabilityTypeWrapper(CapabilityType.VIDEO_STREAM.codifiedEnum()) -> VideoStreamCapabilityParameterObject(
                protocols = parametersJson["protocols"]?.jsonArray?.map {
                    VideoStreamCapabilityParameterObjectStreamProtocolWrapper(it.jsonPrimitive.content.codifiedEnum())
                } ?: emptyList()
            )
            else -> error("Unsupported capability type")
        }
    }

    private fun mapCapabilityState(typeWrapper: CapabilityTypeWrapper, stateJson: JsonObject): CapabilityStateObjectData? {
        return when (typeWrapper) {
            CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) ->
                OnOffCapabilityStateObjectData(
                    instance = stateJson["instance"]?.jsonPrimitive?.content?.let { OnOffCapabilityStateObjectInstanceWrapper(it.codifiedEnum()) }
                        ?: OnOffCapabilityStateObjectInstanceWrapper(OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                    value = OnOffCapabilityStateObjectValue(value = stateJson["value"]?.jsonPrimitive?.boolean ?: false)
                )

            CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) ->
                ColorSettingCapabilityStateObjectData(
                    instance = stateJson["instance"]?.jsonPrimitive?.content?.let { ColorSettingCapabilityStateObjectInstanceWrapper(it.codifiedEnum()) }
                        ?: ColorSettingCapabilityStateObjectInstanceWrapper(ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
                    value = when (val valueJson = stateJson["value"]) {
                        is JsonPrimitive -> ColorSettingCapabilityStateObjectValueInteger(value = valueJson.int)
                        is JsonObject -> when (valueJson["type"]?.jsonPrimitive?.content) {
                            "scene" -> ColorSettingCapabilityStateObjectValueObjectScene(value = SceneObjectWrapper(valueJson["id"]?.jsonPrimitive?.content?.codifiedEnum() ?: SceneObject.ALARM.codifiedEnum()))
                            else -> ColorSettingCapabilityStateObjectValueObjectHSV(value = HSVObject(
                                h = valueJson["h"]?.jsonPrimitive?.int ?: 0,
                                s = valueJson["s"]?.jsonPrimitive?.int ?: 0,
                                v = valueJson["v"]?.jsonPrimitive?.int ?: 0
                            ))
                        }
                        else -> ColorSettingCapabilityStateObjectValueInteger(0)
                    }
                )
            CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) ->
                RangeCapabilityStateObjectData(
                    instance = stateJson["instance"]?.jsonPrimitive?.content?.let { RangeCapabilityWrapper(it.codifiedEnum()) }
                        ?: RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
                    value = RangeCapabilityStateObjectDataValue(value = stateJson["value"]?.jsonPrimitive?.float ?: 0.0f),
                    relative = stateJson["relative"]?.jsonPrimitive?.boolean
                )
            CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) ->
                ModeCapabilityStateObjectData(
                    instance = stateJson["instance"]?.jsonPrimitive?.content?.let { ModeCapabilityInstanceWrapper(it.codifiedEnum()) }
                        ?: ModeCapabilityInstanceWrapper(ModeCapability.THERMOSTAT.codifiedEnum()),
                    value = stateJson["value"]?.jsonPrimitive?.content?.let { ModeCapabilityModeWrapper(it.codifiedEnum()) }
                        ?: ModeCapabilityModeWrapper(ModeCapabilityMode.AUTO.codifiedEnum())
                )
            CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) ->
                ToggleCapabilityStateObjectData(
                    instance = stateJson["instance"]?.jsonPrimitive?.content?.let { ToggleCapabilityWrapper(it.codifiedEnum()) }
                        ?: ToggleCapabilityWrapper(ToggleCapability.BACKLIGHT.codifiedEnum()),
                    value = ToggleCapabilityStateObjectDataValue(value = stateJson["value"]?.jsonPrimitive?.boolean ?: false)
                )
            else -> null
        }
    }

    private fun mapProperties(properties: List<JsonObject>): List<DevicePropertyObject> {
        return properties.mapNotNull { mapProperty(it) }
    }

    private fun mapProperty(propertyJson: JsonObject): DevicePropertyObject? {
        val type = PropertyTypeWrapper(propertyJson["type"]?.jsonPrimitive?.content!!.codifiedEnum())
        val parameters = propertyJson["parameters"]?.jsonObject?.let { mapPropertyParameters(type, it) }
        val state = if(propertyJson["state"] !is JsonNull) mapPropertyState(type, propertyJson["state"]!!.jsonObject) else null
        return if (parameters != null) {
            DevicePropertyObject(
                type = type,
                reportable = propertyJson["reportable"]?.jsonPrimitive?.boolean ?: false,
                retrievable = propertyJson["retrievable"]?.jsonPrimitive?.boolean ?: false,
                parameters = parameters,
                state = state,
                lastUpdated = propertyJson["last_updated"]?.jsonPrimitive?.float ?: 0.0f
            )
        } else {
            null
        }
    }

    private fun mapPropertyParameters(type: PropertyTypeWrapper, parametersJson: JsonObject): PropertyParameterObject {
        return when (type) {
            PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> FloatPropertyParameterObject(
                instance = parametersJson["instance"]?.jsonPrimitive?.content?.let { PropertyFunctionWrapper(it.codifiedEnum()) }
                    ?: PropertyFunctionWrapper(PropertyFunction.HUMIDITY.codifiedEnum()),
                unit = parametersJson["unit"]?.jsonPrimitive?.content?.let { MeasurementUnitWrapper(it.codifiedEnum()) }
                    ?: MeasurementUnitWrapper(MeasurementUnit.PERCENT.codifiedEnum())
            )
            PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> EventPropertyParameterObject(
                instance = parametersJson["instance"]?.jsonPrimitive?.content?.let { PropertyFunctionWrapper(it.codifiedEnum()) }
                    ?: PropertyFunctionWrapper(PropertyFunction.BUTTON.codifiedEnum()),
                events = parametersJson["events"]?.jsonArray?.map {
                    EventObject(value = EventObjectValueWrapper(it.jsonObject["value"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: EventObjectValue.CLICK.codifiedEnum()))
                } ?: emptyList()
            )
            else -> error("Unsupported property type")
        }
    }

    private fun mapPropertyState(type: PropertyTypeWrapper, stateJson: JsonObject): PropertyStateObjectData? {
        return type.let {
            when (it) {
                PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> FloatPropertyStateObjectData(
                    state = FloatPropertyState(
                        propertyFunction = stateJson["state"]?.jsonObject?.get("function")?.jsonPrimitive?.content?.let { PropertyFunctionWrapper(it.codifiedEnum()) }
                            ?: PropertyFunctionWrapper(PropertyFunction.CUSTOM.codifiedEnum()),
                        propertyValue = FloatObjectValue(value = stateJson["state"]?.jsonObject?.get("value")?.jsonPrimitive?.float ?: 0.0f)
                    )
                )

                PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> EventPropertyStateObjectData(
                    state = EventPropertyState(
                        propertyFunction = stateJson["state"]?.jsonObject?.get("function")?.jsonPrimitive?.content?.let { PropertyFunctionWrapper(it.codifiedEnum()) }
                            ?: PropertyFunctionWrapper(PropertyFunction.CUSTOM.codifiedEnum()),
                        propertyValue = EventObject(value = stateJson["state"]?.jsonObject?.get("value")?.jsonPrimitive?.content?.let { EventObjectValueWrapper(it.codifiedEnum()) }
                            ?: EventObjectValueWrapper(EventObjectValue.NORMAL.codifiedEnum()))
                    )
                )

                else -> null
            }
        }
    }

    private fun mapScenarios(scenarios: List<JsonObject>): List<ScenarioObject> {
        return scenarios.map { scenarioJson ->
            ScenarioObject(
                id = scenarioJson["id"]?.jsonPrimitive?.content ?: "",
                name = scenarioJson["name"]?.jsonPrimitive?.content ?: "",
                isActive = scenarioJson["is_active"]?.jsonPrimitive?.boolean ?: false
            )
        }
    }

    private fun mapGroupCapability(capabilityJson: JsonObject): GroupCapabilityObject {
        val type = CapabilityTypeWrapper(capabilityJson["type"]?.jsonPrimitive?.content!!.codifiedEnum())
        val parameters = mapCapabilityParameters(type, capabilityJson["parameters"]?.jsonObject!!)
        return GroupCapabilityObject(
            type = type,
            retrievable = capabilityJson["retrievable"]?.jsonPrimitive?.boolean ?: false,
            parameters = parameters,
            state = capabilityJson["state"]?.jsonObject?.let { mapCapabilityState(type, it) }
        )
    }

    private fun mapHouseholds(households: List<JsonObject>): List<HouseholdObject> {
        return households.map { householdJson ->
            HouseholdObject(
                id = householdJson["id"]?.jsonPrimitive?.content ?: "",
                name = householdJson["name"]?.jsonPrimitive?.content ?: ""
            )
        }
    }
}
