package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
            type = DeviceType.valueOf(deviceStateResponse.type.content),
            externalId = deviceStateResponse.externalId,
            skillId = deviceStateResponse.skillId,
            householdId = "", // Отсутствует в ответе
            room = deviceStateResponse.room,
            groups = deviceStateResponse.groups,
            capabilities = mapCapabilities(deviceStateResponse.capabilities),
            properties = mapProperties(deviceStateResponse.properties),
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
                type = groupJson["type"]!!.jsonPrimitive.content,
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
                type = DeviceType.valueOf(deviceJson["type"]!!.jsonPrimitive.content),
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

    private fun mapCapabilities(capabilities: List<JsonObject>): List<DeviceCapabilityObject> {
        return capabilities.map { capabilityJson ->
            DeviceCapabilityObject(
                type = CapabilityType.valueOf(capabilityJson["type"]!!.jsonPrimitive.content),
                reportable = capabilityJson["reportable"]!!.jsonPrimitive.boolean,
                retrievable = capabilityJson["retrievable"]!!.jsonPrimitive.boolean,
                parameters = mapCapabilityParameters(capabilityJson["parameters"]!!.jsonObject),
                state = mapCapabilityState(capabilityJson["state"]?.jsonObject),
                lastUpdated = capabilityJson["last_updated"]!!.jsonPrimitive.float
            )
        }
    }

    private fun mapCapabilityParameters(parametersJson: JsonObject): CapabilityParameterObject {
        return when (val capabilityType = CapabilityType.valueOf(parametersJson["type"]!!.jsonPrimitive.content)) {
            CapabilityType.COLOR_SETTING -> ColorSettingCapabilityParameterObject(
                colorModel = parametersJson["color_model"]?.let { ColorModel.valueOf(it.jsonPrimitive.content) },
                temperatureK = parametersJson["temperature_k"]?.let {
                    TemperatureK(
                        min = it.jsonObject["min"]!!.jsonPrimitive.int,
                        max = it.jsonObject["max"]!!.jsonPrimitive.int
                    )
                },
                colorScene = parametersJson["color_scene"]?.let {
                    ColorScene(scenes = it.jsonObject["scenes"]!!.jsonArray.map { sceneJson ->
                        Scene(id = SceneObject.valueOf(sceneJson.jsonObject["id"]!!.jsonPrimitive.content))
                    })
                }
            )
            CapabilityType.ON_OFF -> OnOffCapabilityParameterObject(
                split = parametersJson["split"]!!.jsonPrimitive.boolean
            )
            CapabilityType.MODE -> ModeCapabilityParameterObject(
                instance = ModeCapabilityInstance.valueOf(parametersJson["instance"]!!.jsonPrimitive.content),
                modes = parametersJson["modes"]!!.jsonArray.map {
                    ModeObject(value = ModeCapabilityMode.valueOf(it.jsonObject["value"]!!.jsonPrimitive.content))
                }
            )
            CapabilityType.RANGE -> RangeCapabilityParameterObject(
                instance = RangeCapabilityParameterObjectFunction.valueOf(parametersJson["instance"]!!.jsonPrimitive.content),
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
            CapabilityType.TOGGLE -> ToggleCapabilityParameterObject(
                instance = ToggleCapabilityParameterObjectFunction.valueOf(parametersJson["instance"]!!.jsonPrimitive.content)
            )
            CapabilityType.VIDEO_STREAM -> VideoStreamCapabilityParameterObject(
                protocols = parametersJson["protocols"]!!.jsonArray.map {
                    VideoStreamCapabilityParameterObjectStreamProtocol.valueOf(it.jsonPrimitive.content)
                }
            )
        }
    }

    private fun mapCapabilityState(stateJson: JsonObject?): CapabilityStateObjectData? {
        return when (stateJson?.get("type")?.jsonPrimitive?.contentOrNull?.let { CapabilityType.valueOf(it) }) {
            CapabilityType.ON_OFF -> stateJson?.let {
                OnOffCapabilityStateObjectData(
                    instance = OnOffCapabilityStateObjectInstance.valueOf(it["instance"]!!.jsonPrimitive.content),
                    value = OnOffCapabilityStateObjectValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.boolean)
                )
            }

            CapabilityType.COLOR_SETTING -> stateJson?.let {
                ColorSettingCapabilityStateObjectData(
                    instance = ColorSettingCapabilityStateObjectInstance.valueOf(it["instance"]!!.jsonPrimitive.content),
                    value = when (val valueJson = it["value"]!!.jsonObject["value"]!!) {
                        is JsonPrimitive -> ColorSettingCapabilityStateObjectValueInteger(value = valueJson.int)
                        is JsonObject -> when (valueJson["type"]?.jsonPrimitive?.content) {
                            "scene" -> ColorSettingCapabilityStateObjectValueObjectScene(value = SceneObject.valueOf(valueJson["id"]!!.jsonPrimitive.content))
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
            CapabilityType.RANGE -> stateJson?.let {
                RangeCapabilityStateObjectData(
                    instance = RangeCapabilityParameterObjectFunction.valueOf(it["instance"]!!.jsonPrimitive.content),
                    value = RangeCapabilityStateObjectDataValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.float),
                    relative = it["relative"]?.jsonPrimitive?.boolean
                )
            }
            CapabilityType.MODE -> stateJson?.let {
                ModeCapabilityStateObjectData(
                    instance = ModeCapabilityInstance.valueOf(it["instance"]!!.jsonPrimitive.content),
                    value = ModeCapabilityMode.valueOf(it["value"]!!.jsonPrimitive.content)
                )
            }
            CapabilityType.TOGGLE -> stateJson?.let {
                ToggleCapabilityStateObjectData(
                    instance = ToggleCapabilityParameterObjectFunction.valueOf(it["instance"]!!.jsonPrimitive.content),
                    value = ToggleCapabilityStateObjectDataValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.boolean)
                )
            }
            null -> null
            else -> error("Unsupported capability state type")
        }
    }

    private fun mapProperties(properties: List<JsonObject>): List<DevicePropertyObject> {
        return properties.map { propertyJson ->
            DevicePropertyObject(
                type = propertyJson["type"]!!.jsonPrimitive.content,
                reportable = propertyJson["reportable"]!!.jsonPrimitive.boolean,
                retrievable = propertyJson["retrievable"]!!.jsonPrimitive.boolean,
                parameters = mapPropertyParameters(propertyJson["parameters"]!!.jsonObject),
                state = mapPropertyState(propertyJson["state"]?.jsonObject),
                lastUpdated = propertyJson["last_updated"]!!.jsonPrimitive.float
            )
        }
    }

    private fun mapPropertyParameters(parametersJson: JsonObject): PropertyParameterObject {
        return when (val propertyType = PropertyType.valueOf(parametersJson["type"]!!.jsonPrimitive.content)) {
            PropertyType.FLOAT -> FloatPropertyParameterObject(
                instance = PropertyFunction.valueOf(parametersJson["instance"]!!.jsonPrimitive.content),
                unit = MeasurementUnit.valueOf(parametersJson["unit"]!!.jsonPrimitive.content)
            )
            PropertyType.EVENT -> EventPropertyParameterObject(
                instance = PropertyFunction.valueOf(parametersJson["instance"]!!.jsonPrimitive.content),
                events = parametersJson["events"]!!.jsonArray.map { EventObject(value = EventObjectValue.valueOf(it.jsonObject["value"]!!.jsonPrimitive.content)) }
            )
        }
    }

    private fun mapPropertyState(stateJson: JsonObject?): PropertyStateObjectData? {
        return stateJson?.let {
            when (PropertyType.valueOf(it["type"]!!.jsonPrimitive.content)) {
                PropertyType.FLOAT -> FloatPropertyStateObjectData(
                    state = FloatPropertyState(
                        propertyFunction = PropertyFunction.valueOf(it["state"]!!.jsonObject["function"]!!.jsonPrimitive.content),
                        propertyValue = FloatObjectValue(value = it["state"]!!.jsonObject["value"]!!.jsonPrimitive.float)
                    )
                )
                PropertyType.EVENT -> EventPropertyStateObjectData(
                    state = EventPropertyState(
                        propertyFunction = PropertyFunction.valueOf(it["state"]!!.jsonObject["function"]!!.jsonPrimitive.content),
                        propertyValue = EventObject(value = EventObjectValue.valueOf(it["state"]!!.jsonObject["value"]!!.jsonPrimitive.content))
                    )
                )
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

    private fun mapHouseholds(households: List<JsonObject>): List<HouseholdObject> {
        return households.map { householdJson ->
            HouseholdObject(
                id = householdJson["id"]!!.jsonPrimitive.content,
                name = householdJson["name"]!!.jsonPrimitive.content,
                type = householdJson["type"]!!.jsonPrimitive.content
            )
        }
    }

    private fun mapGroupCapabilities(capabilities: List<JsonObject>): List<GroupCapabilityObject> {
        return capabilities.map { capabilityJson ->
            GroupCapabilityObject(
                type = CapabilityType.valueOf(capabilityJson["type"]!!.jsonPrimitive.content),
                retrievable = capabilityJson["retrievable"]!!.jsonPrimitive.boolean,
                parameters = mapCapabilityParameters(capabilityJson["parameters"]!!.jsonObject),
                state = mapCapabilityState(capabilityJson["state"]?.jsonObject)
            )
        }
    }
}
