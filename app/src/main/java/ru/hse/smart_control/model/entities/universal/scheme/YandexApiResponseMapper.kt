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
            type = DeviceTypeWrapper(DeviceType.valueOf(deviceStateResponse.type.content).codifiedEnum()),
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
                type = DeviceTypeWrapper(DeviceType.valueOf(deviceJson["type"]!!.jsonPrimitive.content).codifiedEnum()),
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
                type = CapabilityType.valueOf(capabilityJson["type"]!!.jsonPrimitive.content).codifiedEnum(),
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
                colorModel = parametersJson["color_model"]?.let { ColorModelWrapper(ColorModel.valueOf(it.jsonPrimitive.content).codifiedEnum()) },
                temperatureK = parametersJson["temperature_k"]?.let {
                    TemperatureK(
                        min = it.jsonObject["min"]!!.jsonPrimitive.int,
                        max = it.jsonObject["max"]!!.jsonPrimitive.int
                    )
                },
                colorScene = parametersJson["color_scene"]?.let {
                    ColorScene(scenes = it.jsonObject["scenes"]!!.jsonArray.map { sceneJson ->
                        Scene(id = SceneObjectWrapper(SceneObject.valueOf(sceneJson.jsonObject["id"]!!.jsonPrimitive.content).codifiedEnum()))
                    })
                }
            )
            CapabilityType.ON_OFF -> OnOffCapabilityParameterObject(
                split = parametersJson["split"]!!.jsonPrimitive.boolean
            )
            CapabilityType.MODE -> ModeCapabilityParameterObject(
                instance = ModeCapabilityInstanceWrapper(ModeCapability.valueOf(parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                modes = parametersJson["modes"]!!.jsonArray.map {
                    ModeObject(value = ModeCapabilityModeWrapper(ModeCapabilityMode.valueOf(it.jsonObject["value"]!!.jsonPrimitive.content).codifiedEnum()))
                }
            )
            CapabilityType.RANGE -> RangeCapabilityParameterObject(
                instance = RangeCapabilityWrapper(RangeCapability.valueOf(parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum()),
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
                instance = ToggleCapabilityWrapper(ToggleCapability.valueOf(parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum())
            )
            CapabilityType.VIDEO_STREAM -> VideoStreamCapabilityParameterObject(
                protocols = parametersJson["protocols"]!!.jsonArray.map {
                    VideoStreamCapabilityParameterObjectStreamProtocolWrapper(VideoStreamCapabilityParameterObjectStreamProtocol.valueOf(it.jsonPrimitive.content).codifiedEnum())
                }
            )
        }
    }

    private fun mapCapabilityState(stateJson: JsonObject?): CapabilityStateObjectData? {
        return when (stateJson?.get("type")?.jsonPrimitive?.contentOrNull?.let { CapabilityType.valueOf(it) }) {
            CapabilityType.ON_OFF -> stateJson?.let {
                OnOffCapabilityStateObjectData(
                    instance = OnOffCapabilityStateObjectInstanceWrapper(OnOffCapabilityStateObjectInstance.valueOf(it["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                    value = OnOffCapabilityStateObjectValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.boolean)
                )
            }

            CapabilityType.COLOR_SETTING -> stateJson?.let {
                ColorSettingCapabilityStateObjectData(
                    instance = ColorSettingCapabilityStateObjectInstanceWrapper(ColorSettingCapabilityStateObjectInstance.valueOf(it["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                    value = when (val valueJson = it["value"]!!.jsonObject["value"]!!) {
                        is JsonPrimitive -> ColorSettingCapabilityStateObjectValueInteger(value = valueJson.int)
                        is JsonObject -> when (valueJson["type"]?.jsonPrimitive?.content) {
                            "scene" -> ColorSettingCapabilityStateObjectValueObjectScene(value = SceneObjectWrapper(SceneObject.valueOf(valueJson["id"]!!.jsonPrimitive.content).codifiedEnum()))
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
                    instance = RangeCapabilityWrapper(RangeCapability.valueOf(it["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                    value = RangeCapabilityStateObjectDataValue(value = it["value"]!!.jsonObject["value"]!!.jsonPrimitive.float),
                    relative = it["relative"]?.jsonPrimitive?.boolean
                )
            }
            CapabilityType.MODE -> stateJson?.let {
                ModeCapabilityStateObjectData(
                    instance = ModeCapabilityInstanceWrapper(ModeCapability.valueOf(it["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                    value = ModeCapabilityModeWrapper(ModeCapabilityMode.valueOf(it["value"]!!.jsonPrimitive.content).codifiedEnum())
                )
            }
            CapabilityType.TOGGLE -> stateJson?.let {
                ToggleCapabilityStateObjectData(
                    instance = ToggleCapabilityWrapper(ToggleCapability.valueOf(it["instance"]!!.jsonPrimitive.content).codifiedEnum()),
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
                instance = PropertyFunctionWrapper(PropertyFunction.valueOf(parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                unit = MeasurementUnitWrapper(MeasurementUnit.valueOf(parametersJson["unit"]!!.jsonPrimitive.content).codifiedEnum())
            )
            PropertyType.EVENT -> EventPropertyParameterObject(
                instance = PropertyFunctionWrapper(PropertyFunction.valueOf(parametersJson["instance"]!!.jsonPrimitive.content).codifiedEnum()),
                events = parametersJson["events"]!!.jsonArray.map { EventObject(value = EventObjectValueWrapper(EventObjectValue.valueOf(it.jsonObject["value"]!!.jsonPrimitive.content).codifiedEnum())) }
            )
        }
    }

    private fun mapPropertyState(stateJson: JsonObject?): PropertyStateObjectData? {
        return stateJson?.let {
            when (PropertyTypeWrapper(PropertyType.valueOf(it["type"]!!.jsonPrimitive.content).codifiedEnum())) {
                PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> FloatPropertyStateObjectData(
                    state = FloatPropertyState(
                        propertyFunction = PropertyFunctionWrapper(PropertyFunction.valueOf(it["state"]!!.jsonObject["function"]!!.jsonPrimitive.content).codifiedEnum()),
                        propertyValue = FloatObjectValue(value = it["state"]!!.jsonObject["value"]!!.jsonPrimitive.float)
                    )
                )
                PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> EventPropertyStateObjectData(
                    state = EventPropertyState(
                        propertyFunction = PropertyFunctionWrapper(PropertyFunction.valueOf(it["state"]!!.jsonObject["function"]!!.jsonPrimitive.content).codifiedEnum()),
                        propertyValue = EventObject(value = EventObjectValueWrapper(EventObjectValue.valueOf(it["state"]!!.jsonObject["value"]!!.jsonPrimitive.content).codifiedEnum()))
                    )
                )
                else -> null
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
                type = CapabilityType.valueOf(capabilityJson["type"]!!.jsonPrimitive.content).codifiedEnum(),
                retrievable = capabilityJson["retrievable"]!!.jsonPrimitive.boolean,
                parameters = mapCapabilityParameters(capabilityJson["parameters"]!!.jsonObject),
                state = mapCapabilityState(capabilityJson["state"]?.jsonObject)
            )
        }
    }
}
