package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
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
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.GroupObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.HouseholdObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.MeasurementUnitWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.QuasarInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.RoomObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.ScenarioObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.SmartHomeInfo
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
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyFunction
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyFunctionWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.PropertyTypeWrapper

class YandexApiResponseMapper {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json{
        prettyPrint = true
        prettyPrintIndent = "  "
        encodeDefaults = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun mapYandexUserInfoResponseToJson(response: YandexUserInfoResponse): String {
        val responseJson = buildJsonObject {
            put("status", response.status)
            put("request_id", response.requestId)
            putJsonArray("rooms") {
                response.rooms.forEach { room ->
                    add(room)
                }
            }
            putJsonArray("groups") {
                response.groups.forEach { group ->
                    add(group)
                }
            }
            putJsonArray("devices") {
                response.devices.forEach { device ->
                    add(device)
                }
            }
            putJsonArray("scenarios") {
                response.scenarios.forEach { scenario ->
                    add(scenario)
                }
            }
            putJsonArray("households") {
                response.households.forEach { household ->
                    add(household)
                }
            }
        }
        return json.encodeToString(JsonElement.serializer(), responseJson)
    }

    fun mapUserInfoResponseFromJson(userInfoResponse: YandexUserInfoResponse): SmartHomeInfo {
        val roomObjects = mapRoomsFromJson(userInfoResponse.rooms)
        val groupObjects = mapGroupsFromJson(userInfoResponse.groups)
        val deviceObjects = mapDevicesFromJson(userInfoResponse.devices)
        val scenarioObjects = mapScenariosFromJson(userInfoResponse.scenarios)
        val householdObjects = mapHouseholdsFromJson(userInfoResponse.households)
        return SmartHomeInfo(roomObjects, groupObjects, deviceObjects, scenarioObjects, householdObjects)
    }

    fun mapUserInfoToResponseYandexUserInfoResponse(userInfo: SmartHomeInfo): YandexUserInfoResponse {
        return YandexUserInfoResponse(
            rooms = mapRoomsToJson(userInfo.rooms),
            groups = mapGroupsToJson(userInfo.groups),
            devices = mapDevicesToJson(userInfo.devices),
            scenarios = mapScenariosToJson(userInfo.scenarios),
            households = mapHouseholdsToJson(userInfo.households),
            requestId = "",
            status = ""
        )
    }

    fun mapDeviceStateResponseFromYandexDeviceStateResponse(deviceStateResponse: YandexDeviceStateResponse): DeviceObject {
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
            capabilities = mapCapabilitiesFromJson(deviceStateResponse.capabilities),
            properties = mapPropertiesFromJson(deviceStateResponse.properties),
            quasarInfo = deviceStateResponse.quasarInfo?.let { mapQuasarInfo(it) }
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun mapDeviceToJson(device: DeviceObject): JsonObject {
        return buildJsonObject {
            put("id", device.id)
            put("name", device.name)
            putJsonArray("aliases") {
                device.aliases.forEach { alias ->
                    add(JsonPrimitive(alias))
                }
            }
            put("type", device.type.type.code())
            put("external_id", device.externalId)
            put("skill_id", device.skillId)
            put("household_id", device.householdId)
            put("room", device.room)
            putJsonArray("groups") {
                device.groups.forEach { group ->
                    add(JsonPrimitive(group))
                }
            }
            put("capabilities", buildJsonArray {
                addAll(mapCapabilitiesToJson(device.capabilities))
            })
            put("properties", buildJsonArray {
                addAll(mapPropertiesToJson(device.properties))
            })
            device.quasarInfo?.let { put("quasar_info", mapQuasarInfoToJson(it)) }
        }
    }

    private fun mapQuasarInfo(quasarInfo: JsonObject): QuasarInfo {
        return QuasarInfo(
            deviceId = quasarInfo["device_id"]?.jsonPrimitive?.content ?: "",
            platform = quasarInfo["platform"]?.jsonPrimitive?.content ?: ""
        )
    }

    private fun mapQuasarInfoToJson(quasarInfo: QuasarInfo): JsonObject {
        return buildJsonObject {
            put("device_id", quasarInfo.deviceId)
            put("platform", quasarInfo.platform)
        }
    }

    private fun mapRoomsFromJson(rooms: List<JsonObject>): List<RoomObject> {
        return rooms.map { roomJson ->
            RoomObject(
                id = roomJson["id"]?.jsonPrimitive?.content ?: "",
                name = roomJson["name"]?.jsonPrimitive?.content ?: "",
                devices = roomJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                householdId = roomJson["household_id"]?.jsonPrimitive?.content ?: ""
            )
        }
    }

    private fun mapRoomsToJson(rooms: List<RoomObject>): List<JsonObject> {
        return rooms.map { room ->
            buildJsonObject {
                put("id", room.id)
                put("name", room.name)
                putJsonArray("devices") {
                    room.devices.forEach { deviceId ->
                        add(JsonPrimitive(deviceId))
                    }
                }
                put("household_id", room.householdId)
            }
        }
    }

    private fun mapGroupsFromJson(groups: List<JsonObject>): List<GroupObject> {
        return groups.map { groupJson ->
            GroupObject(
                id = groupJson["id"]?.jsonPrimitive?.content ?: "",
                name = groupJson["name"]?.jsonPrimitive?.content ?: "",
                aliases = groupJson["aliases"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                type = groupJson["type"]?.jsonPrimitive?.content?.let { DeviceTypeWrapper(it.codifiedEnum()) }
                    ?: DeviceTypeWrapper(DeviceType.OTHER.codifiedEnum()),
                capabilities = groupJson["capabilities"]?.jsonArray?.map { mapGroupCapabilityFromJson(it.jsonObject) } ?: emptyList(),
                devices = groupJson["devices"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList(),
                householdId = groupJson["household_id"]?.jsonPrimitive?.content ?: ""
            )
        }
    }

    private fun mapGroupsToJson(groups: List<GroupObject>): List<JsonObject> {
        return groups.map { group ->
            buildJsonObject {
                put("id", group.id)
                put("name", group.name)
                putJsonArray("aliases") {
                    group.aliases.forEach { alias ->
                        add(JsonPrimitive(alias))
                    }
                }
                put("type", group.type.type.code())
                putJsonArray("capabilities") {
                    group.capabilities.forEach { capability ->
                        add(mapGroupCapabilityToJson(capability))
                    }
                }
                putJsonArray("devices") {
                    group.devices.forEach { deviceId ->
                        add(JsonPrimitive(deviceId))
                    }
                }
                put("household_id", group.householdId)
            }
        }
    }

    private fun mapDevicesFromJson(devices: List<JsonObject>): List<DeviceObject> {
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
                capabilities = deviceJson["capabilities"]?.jsonArray?.mapNotNull { mapCapabilityFromJson(it.jsonObject) } ?: emptyList(),
                properties = deviceJson["properties"]?.jsonArray?.mapNotNull { mapPropertyFromJson(it.jsonObject) } ?: emptyList(),
                quasarInfo = deviceJson["quasar_info"]?.jsonObject?.let { mapQuasarInfo(it) }
            )
        }
    }

    private fun mapDevicesToJson(devices: List<DeviceObject>): List<JsonObject> {
        return devices.map { device ->
            mapDeviceToJson(device)
        }
    }

    private fun mapCapabilitiesFromJson(capabilities: List<JsonObject>): List<DeviceCapabilityObject> {
        return capabilities.mapNotNull { mapCapabilityFromJson(it) }
    }

    private fun mapCapabilitiesToJson(capabilities: List<DeviceCapabilityObject>): List<JsonObject> {
        return capabilities.map { deviceCapability ->
            mapCapabilityToJson(deviceCapability)
        }
    }

    private fun mapCapabilityFromJson(capabilityJson: JsonObject): DeviceCapabilityObject? {
        val type = CapabilityTypeWrapper(capabilityJson["type"]?.jsonPrimitive?.content!!.codifiedEnum())
        val parameters = capabilityJson["parameters"]?.jsonObject?.let { mapCapabilityParametersFromJson(type, it) }
        val state = if(capabilityJson["state"] is JsonNull)
            null
        else
            capabilityJson["state"]?.jsonObject?.let { mapCapabilityStateFromJson(type, it) }
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

    private fun mapCapabilityToJson(deviceCapability: DeviceCapabilityObject): JsonObject {
        val type = deviceCapability.type
        val parametersJson = mapCapabilityParametersToJson(type, deviceCapability.parameters)
        val stateJson = deviceCapability.state?.let { mapCapabilityStateToJson(type, it) }

        return buildJsonObject {
            put("type", type.type.code())
            put("reportable", deviceCapability.reportable)
            put("retrievable", deviceCapability.retrievable)
            put("parameters", parametersJson)
            stateJson?.let { put("state", it) }
            put("last_updated", deviceCapability.lastUpdated)
        }
    }

    private fun mapCapabilityParametersFromJson(typeWrapper: CapabilityTypeWrapper, parametersJson: JsonObject): CapabilityParameterObject {
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

    private fun mapCapabilityParametersToJson(typeWrapper: CapabilityTypeWrapper, capabilityParameters: CapabilityParameterObject): JsonObject {
        return when (typeWrapper) {
            CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> {
                capabilityParameters as? ColorSettingCapabilityParameterObject ?: error("Invalid capability parameters type")
                buildJsonObject {
                    capabilityParameters.colorModel?.let { put("color_model", it.colorModel.code()) }
                    capabilityParameters.temperatureK?.let {
                        putJsonObject("temperature_k") {
                            put("min", it.min)
                            put("max", it.max)
                        }
                    }
                    capabilityParameters.colorScene?.let {
                        putJsonObject("color_scene") {
                            putJsonArray("scenes") {
                                it.scenes.forEach { scene ->
                                    addJsonObject {
                                        put("id", scene.id.scene.code())
                                    }
                                }
                            }
                        }
                    }
                }
            }
            CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> {
                capabilityParameters as? OnOffCapabilityParameterObject ?: error("Invalid capability parameters type")
                buildJsonObject {
                    put("split", capabilityParameters.split)
                }
            }
            CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> {
                capabilityParameters as? ModeCapabilityParameterObject ?: error("Invalid capability parameters type")
                buildJsonObject {
                    put("instance", capabilityParameters.instance.mode.code())
                    putJsonArray("modes") {
                        capabilityParameters.modes.forEach { mode ->
                            addJsonObject {
                                put("value", mode.value.mode.code())
                            }
                        }
                    }
                }
            }
            CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> {
                capabilityParameters as? RangeCapabilityParameterObject ?: error("Invalid capability parameters type")
                buildJsonObject {
                    put("instance", capabilityParameters.instance.range.code())
                    put("random_access", capabilityParameters.randomAccess)
                    capabilityParameters.range?.let {
                        putJsonObject("range") {
                            put("min", it.min)
                            put("max", it.max)
                            put("precision", it.precision)
                        }
                    }
                    capabilityParameters.looped?.let { put("looped", it) }
                    capabilityParameters.unit?.let { put("unit", it.unit.code()) }
                }
            }
            CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> {
                capabilityParameters as? ToggleCapabilityParameterObject ?: error("Invalid capability parameters type")
                buildJsonObject {
                    put("instance", capabilityParameters.instance.toggle.code())
                }
            }
            CapabilityTypeWrapper(CapabilityType.VIDEO_STREAM.codifiedEnum()) -> {
                capabilityParameters as? VideoStreamCapabilityParameterObject ?: error("Invalid capability parameters type")
                buildJsonObject {
                    putJsonArray("protocols") {
                        capabilityParameters.protocols.forEach { protocol ->
                            add(JsonPrimitive(protocol.streamProtocol.code()))
                        }
                    }
                }
            }
            else -> error("Unsupported capability type")
        }
    }

    private fun mapCapabilityStateFromJson(typeWrapper: CapabilityTypeWrapper, stateJson: JsonObject): CapabilityStateObjectData? {
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
                        ?: ColorSettingCapabilityStateObjectInstanceWrapper(
                            ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
                    value = when (val valueJson = stateJson["value"]) {
                        is JsonPrimitive -> ColorSettingCapabilityStateObjectValueInteger(value = valueJson.int)
                        is JsonObject -> when (valueJson["type"]?.jsonPrimitive?.content) {
                            "scene" -> ColorSettingCapabilityStateObjectValueObjectScene(value = SceneObjectWrapper(valueJson["id"]?.jsonPrimitive?.content?.codifiedEnum() ?: SceneObject.ALARM.codifiedEnum()))
                            else -> ColorSettingCapabilityStateObjectValueObjectHSV(value = HSVObject(
                                h = valueJson["h"]?.jsonPrimitive?.int ?: 0,
                                s = valueJson["s"]?.jsonPrimitive?.int ?: 0,
                                v = valueJson["v"]?.jsonPrimitive?.int ?: 0
                            )
                            )
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

    private fun mapCapabilityStateToJson(typeWrapper: CapabilityTypeWrapper, capabilityState: CapabilityStateObjectData): JsonObject? {
        return when (typeWrapper) {
            CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()) -> {
                if (capabilityState !is OnOffCapabilityStateObjectData) return null
                buildJsonObject {
                    put("instance", capabilityState.instance.onOff.code())
                    put("value", capabilityState.value.value)
                }
            }

            CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()) -> {
                if (capabilityState !is ColorSettingCapabilityStateObjectData) return null
                buildJsonObject {
                    put("instance", capabilityState.instance.colorSetting.code())
                    put("value", when (val value = capabilityState.value) {
                        is ColorSettingCapabilityStateObjectValueInteger -> JsonPrimitive(value.value)
                        is ColorSettingCapabilityStateObjectValueObjectScene -> buildJsonObject {
                            put("type", "scene")
                            put("id", value.value.scene.code())
                        }
                        is ColorSettingCapabilityStateObjectValueObjectHSV -> buildJsonObject {
                            put("type", "hsv")
                            put("h", value.value.h)
                            put("s", value.value.s)
                            put("v", value.value.v)
                        }
                    })
                }
            }

            CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()) -> {
                if (capabilityState !is RangeCapabilityStateObjectData) return null
                buildJsonObject {
                    put("instance", capabilityState.instance.range.code())
                    put("value", capabilityState.value.value)
                    capabilityState.relative?.let { put("relative", it) }
                }
            }

            CapabilityTypeWrapper(CapabilityType.MODE.codifiedEnum()) -> {
                if (capabilityState !is ModeCapabilityStateObjectData) return null
                buildJsonObject {
                    put("instance", capabilityState.instance.mode.code())
                    put("value", capabilityState.value.mode.code())
                }
            }

            CapabilityTypeWrapper(CapabilityType.TOGGLE.codifiedEnum()) -> {
                if (capabilityState !is ToggleCapabilityStateObjectData) return null
                buildJsonObject {
                    put("instance", capabilityState.instance.toggle.code())
                    put("value", capabilityState.value.value)
                }
            }

            else -> null
        }
    }

    private fun mapPropertiesFromJson(properties: List<JsonObject>): List<DevicePropertyObject> {
        return properties.mapNotNull { mapPropertyFromJson(it) }
    }

    private fun mapPropertiesToJson(properties: List<DevicePropertyObject>): List<JsonObject> {
        return properties.map { deviceProperty ->
            mapPropertyToJson(deviceProperty)
        }
    }

    private fun mapPropertyFromJson(propertyJson: JsonObject): DevicePropertyObject? {
        val type = PropertyTypeWrapper(propertyJson["type"]?.jsonPrimitive?.content!!.codifiedEnum())
        val parameters = propertyJson["parameters"]?.jsonObject?.let { mapPropertyParametersFromJson(type, it) }
        val state = if(propertyJson["state"] !is JsonNull) mapPropertyStateFromJson(type, propertyJson["state"]!!.jsonObject) else null
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

    private fun mapPropertyToJson(deviceProperty: DevicePropertyObject): JsonObject {
        val type = deviceProperty.type
        val parametersJson = mapPropertyParametersToJson(type, deviceProperty.parameters)
        val stateJson = deviceProperty.state?.let { mapPropertyStateToJson(type, it) }

        return buildJsonObject {
            put("type", type.type.code())
            put("reportable", deviceProperty.reportable)
            put("retrievable", deviceProperty.retrievable)
            put("parameters", parametersJson)
            stateJson?.let { put("state", it) } ?: put("state", JsonNull)
            put("last_updated", deviceProperty.lastUpdated)
        }
    }

    private fun mapPropertyParametersFromJson(type: PropertyTypeWrapper, parametersJson: JsonObject): PropertyParameterObject {
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

    private fun mapPropertyParametersToJson(type: PropertyTypeWrapper, propertyParameters: PropertyParameterObject): JsonObject {
        return when (type) {
            PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> {
                propertyParameters as? FloatPropertyParameterObject ?: error("Invalid property parameters type")
                buildJsonObject {
                    put("instance", propertyParameters.instance.function.code())
                    put("unit", propertyParameters.unit.unit.code())
                }
            }
            PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> {
                propertyParameters as? EventPropertyParameterObject ?: error("Invalid property parameters type")
                buildJsonObject {
                    put("instance", propertyParameters.instance.function.code())
                    putJsonArray("events") {
                        propertyParameters.events.forEach { event ->
                            addJsonObject {
                                put("value", event.value.value.code())
                            }
                        }
                    }
                }
            }
            else -> error("Unsupported property type")
        }
    }

    private fun mapPropertyStateFromJson(type: PropertyTypeWrapper, stateJson: JsonObject): PropertyStateObjectData? {
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

    private fun mapPropertyStateToJson(type: PropertyTypeWrapper, propertyState: PropertyStateObjectData): JsonObject? {
        return when (type) {
            PropertyTypeWrapper(PropertyType.FLOAT.codifiedEnum()) -> {
                if (propertyState !is FloatPropertyStateObjectData) return null
                buildJsonObject {
                    putJsonObject("state") {
                        put("function", propertyState.state.propertyFunction.function.code())
                        put("value", propertyState.state.propertyValue.value)
                    }
                }
            }

            PropertyTypeWrapper(PropertyType.EVENT.codifiedEnum()) -> {
                if (propertyState !is EventPropertyStateObjectData) return null
                buildJsonObject {
                    putJsonObject("state") {
                        put("function", propertyState.state.propertyFunction.function.code())
                        put("value", propertyState.state.propertyValue.value.value.code())
                    }
                }
            }

            else -> null
        }
    }

    private fun mapScenariosFromJson(scenarios: List<JsonObject>): List<ScenarioObject> {
        return scenarios.map { scenarioJson ->
            ScenarioObject(
                id = scenarioJson["id"]?.jsonPrimitive?.content ?: "",
                name = scenarioJson["name"]?.jsonPrimitive?.content ?: "",
                isActive = scenarioJson["is_active"]?.jsonPrimitive?.boolean ?: false
            )
        }
    }

    private fun mapScenariosToJson(scenarios: List<ScenarioObject>): List<JsonObject> {
        return scenarios.map { scenario ->
            buildJsonObject {
                put("id", scenario.id)
                put("name", scenario.name)
                put("is_active", scenario.isActive)
            }
        }
    }

    private fun mapGroupCapabilityFromJson(capabilityJson: JsonObject): GroupCapabilityObject {
        val type = CapabilityTypeWrapper(capabilityJson["type"]?.jsonPrimitive?.content!!.codifiedEnum())
        val parameters = mapCapabilityParametersFromJson(type, capabilityJson["parameters"]?.jsonObject!!)
        return GroupCapabilityObject(
            type = type,
            retrievable = capabilityJson["retrievable"]?.jsonPrimitive?.boolean ?: false,
            parameters = parameters,
            state = capabilityJson["state"]?.jsonObject?.let { mapCapabilityStateFromJson(type, it) }
        )
    }

    private fun mapGroupCapabilityToJson(groupCapability: GroupCapabilityObject): JsonObject {
        val parametersJson = mapCapabilityParametersToJson(groupCapability.type, groupCapability.parameters)
        val stateJson = groupCapability.state?.let {
            mapCapabilityStateToJson(groupCapability.type, it)
        }

        return buildJsonObject {
            put("type", groupCapability.type.type.code())
            put("retrievable", groupCapability.retrievable)
            put("parameters", parametersJson)
            stateJson?.let { put("state", it) }
        }
    }

    private fun mapHouseholdsFromJson(households: List<JsonObject>): List<HouseholdObject> {
        return households.map { householdJson ->
            HouseholdObject(
                id = householdJson["id"]?.jsonPrimitive?.content ?: "",
                name = householdJson["name"]?.jsonPrimitive?.content ?: "",
                type = householdJson["type"]?.jsonPrimitive?.content ?: ""
            )
        }
    }

    private fun mapHouseholdsToJson(households: List<HouseholdObject>): List<JsonObject> {
        return households.map { household ->
            buildJsonObject {
                put("id", household.id)
                put("name", household.name)
                put("type", household.type)
            }
        }
    }
}
