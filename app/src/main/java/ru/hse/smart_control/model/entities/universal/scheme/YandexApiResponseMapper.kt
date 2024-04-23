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
            householdId = "", // Отсутствует в ответе
            room = deviceStateResponse.room,
            groups = deviceStateResponse.groups,
            capabilities = deviceStateResponse.capabilities?.let { mapCapabilities(it) } ?: emptyList(),
            properties = deviceStateResponse.properties?.let { mapProperties(it) } ?: emptyList(),
            quasarInfo = null // Отсутствует в ответе
        )
    }

    private fun mapRooms(rooms: List<JsonObject>): List<RoomObject> {
        return rooms.map { roomJson ->
            RoomObject(
                id = roomJson["id"]!!.jsonPrimitive.content,
                name = roomJson["name"]!!.jsonPrimitive.content,
                devices = roomJson["devices"]!!.jsonArray.map { it.jsonPrimitive.content },
                householdId = roomJson["household_id"]!!.jsonPrimitive.content
            )
        }
    }

    private fun mapGroups(groups: List<JsonObject>): List<GroupObject> {
        return groups.map { groupJson ->
            GroupObject(
                id = groupJson["id"]!!.jsonPrimitive.content,
                name = groupJson["name"]!!.jsonPrimitive.content,
                aliases = groupJson["aliases"]!!.jsonArray.map { it.jsonPrimitive.content },
                type = DeviceTypeWrapper(groupJson["type"]!!.jsonPrimitive.content.codifiedEnum()),
                capabilities = mapGroupCapabilities(groupJson["capabilities"]!!.jsonArray.map { it.jsonObject }),
                devices = groupJson["devices"]!!.jsonArray.map { it.jsonPrimitive.content },
                householdId = groupJson["household_id"]!!.jsonPrimitive.content
            )
        }
    }

    private fun mapDevices(devices: List<JsonObject>): List<DeviceObject> {
        return devices.map { deviceJson ->
            DeviceObject(
                id = deviceJson["id"]!!.jsonPrimitive.content,
                name = deviceJson["name"]!!.jsonPrimitive.content,
                aliases = deviceJson["aliases"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                type = DeviceTypeWrapper(deviceJson["type"]!!.jsonPrimitive.content.codifiedEnum()),
                externalId = deviceJson["external_id"]!!.jsonPrimitive.content,
                skillId = deviceJson["skill_id"]!!.jsonPrimitive.content,
                householdId = deviceJson["household_id"]!!.jsonPrimitive.content,
                room = deviceJson["room"]?.jsonPrimitive?.content,
                groups = deviceJson["groups"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                capabilities = mapCapabilities(deviceJson["capabilities"]?.jsonArray?.map { it.jsonObject } ?: emptyList()),
                properties = mapProperties(deviceJson["properties"]?.jsonArray?.map { it.jsonObject } ?: emptyList()),
                quasarInfo = null // Отсутствует в ответе
            )
        }
    }

    private fun mapCapabilities(capabilities: List<JsonElement>): List<DeviceCapabilityObject> {
        return capabilities.mapNotNull { capabilityJson ->
            val type = (capabilityJson as? JsonObject)?.get("type")?.jsonPrimitive?.contentOrNull?.let { CapabilityTypeWrapper(it.codifiedEnum()) }
            val parameters = (capabilityJson as? JsonObject)?.get("parameters")?.jsonObject?.let { mapCapabilityParameters(type, it) }
            val state = when (val stateElement = (capabilityJson as? JsonObject)?.get("state")) {
                is JsonObject -> mapCapabilityState(stateElement)
                is JsonNull -> null
                else -> null
            }
            if (type != null && parameters != null) {
                DeviceCapabilityObject(
                    type = type,
                    reportable = capabilityJson["reportable"]?.jsonPrimitive?.boolean ?: false,
                    retrievable = capabilityJson["retrievable"]?.jsonPrimitive?.boolean ?: false,
                    parameters = parameters,
                    state = state,
                    lastUpdated = (capabilityJson as? JsonObject)?.get("last_updated")?.jsonPrimitive?.float ?: 0.0f
                )
            } else {
                null
            }
        }
    }

    private fun mapCapabilityParameters(type: CapabilityTypeWrapper?, parametersJson: JsonObject): CapabilityParameterObject {
        return when (type) {
            CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> ColorSettingCapabilityParameterObject(
                colorModel = parametersJson["color_model"]?.let { ColorModelWrapper(it.jsonPrimitive.content.codifiedEnum()) },
                temperatureK = parametersJson["temperature_k"]?.let {
                    TemperatureK(
                        min = it.jsonObject["min"]?.jsonPrimitive?.int ?: 0,
                        max = it.jsonObject["max"]?.jsonPrimitive?.int ?: 0
                    )
                },
                colorScene = parametersJson["color_scene"]?.let {
                    ColorScene(scenes = it.jsonObject["scenes"]?.jsonArray?.map { sceneJson ->
                        Scene(id = SceneObjectWrapper(sceneJson.jsonObject["id"]?.jsonPrimitive?.content?.codifiedEnum() ?: SceneObject.ALARM.codifiedEnum()))
                    } ?: emptyList())
                }
            )
            CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> OnOffCapabilityParameterObject(
                split = parametersJson["split"]!!.jsonPrimitive.boolean
            )
            CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> ModeCapabilityParameterObject(
                instance = ModeCapabilityInstanceWrapper((parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                modes = parametersJson["modes"]!!.jsonArray.map {
                    ModeObject(value = ModeCapabilityModeWrapper((it.jsonObject["value"]!!.jsonPrimitive.content).codifiedEnum()))
                }
            )
            CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> RangeCapabilityParameterObject(
                instance = RangeCapabilityWrapper((parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                randomAccess = parametersJson["random_access"]!!.jsonPrimitive.boolean,
                range = parametersJson["range"]?.let {
                    Range(
                        min = it.jsonObject["min"]!!.jsonPrimitive.float,
                        max = it.jsonObject["max"]!!.jsonPrimitive.float,
                        precision = it.jsonObject["precision"]!!.jsonPrimitive.float
                    )
                },
                looped = parametersJson["looped"]?.jsonPrimitive?.boolean
            )
            CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> ToggleCapabilityParameterObject(
                instance = ToggleCapabilityWrapper((parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum())
            )
            CapabilityTypeWrapper(CapabilityType.VIDEO_STREAM.codifiedEnum()) -> VideoStreamCapabilityParameterObject(
                protocols = parametersJson["protocols"]!!.jsonArray.map {
                    VideoStreamCapabilityParameterObjectStreamProtocolWrapper((it.jsonPrimitive.content).codifiedEnum())
                }
            )
            else -> error("Unsupported capability type")
        }
    }

    private fun mapCapabilityState(stateJson: JsonObject?): CapabilityStateObjectData? {
        return when (stateJson?.get("type")?.jsonPrimitive?.contentOrNull?.let { CapabilityTypeWrapper(it.codifiedEnum()) }) {
            CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> stateJson?.let {
                OnOffCapabilityStateObjectData(
                    instance = OnOffCapabilityStateObjectInstanceWrapper(it["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                    value = OnOffCapabilityStateObjectValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.boolean)
                )
            }

            CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> stateJson?.let {
                ColorSettingCapabilityStateObjectData(
                    instance = ColorSettingCapabilityStateObjectInstanceWrapper(it["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                    value = when (val valueJson = it["value"]!!.jsonObject["value"]!!) {
                        is JsonPrimitive -> ColorSettingCapabilityStateObjectValueInteger(value = valueJson.int)
                        is JsonObject -> when (valueJson["type"]?.jsonPrimitive?.content) {
                            "scene" -> ColorSettingCapabilityStateObjectValueObjectScene(value = SceneObjectWrapper(valueJson["id"]!!.jsonPrimitive.content.codifiedEnum()))
                            else -> ColorSettingCapabilityStateObjectValueObjectHSV(value = HSVObject(
                                h = valueJson["h"]!!.jsonPrimitive.int,
                                s = valueJson["s"]!!.jsonPrimitive.int,
                                v = valueJson["v"]!!.jsonPrimitive.int
                            ))
                        }
                        else -> error("Unsupported color setting value type")
                    }
                )
            }
            CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> stateJson?.let {
                RangeCapabilityStateObjectData(
                    instance = RangeCapabilityWrapper(it["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                    value = RangeCapabilityStateObjectDataValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.float),
                    relative = it["relative"]?.jsonPrimitive?.boolean
                )
            }
            CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> stateJson?.let {
                ModeCapabilityStateObjectData(
                    instance = ModeCapabilityInstanceWrapper(it["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                    value = ModeCapabilityModeWrapper(it["value"]!!.jsonPrimitive.content.codifiedEnum())
                )
            }
            CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> stateJson?.let {
                ToggleCapabilityStateObjectData(
                    instance = ToggleCapabilityWrapper(it["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                    value = ToggleCapabilityStateObjectDataValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.boolean)
                )
            }
            null -> null
            else -> error("Unsupported capability state type")
        }
    }

    private fun mapProperties(properties: List<JsonObject>): List<DevicePropertyObject> {
        return properties.map { propertyJson ->
            val type = PropertyTypeWrapper(propertyJson["type"]!!.jsonPrimitive.content.codifiedEnum())
            val state = when (val stateElement = (propertyJson as? JsonObject)?.get("state")) {
                is JsonObject -> mapPropertyState(stateElement)
                is JsonNull -> null
                else -> null
            }
            DevicePropertyObject(
                type = type,
                reportable = propertyJson["reportable"]!!.jsonPrimitive.boolean,
                retrievable = propertyJson["retrievable"]!!.jsonPrimitive.boolean,
                parameters = mapPropertyParameters(type, propertyJson["parameters"]!!.jsonObject),
                state = state,
                lastUpdated = propertyJson["last_updated"]!!.jsonPrimitive.float
            )
        }
    }

    private fun mapPropertyParameters(type: PropertyTypeWrapper, parametersJson: JsonObject): PropertyParameterObject {
        return when (type) {
            PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> FloatPropertyParameterObject(
                instance = PropertyFunctionWrapper(parametersJson["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                unit = MeasurementUnitWrapper(parametersJson["unit"]!!.jsonPrimitive.content.codifiedEnum())
            )
            PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> EventPropertyParameterObject(
                instance = PropertyFunctionWrapper(parametersJson["instance"]!!.jsonPrimitive.content.codifiedEnum()),
                events = parametersJson["events"]!!.jsonArray.map { EventObject(value = EventObjectValueWrapper(it.jsonObject["value"]!!.jsonPrimitive.content.codifiedEnum())) }
            )
            else -> error("Unsupported property type")
        }
    }

    private fun mapPropertyState(stateJson: JsonObject?): PropertyStateObjectData? {
        return stateJson?.let {
            when (PropertyTypeWrapper(it["type"]!!.jsonPrimitive.content.codifiedEnum())) {
                PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> FloatPropertyStateObjectData(
                    state = FloatPropertyState(
                        propertyFunction = PropertyFunctionWrapper(it["state"]!!.jsonObject["function"]!!.jsonPrimitive.content.codifiedEnum()),
                        propertyValue = FloatObjectValue(value = it["state"]!!.jsonObject["value"]!!.jsonPrimitive.float)
                    )
                )
                PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> EventPropertyStateObjectData(
                    state = EventPropertyState(
                        propertyFunction = PropertyFunctionWrapper(it["state"]!!.jsonObject["function"]!!.jsonPrimitive.content.codifiedEnum()),
                        propertyValue = EventObject(value = EventObjectValueWrapper(it["state"]!!.jsonObject["value"]!!.jsonPrimitive.content.codifiedEnum()))
                    )
                )
                else -> error("Unsupported property state type")
            }
        }
    }


    private fun mapScenarios(scenarios: List<JsonObject>): List<ScenarioObject> {
        return scenarios.map { scenarioJson ->
            ScenarioObject(
                id = scenarioJson["id"]!!.jsonPrimitive.content,
                name = scenarioJson["name"]!!.jsonPrimitive.content,
                isActive = scenarioJson["is_active"]!!.jsonPrimitive.boolean
            )
        }
    }

    private fun mapGroupCapabilities(capabilities: List<JsonObject>): List<GroupCapabilityObject> {
        return capabilities.mapNotNull { capabilityJson ->
            val type = capabilityJson["type"]?.jsonPrimitive?.contentOrNull?.let { CapabilityTypeWrapper(it.codifiedEnum()) }
            val parameters = capabilityJson["parameters"]?.jsonObject?.let { mapCapabilityParameters(type, it) }
            if (type != null && parameters != null) {
                GroupCapabilityObject(
                    type = type,
                    retrievable = capabilityJson["retrievable"]?.jsonPrimitive?.boolean ?: false,
                    parameters = parameters,
                    state = capabilityJson["state"]?.jsonObject?.let { mapCapabilityState(it) }
                )
            } else {
                null
            }
        }
    }

    private fun mapHouseholds(households: List<JsonObject>): List<HouseholdObject> {
        return households.map { householdJson ->
            HouseholdObject(
                id = householdJson["id"]!!.jsonPrimitive.content,
                name = householdJson["name"]!!.jsonPrimitive.content
            )
        }
    }

}
