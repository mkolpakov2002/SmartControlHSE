package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import ru.hse.smart_control.model.entities.universal.ColorSettingParameters
import ru.hse.smart_control.model.entities.universal.OnOffParameters
import ru.hse.smart_control.model.entities.universal.RangeParameters

@Serializable
sealed interface Response {
    val status: String
    @SerialName("request_id")
    val requestId: String
}

@Serializable
sealed class ApiResponse {
    @Serializable
    data class SuccessUserInfo(val data: UserInfoResponse) : ApiResponse()
    @Serializable
    data class SuccessDeviceState(val data: DeviceStateResponse) : ApiResponse()
    @Serializable
    data class Error(val error: ErrorModel) : ApiResponse()
}

@Serializable
data class ErrorModel(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    val error: String
): Response

@Serializable
data class UserInfoResponse(
    @SerialName("request_id") override val requestId: String,
    @SerialName("status") override val status: String,
    @SerialName("rooms") val rooms: List<RoomObject>,
    @SerialName("groups") val groups: List<GroupObject>,
    @SerialName("devices") val devices: List<DeviceObject>,
    @SerialName("scenarios") val scenarios: List<ScenarioObject>,
    @SerialName("households") val households: List<HouseholdObject>
): Response

@Serializable
data class RoomObject(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("devices") val devices: List<String>,
    @SerialName("household_id") val householdId: String
)

@Serializable
data class GroupObject(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("aliases") val aliases: List<String>,
    @SerialName("type") val type: String,
    @SerialName("capabilities") val capabilities: List<GroupCapabilityObject>,
    @SerialName("devices") val devices: List<String>,
    @SerialName("household_id") val householdId: String
)

@Serializable
sealed interface BaseDeviceObject {
    @SerialName("id") val id: String
    @SerialName("name") val name: String
    @SerialName("aliases") val aliases: List<String>
    @SerialName("type") val type: DeviceType
    @SerialName("groups") val groups: List<String>
    @SerialName("room") val room: String?
    @SerialName("external_id") val externalId: String
    @SerialName("skill_id") val skillId: String
    @SerialName("capabilities") val capabilities: List<Capability>
    @SerialName("properties") val properties: List<Property>
}

//@Serializable(with = DeviceTypeSerializer::class)
@Serializable
sealed class DeviceType {
    @Serializable
    @SerialName("devices.types.light")
    data object Light : DeviceType()

    @Serializable
    @SerialName("devices.types.socket")
    data object Socket : DeviceType()

    @Serializable
    @SerialName("devices.types.switch")
    data object Switch : DeviceType()

    @Serializable
    @SerialName("devices.types.thermostat")
    data object Thermostat : DeviceType()

    @Serializable
    @SerialName("devices.types.thermostat.ac")
    data object ThermostatAC : DeviceType()

    @Serializable
    @SerialName("devices.types.media_device")
    data object MediaDevice : DeviceType()

    @Serializable
    @SerialName("devices.types.media_device.tv")
    data object MediaDeviceTV : DeviceType()

    @Serializable
    @SerialName("devices.types.media_device.tv_box")
    data object MediaDeviceTVBox : DeviceType()

    @Serializable
    @SerialName("devices.types.media_device.receiver")
    data object MediaDeviceReceiver : DeviceType()

    @Serializable
    @SerialName("devices.types.cooking")
    data object Cooking : DeviceType()

    @Serializable
    @SerialName("devices.types.cooking.coffee_maker")
    data object CoffeeMaker : DeviceType()

    @Serializable
    @SerialName("devices.types.cooking.kettle")
    data object Kettle : DeviceType()

    @Serializable
    @SerialName("devices.types.cooking.multicooker")
    data object Multicooker : DeviceType()

    @Serializable
    @SerialName("devices.types.openable")
    data object Openable : DeviceType()

    @Serializable
    @SerialName("devices.types.openable.curtain")
    data object OpenableCurtain : DeviceType()

    @Serializable
    @SerialName("devices.types.humidifier")
    data object Humidifier : DeviceType()

    @Serializable
    @SerialName("devices.types.purifier")
    data object Purifier : DeviceType()

    @Serializable
    @SerialName("devices.types.vacuum_cleaner")
    data object VacuumCleaner : DeviceType()

    @Serializable
    @SerialName("devices.types.washing_machine")
    data object WashingMachine : DeviceType()

    @Serializable
    @SerialName("devices.types.dishwasher")
    data object Dishwasher : DeviceType()

    @Serializable
    @SerialName("devices.types.iron")
    data object Iron : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor")
    data object Sensor : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.motion")
    data object SensorMotion : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.door")
    data object SensorDoor : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.window")
    data object SensorWindow : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.water_leak")
    data object SensorWaterLeak : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.smoke")
    data object SensorSmoke : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.gas")
    data object SensorGas : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.vibration")
    data object SensorVibration : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.button")
    data object SensorButton : DeviceType()

    @Serializable
    @SerialName("devices.types.sensor.illumination")
    data object SensorIllumination : DeviceType()

    @Serializable
    @SerialName("devices.types.other")
    data object Other : DeviceType()

    @Serializable
    data class CustomDeviceType(
        @SerialName(value = "serialName") val serialName: String
    ) : DeviceType()
}

object DeviceTypeSerializer : KSerializer<DeviceType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DeviceType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DeviceType) {
        when (value) {
            is DeviceType.Light -> encoder.encodeString("devices.types.light")
            is DeviceType.Socket -> encoder.encodeString("devices.types.socket")
            is DeviceType.Switch -> encoder.encodeString("devices.types.switch")
            is DeviceType.Thermostat -> encoder.encodeString("devices.types.thermostat")
            is DeviceType.ThermostatAC -> encoder.encodeString("devices.types.thermostat.ac")
            is DeviceType.MediaDevice -> encoder.encodeString("devices.types.media_device")
            is DeviceType.MediaDeviceTV -> encoder.encodeString("devices.types.media_device.tv")
            is DeviceType.MediaDeviceTVBox -> encoder.encodeString("devices.types.media_device.tv_box")
            is DeviceType.MediaDeviceReceiver -> encoder.encodeString("devices.types.media_device.receiver")
            is DeviceType.Cooking -> encoder.encodeString("devices.types.cooking")
            is DeviceType.CoffeeMaker -> encoder.encodeString("devices.types.cooking.coffee_maker")
            is DeviceType.Kettle -> encoder.encodeString("devices.types.cooking.kettle")
            is DeviceType.Multicooker -> encoder.encodeString("devices.types.cooking.multicooker")
            is DeviceType.Openable -> encoder.encodeString("devices.types.openable")
            is DeviceType.OpenableCurtain -> encoder.encodeString("devices.types.openable.curtain")
            is DeviceType.Humidifier -> encoder.encodeString("devices.types.humidifier")
            is DeviceType.Purifier -> encoder.encodeString("devices.types.purifier")
            is DeviceType.VacuumCleaner -> encoder.encodeString("devices.types.vacuum_cleaner")
            is DeviceType.WashingMachine -> encoder.encodeString("devices.types.washing_machine")
            is DeviceType.Dishwasher -> encoder.encodeString("devices.types.dishwasher")
            is DeviceType.Iron -> encoder.encodeString("devices.types.iron")
            is DeviceType.Sensor -> encoder.encodeString("devices.types.sensor")
            is DeviceType.SensorMotion -> encoder.encodeString("devices.types.sensor.motion")
            is DeviceType.SensorDoor -> encoder.encodeString("devices.types.sensor.door")
            is DeviceType.SensorWindow -> encoder.encodeString("devices.types.sensor.window")
            is DeviceType.SensorWaterLeak -> encoder.encodeString("devices.types.sensor.water_leak")
            is DeviceType.SensorSmoke -> encoder.encodeString("devices.types.sensor.smoke")
            is DeviceType.SensorGas -> encoder.encodeString("devices.types.sensor.gas")
            is DeviceType.SensorVibration -> encoder.encodeString("devices.types.sensor.vibration")
            is DeviceType.SensorButton -> encoder.encodeString("devices.types.sensor.button")
            is DeviceType.SensorIllumination -> encoder.encodeString("devices.types.sensor.illumination")
            is DeviceType.Other -> encoder.encodeString("devices.types.other")
            is DeviceType.CustomDeviceType -> encoder.encodeString(value.serialName)
        }
    }

    override fun deserialize(decoder: Decoder): DeviceType {
        return when (decoder) {
            is JsonDecoder -> {
                val element = decoder.decodeJsonElement()
                when (element) {
                    is JsonPrimitive -> {
                        when (element.content) {
                            "devices.types.light" -> DeviceType.Light
                            "devices.types.socket" -> DeviceType.Socket
                            "devices.types.switch" -> DeviceType.Switch
                            "devices.types.thermostat" -> DeviceType.Thermostat
                            "devices.types.thermostat.ac" -> DeviceType.ThermostatAC
                            "devices.types.media_device" -> DeviceType.MediaDevice
                            "devices.types.media_device.tv" -> DeviceType.MediaDeviceTV
                            "devices.types.media_device.tv_box" -> DeviceType.MediaDeviceTVBox
                            "devices.types.media_device.receiver" -> DeviceType.MediaDeviceReceiver
                            "devices.types.cooking" -> DeviceType.Cooking
                            "devices.types.cooking.coffee_maker" -> DeviceType.CoffeeMaker
                            "devices.types.cooking.kettle" -> DeviceType.Kettle
                            "devices.types.cooking.multicooker" -> DeviceType.Multicooker
                            "devices.types.openable" -> DeviceType.Openable
                            "devices.types.openable.curtain" -> DeviceType.OpenableCurtain
                            "devices.types.humidifier" -> DeviceType.Humidifier
                            "devices.types.purifier" -> DeviceType.Purifier
                            "devices.types.vacuum_cleaner" -> DeviceType.VacuumCleaner
                            "devices.types.washing_machine" -> DeviceType.WashingMachine
                            "devices.types.dishwasher" -> DeviceType.Dishwasher
                            "devices.types.iron" -> DeviceType.Iron
                            "devices.types.sensor" -> DeviceType.Sensor
                            "devices.types.sensor.motion" -> DeviceType.SensorMotion
                            "devices.types.sensor.door" -> DeviceType.SensorDoor
                            "devices.types.sensor.window" -> DeviceType.SensorWindow
                            "devices.types.sensor.water_leak" -> DeviceType.SensorWaterLeak
                            "devices.types.sensor.smoke" -> DeviceType.SensorSmoke
                            "devices.types.sensor.gas" -> DeviceType.SensorGas
                            "devices.types.sensor.vibration" -> DeviceType.SensorVibration
                            "devices.types.sensor.button" -> DeviceType.SensorButton
                            "devices.types.sensor.illumination" -> DeviceType.SensorIllumination
                            "devices.types.other" -> DeviceType.Other
                            else -> DeviceType.CustomDeviceType(element.content)
                        }
                    }
                    is JsonObject -> DeviceType.CustomDeviceType(element["serialName"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Expected 'serialName' field"))
                    else -> throw SerializationException("Expected JsonObject or JsonPrimitive")
                }
            }
            else -> {
                when (val value = decoder.decodeString()) {
                    "devices.types.light" -> DeviceType.Light
                    "devices.types.socket" -> DeviceType.Socket
                    "devices.types.switch" -> DeviceType.Switch
                    "devices.types.thermostat" -> DeviceType.Thermostat
                    "devices.types.thermostat.ac" -> DeviceType.ThermostatAC
                    "devices.types.media_device" -> DeviceType.MediaDevice
                    "devices.types.media_device.tv" -> DeviceType.MediaDeviceTV
                    "devices.types.media_device.tv_box" -> DeviceType.MediaDeviceTVBox
                    "devices.types.media_device.receiver" -> DeviceType.MediaDeviceReceiver
                    "devices.types.cooking" -> DeviceType.Cooking
                    "devices.types.cooking.coffee_maker" -> DeviceType.CoffeeMaker
                    "devices.types.cooking.kettle" -> DeviceType.Kettle
                    "devices.types.cooking.multicooker" -> DeviceType.Multicooker
                    "devices.types.openable" -> DeviceType.Openable
                    "devices.types.openable.curtain" -> DeviceType.OpenableCurtain
                    "devices.types.humidifier" -> DeviceType.Humidifier
                    "devices.types.purifier" -> DeviceType.Purifier
                    "devices.types.vacuum_cleaner" -> DeviceType.VacuumCleaner
                    "devices.types.washing_machine" -> DeviceType.WashingMachine
                    "devices.types.dishwasher" -> DeviceType.Dishwasher
                    "devices.types.iron" -> DeviceType.Iron
                    "devices.types.sensor" -> DeviceType.Sensor
                    "devices.types.sensor.motion" -> DeviceType.SensorMotion
                    "devices.types.sensor.door" -> DeviceType.SensorDoor
                    "devices.types.sensor.window" -> DeviceType.SensorWindow
                    "devices.types.sensor.water_leak" -> DeviceType.SensorWaterLeak
                    "devices.types.sensor.smoke" -> DeviceType.SensorSmoke
                    "devices.types.sensor.gas" -> DeviceType.SensorGas
                    "devices.types.sensor.vibration" -> DeviceType.SensorVibration
                    "devices.types.sensor.button" -> DeviceType.SensorButton
                    "devices.types.sensor.illumination" -> DeviceType.SensorIllumination
                    "devices.types.other" -> DeviceType.Other
                    else -> DeviceType.CustomDeviceType(value)
                }
            }
        }
    }
}

@Serializable
data class DeviceObject(
    override val id: String,
    override val name: String,
    override val aliases: List<String>,
    override val type: DeviceType,
    @SerialName("external_id") override val externalId: String,
    @SerialName("skill_id") override val skillId: String,
    @SerialName("household_id") val householdId: String,
    override val room: String?,
    override val groups: List<String>,
    override val capabilities: List<DeviceCapabilityObject>,
    override val properties: List<DevicePropertyObject>,
    @SerialName("quasar_info") val quasarInfo: QuasarInfo? = null
) : BaseDeviceObject

@Serializable
data class ScenarioObject(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("is_active") val isActive: Boolean
)

@Serializable
data class HouseholdObject(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String
)

//@Serializable(with = GroupCapabilityObjectSerializer::class)
@Serializable
data class GroupCapabilityObject(
    @SerialName("type") val type: String,
    @SerialName("retrievable") val retrievable: Boolean,
    @SerialName("parameters") val parameters: CapabilityParameterObject,
    @SerialName("state") val state: CapabilityStateObjectData?
)

@Serializable
data class DevicePropertyObject(
    override val type: String,
    @SerialName("reportable") val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: PropertyParameterObject,
    override val state: PropertyStateObject?,
    @SerialName("last_updated") override val lastUpdated: Float,
) : Property

@Serializable
data class QuasarInfo(
    @SerialName("device_id") val deviceId: String,
    @SerialName("platform") val platform: String
)

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html
 */

@Serializable
data class DeviceStateResponse(
    override val status: String,
    @SerialName("request_id") override val requestId: String,
    override val id: String,
    override val name: String,
    override val aliases: List<String>,
    override val type: DeviceType,
    @SerialName("state") val state: DeviceState,
    override val groups: List<String>,
    override val room: String?,
    @SerialName("external_id") override val externalId: String,
    @SerialName("skill_id") override val skillId: String,
    override val capabilities: List<CapabilityObject>,
    override val properties: List<PropertyObject>
) : BaseDeviceObject, Response

@Serializable
sealed class DeviceState {
    @Serializable
    @SerialName("online")
    data object Online : DeviceState()
    @Serializable
    @SerialName("offline")
    data object Offline : DeviceState()
}

sealed interface Capability{
    @SerialName("type")  val type: String
    @SerialName("retrievable") val retrievable: Boolean
    @SerialName("parameters") val parameters: CapabilityParameterObject
    @SerialName("state") val state: CapabilityStateObjectData?
    @SerialName("last_updated") val lastUpdated: Float
}

//@Serializable(with = DeviceCapabilityObjectSerializer::class)
@Serializable
data class DeviceCapabilityObject(
    override val type: String,
    val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    override val lastUpdated: Float
) : Capability

//@Serializable(with = CapabilityObjectSerializer::class)
@Serializable
data class CapabilityObject(
    override val type: String,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    @SerialName("last_updated") override val lastUpdated: Float
) : Capability

//object CapabilityObjectSerializer : KSerializer<CapabilityObject> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CapabilityObject") {
//    }
//
//    override fun serialize(encoder: Encoder, value: CapabilityObject) {
//    }
//
//    override fun deserialize(decoder: Decoder): CapabilityObject {
//        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
//        val jsonObject = input.decodeJsonElement().jsonObject
//        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing retrievable")
//        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Missing type")
//        val stateJson = jsonObject["state"]
//        val state: CapabilityStateObjectData?
//        when (stateJson) {
//            null -> state = null
//            is JsonNull -> state = null
//            else -> {
//                state = when (type) {
//                    "devices.capabilities.on_off" -> {
//                        val instance = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
//                        OnOffCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.color_setting" -> {
//                        val instance = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
//                        ColorSettingCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.video_stream" -> {
//                        val instance = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
//                        VideoStreamCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.mode" -> {
//                        val instance = input.json.decodeFromJsonElement<ModeCapabilityInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<ModeCapabilityMode>(stateJson.jsonObject["value"]!!)
//                        ModeCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.range" -> {
//                        val instance = input.json.decodeFromJsonElement<RangeCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<RangeCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
//                        RangeCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.toggle" -> {
//                        val instance = input.json.decodeFromJsonElement<ToggleCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<ToggleCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
//                        ToggleCapabilityStateObjectData(instance, value)
//                    }
//                    else -> throw SerializationException("Unknown CapabilityStateObjectData type: $type")
//                }
//            }
//        }
//        val lastUpdated = jsonObject["last_updated"]?.jsonPrimitive?.float ?: throw SerializationException("Missing lastUpdated")
//
//        val paramsJson = jsonObject["parameters"]?.jsonObject ?: throw SerializationException("Missing parameters")
//        val params = when (type) {
//            "devices.capabilities.color_setting" -> input.json.decodeFromJsonElement<ColorSettingCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.on_off" -> input.json.decodeFromJsonElement<OnOffCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.range" -> input.json.decodeFromJsonElement<RangeCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.mode" -> input.json.decodeFromJsonElement<ModeCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.toggle" -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.video_stream" -> input.json.decodeFromJsonElement<VideoStreamCapabilityParameterObject>(paramsJson)
//            else -> throw SerializationException("Unknown Capability type")
//        }
//
//        return CapabilityObject(retrievable = retrievable, type = type, parameters = params, state = state, lastUpdated = lastUpdated)
//    }
//}

@Serializable
sealed interface Property{
    @SerialName("type") val type: String
    @SerialName("retrievable") val retrievable: Boolean
    @SerialName("parameters") val parameters: PropertyParameterObject
    @SerialName("state") val state: PropertyStateObject?
    @SerialName("last_updated") val lastUpdated: Float
}

@Serializable
data class PropertyObject(
    override val type: String,
    override val retrievable: Boolean,
    override val parameters: PropertyParameterObject,
    override val state: PropertyStateObject?,
    @SerialName("last_updated") override val lastUpdated: Float
) : Property

//object GroupCapabilityObjectSerializer : KSerializer<GroupCapabilityObject> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GroupCapabilityObject") {
//        element("type", String.serializer().descriptor)
//        element("retrievable", Boolean.serializer().descriptor)
//        element("parameters", CapabilityParameterObject.serializer().descriptor)
//        element("state", CapabilityStateObjectData.serializer().descriptor)
//    }
//
//    @OptIn(ExperimentalSerializationApi::class)
//    override fun serialize(encoder: Encoder, value: GroupCapabilityObject) {
//        val compositeOutput = encoder.beginStructure(descriptor)
//        compositeOutput.encodeStringElement(descriptor, 0, value.type)
//        compositeOutput.encodeBooleanElement(descriptor, 1, value.retrievable)
//        compositeOutput.encodeSerializableElement(descriptor, 2, CapabilityParameterObject.serializer(), value.parameters)
//        compositeOutput.encodeNullableSerializableElement(descriptor, 3, CapabilityStateObjectData.serializer(), value.state)
//        compositeOutput.endStructure(descriptor)
//    }
//
//    override fun deserialize(decoder: Decoder): GroupCapabilityObject {
//        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
//        val jsonObject = input.decodeJsonElement().jsonObject
//
//        val type = jsonObject["type"]?.jsonPrimitive?.content ?: throw SerializationException("Missing type")
//        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing retrievable")
//
//        val paramsJson = jsonObject["parameters"]?.jsonObject ?: throw SerializationException("Missing parameters")
//        val params = when (type) {
//            "devices.capabilities.color_setting" -> input.json.decodeFromJsonElement<ColorSettingCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.on_off" -> input.json.decodeFromJsonElement<OnOffCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.range" -> input.json.decodeFromJsonElement<RangeCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.mode" -> input.json.decodeFromJsonElement<ModeCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.toggle" -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.video_stream" -> input.json.decodeFromJsonElement<VideoStreamCapabilityParameterObject>(paramsJson)
//            else -> throw SerializationException("Unknown Capability type")
//        }
//
//        val stateJson = jsonObject["state"]?.jsonObject ?: throw SerializationException("Missing state")
//        val state = when (type) {
//                "devices.capabilities.color_setting" -> input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectData>(stateJson)
//                "devices.capabilities.on_off" -> input.json.decodeFromJsonElement<OnOffCapabilityStateObjectData>(stateJson)
//                "devices.capabilities.range" -> input.json.decodeFromJsonElement<RangeCapabilityStateObjectData>(stateJson)
//                "devices.capabilities.mode" -> input.json.decodeFromJsonElement<ModeCapabilityStateObjectData>(stateJson)
//                "devices.capabilities.toggle" -> input.json.decodeFromJsonElement<ToggleCapabilityStateObjectData>(stateJson)
//                "devices.capabilities.video_stream" -> input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectData>(stateJson)
//                else -> throw SerializationException("Unknown Capability type")
//            }
//
//        return GroupCapabilityObject(type = type, retrievable = retrievable, parameters = params, state = state)
//    }
//}
//
//object DeviceCapabilityObjectSerializer : KSerializer<DeviceCapabilityObject> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DeviceCapabilityObject") {
//    }
//
//    override fun serialize(encoder: Encoder, value: DeviceCapabilityObject) {
//    }
//
//    override fun deserialize(decoder: Decoder): DeviceCapabilityObject {
//        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
//        val jsonObject = input.decodeJsonElement().jsonObject
//
//        val reportable = jsonObject["reportable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing reportable")
//        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing retrievable")
//        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Missing type")
//        val stateJson = jsonObject["state"]
//        val state: CapabilityStateObjectData?
//        when (stateJson) {
//            null -> state = null
//            is JsonNull -> state = null
//            else -> {
//                state = when (type) {
//                    "devices.capabilities.on_off" -> {
//                        val instance = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
//                        OnOffCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.color_setting" -> {
//                        val instance = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
//                        ColorSettingCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.video_stream" -> {
//                        val instance = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
//                        VideoStreamCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.mode" -> {
//                        val instance = input.json.decodeFromJsonElement<ModeCapabilityInstance>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<ModeCapabilityMode>(stateJson.jsonObject["value"]!!)
//                        ModeCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.range" -> {
//                        val instance = input.json.decodeFromJsonElement<RangeCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<RangeCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
//                        RangeCapabilityStateObjectData(instance, value)
//                    }
//                    "devices.capabilities.toggle" -> {
//                        val instance = input.json.decodeFromJsonElement<ToggleCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
//                        val value = input.json.decodeFromJsonElement<ToggleCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
//                        ToggleCapabilityStateObjectData(instance, value)
//                    }
//                    else -> throw SerializationException("Unknown CapabilityStateObjectData type: $type")
//                }
//            }
//        }
//        val lastUpdated = jsonObject["last_updated"]?.jsonPrimitive?.float ?: throw SerializationException("Missing lastUpdated")
//
//        val paramsJson = jsonObject["parameters"]?.jsonObject ?: throw SerializationException("Missing parameters")
//        val params = when (type) {
//            "devices.capabilities.color_setting" -> input.json.decodeFromJsonElement<ColorSettingCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.on_off" -> input.json.decodeFromJsonElement<OnOffCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.range" -> input.json.decodeFromJsonElement<RangeCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.mode" -> input.json.decodeFromJsonElement<ModeCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.toggle" -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObject>(paramsJson)
//            "devices.capabilities.video_stream" -> input.json.decodeFromJsonElement<VideoStreamCapabilityParameterObject>(paramsJson)
//            else -> throw SerializationException("Unknown Capability type")
//        }
//
//        return DeviceCapabilityObject(reportable = reportable, retrievable = retrievable, type = type, parameters = params, state = state, lastUpdated = lastUpdated)
//    }
//}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Polymorphic
sealed class CapabilityParameterObject

@OptIn(ExperimentalSerializationApi::class)
object CapabilityParameterObjectSerializer : KSerializer<CapabilityParameterObject> {
    override val descriptor: SerialDescriptor = PolymorphicSerializer(CapabilityParameterObject::class).descriptor

    override fun serialize(encoder: Encoder, value: CapabilityParameterObject) {
        val polymorphicSerializer = PolymorphicSerializer(CapabilityParameterObject::class)
        polymorphicSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): CapabilityParameterObject {
        val polymorphicSerializer = PolymorphicSerializer(CapabilityParameterObject::class)
        return polymorphicSerializer.deserialize(decoder)
    }
}

@Serializable
data class ColorSettingCapabilityParameterObject(
    @SerialName("color_model") val colorModel: ColorModel? = null,
    @SerialName("temperature_k") val temperatureK: TemperatureK? = null,
    @SerialName("color_scene") val colorScene: ColorScene? = null
): CapabilityParameterObject() {
    init {
        require(colorModel != null || temperatureK != null || colorScene != null) {
            "one of color_model, temperature_k or color_scene must have a value"
        }
    }
}

@Serializable
data class OnOffCapabilityParameterObject(
    val split: Boolean
): CapabilityParameterObject()

@Serializable
data class ModeCapabilityParameterObject(
    val instance: ModeCapabilityInstance,
    val modes: List<ModeObject>
): CapabilityParameterObject()

@Serializable
data class RangeCapabilityParameterObject(
    val instance: RangeCapabilityParameterObjectFunction,
    var unit: MeasurementUnit? = null,
    @SerialName("random_access") val randomAccess: Boolean,
    val range: Range? = null,
    val looped: Boolean
): CapabilityParameterObject() {
    init {
        unit = when (instance) {
            RangeCapabilityParameterObjectFunction.Brightness,
            RangeCapabilityParameterObjectFunction.Humidity,
            RangeCapabilityParameterObjectFunction.Open -> MeasurementUnit.Percent
            else -> {
                //RangeCapabilityInstanceOld.TEMPERATURE
                MeasurementUnit.TemperatureCelsius
            }
        }
    }
}


@Serializable
data class ToggleCapabilityParameterObject(
    val instance: ToggleCapabilityParameterObjectFunction
): CapabilityParameterObject()


@Serializable
data class VideoStreamCapabilityParameterObject(
    val protocols: List<VideoStreamCapabilityParameterObjectStreamProtocol>
): CapabilityParameterObject()

/**
 * make KSerializable
 */

@Serializable
sealed class ColorModel {
    @Serializable
    @SerialName("rgb")
    data object RGB : ColorModel()
    @Serializable
    @SerialName("hsv")
    data object HSV : ColorModel()
}

@Serializable
data class TemperatureK(
    val min: Int,
    val max: Int
)

@Serializable
data class ColorScene(
    val scenes: List<Scene>
)

@Serializable
data class Scene(
    @SerialName("id") val id: SceneObject
)

@Serializable
@Polymorphic
sealed class SceneObject {
    @Serializable
    @SerialName("alarm")
    data object Alarm : SceneObject()

    @Serializable
    @SerialName("alice")
    data object Alice : SceneObject()

    @Serializable
    @SerialName("candle")
    data object Candle : SceneObject()

    @Serializable
    @SerialName("dinner")
    data object Dinner : SceneObject()

    @Serializable
    @SerialName("fantasy")
    data object Fantasy : SceneObject()

    @Serializable
    @SerialName("garland")
    data object Garland : SceneObject()

    @Serializable
    @SerialName("jungle")
    data object Jungle : SceneObject()

    @Serializable
    @SerialName("movie")
    data object Movie : SceneObject()

    @Serializable
    @SerialName("neon")
    data object Neon : SceneObject()

    @Serializable
    @SerialName("night")
    data object Night : SceneObject()

    @Serializable
    @SerialName("ocean")
    data object Ocean : SceneObject()

    @Serializable
    @SerialName("party")
    data object Party : SceneObject()

    @Serializable
    @SerialName("reading")
    data object Reading : SceneObject()

    @Serializable
    @SerialName("rest")
    data object Rest : SceneObject()

    @Serializable
    @SerialName("romance")
    data object Romance : SceneObject()

    @Serializable
    @SerialName("siren")
    data object Siren : SceneObject()

    @Serializable
    @SerialName("sunrise")
    data object Sunrise : SceneObject()

    @Serializable
    @SerialName("sunset")
    data object Sunset : SceneObject()
}

@Serializable
data class ModeObject(val value: ModeCapabilityMode)

@Serializable
sealed class ModeCapabilityMode : CapabilityStateObjectValue() {
    @Serializable
    @SerialName("auto")
    data object Auto : ModeCapabilityMode()

    @Serializable
    @SerialName("eco")
    data object Eco : ModeCapabilityMode()

    @Serializable
    @SerialName("smart")
    data object Smart : ModeCapabilityMode()

    @Serializable
    @SerialName("turbo")
    data object Turbo : ModeCapabilityMode()

    @Serializable
    @SerialName("cool")
    data object Cool : ModeCapabilityMode()

    @Serializable
    @SerialName("dry")
    data object Dry : ModeCapabilityMode()

    @Serializable
    @SerialName("fan_only")
    data object FanOnly : ModeCapabilityMode()

    @Serializable
    @SerialName("heat")
    data object Heat : ModeCapabilityMode()

    @Serializable
    @SerialName("preheat")
    data object Preheat : ModeCapabilityMode()

    @Serializable
    @SerialName("high")
    data object High : ModeCapabilityMode()

    @Serializable
    @SerialName("low")
    data object Low : ModeCapabilityMode()

    @Serializable
    @SerialName("medium")
    data object Medium : ModeCapabilityMode()

    @Serializable
    @SerialName("max")
    data object Max : ModeCapabilityMode()

    @Serializable
    @SerialName("min")
    data object Min : ModeCapabilityMode()

    @Serializable
    @SerialName("fast")
    data object Fast : ModeCapabilityMode()

    @Serializable
    @SerialName("slow")
    data object Slow : ModeCapabilityMode()

    @Serializable
    @SerialName("express")
    data object Express : ModeCapabilityMode()

    @Serializable
    @SerialName("normal")
    data object Normal : ModeCapabilityMode()

    @Serializable
    @SerialName("quiet")
    data object Quiet : ModeCapabilityMode()

    @Serializable
    @SerialName("horizontal")
    data object Horizontal : ModeCapabilityMode()

    @Serializable
    @SerialName("stationary")
    data object Stationary : ModeCapabilityMode()

    @Serializable
    @SerialName("vertical")
    data object Vertical : ModeCapabilityMode()

    @Serializable
    @SerialName("one")
    data object One : ModeCapabilityMode()

    @Serializable
    @SerialName("two")
    data object Two : ModeCapabilityMode()

    @Serializable
    @SerialName("three")
    data object Three : ModeCapabilityMode()

    @Serializable
    @SerialName("four")
    data object Four : ModeCapabilityMode()

    @Serializable
    @SerialName("five")
    data object Five : ModeCapabilityMode()

    @Serializable
    @SerialName("six")
    data object Six : ModeCapabilityMode()

    @Serializable
    @SerialName("seven")
    data object Seven : ModeCapabilityMode()

    @Serializable
    @SerialName("eight")
    data object Eight : ModeCapabilityMode()

    @Serializable
    @SerialName("nine")
    data object Nine : ModeCapabilityMode()

    @Serializable
    @SerialName("ten")
    data object Ten : ModeCapabilityMode()

    @Serializable
    @SerialName("americano")
    data object Americano : ModeCapabilityMode()

    @Serializable
    @SerialName("cappuccino")
    data object Cappuccino : ModeCapabilityMode()

    @Serializable
    @SerialName("double")
    data object Double : ModeCapabilityMode()

    @Serializable
    @SerialName("espresso")
    data object Espresso : ModeCapabilityMode()

    @Serializable
    @SerialName("double_espresso")
    data object DoubleEspresso : ModeCapabilityMode()

    @Serializable
    @SerialName("latte")
    data object Latte : ModeCapabilityMode()

    @Serializable
    @SerialName("black_tea")
    data object BlackTea : ModeCapabilityMode()

    @Serializable
    @SerialName("flower_tea")
    data object FlowerTea : ModeCapabilityMode()

    @Serializable
    @SerialName("green_tea")
    data object GreenTea : ModeCapabilityMode()

    @Serializable
    @SerialName("herbal_tea")
    data object HerbalTea : ModeCapabilityMode()

    @Serializable
    @SerialName("oolong_tea")
    data object OolongTea : ModeCapabilityMode()

    @Serializable
    @SerialName("puerh_tea")
    data object PuerhTea : ModeCapabilityMode()

    @Serializable
    @SerialName("red_tea")
    data object RedTea : ModeCapabilityMode()

    @Serializable
    @SerialName("white_tea")
    data object WhiteTea : ModeCapabilityMode()

    @Serializable
    @SerialName("glass")
    data object Glass : ModeCapabilityMode()

    @Serializable
    @SerialName("intensive")
    data object Intensive : ModeCapabilityMode()

    @Serializable
    @SerialName("pre_rinse")
    data object PreRinse : ModeCapabilityMode()

    @Serializable
    @SerialName("aspic")
    data object Aspic : ModeCapabilityMode()

    @Serializable
    @SerialName("baby_food")
    data object BabyFood : ModeCapabilityMode()

    @Serializable
    @SerialName("baking")
    data object Baking : ModeCapabilityMode()

    @Serializable
    @SerialName("bread")
    data object Bread : ModeCapabilityMode()

    @Serializable
    @SerialName("boiling")
    data object Boiling : ModeCapabilityMode()

    @Serializable
    @SerialName("cereals")
    data object Cereals : ModeCapabilityMode()

    @Serializable
    @SerialName("cheesecake")
    data object Cheesecake : ModeCapabilityMode()

    @Serializable
    @SerialName("deep_fryer")
    data object DeepFryer : ModeCapabilityMode()

    @Serializable
    @SerialName("dessert")
    data object Dessert : ModeCapabilityMode()

    @Serializable
    @SerialName("fowl")
    data object Fowl : ModeCapabilityMode()

    @Serializable
    @SerialName("frying")
    data object Frying : ModeCapabilityMode()

    @Serializable
    @SerialName("macaroni")
    data object Macaroni : ModeCapabilityMode()

    @Serializable
    @SerialName("milk_porridge")
    data object MilkPorridge : ModeCapabilityMode()

    @Serializable
    @SerialName("multicooker")
    data object Multicooker : ModeCapabilityMode()

    @Serializable
    @SerialName("pasta")
    data object Pasta : ModeCapabilityMode()

    @Serializable
    @SerialName("pilaf")
    data object Pilaf : ModeCapabilityMode()

    @Serializable
    @SerialName("pizza")
    data object Pizza : ModeCapabilityMode()

    @Serializable
    @SerialName("sauce")
    data object Sauce : ModeCapabilityMode()

    @Serializable
    @SerialName("slow_cook")
    data object SlowCook : ModeCapabilityMode()

    @Serializable
    @SerialName("soup")
    data object Soup : ModeCapabilityMode()

    @Serializable
    @SerialName("steam")
    data object Steam : ModeCapabilityMode()

    @Serializable
    @SerialName("stewing")
    data object Stewing : ModeCapabilityMode()

    @Serializable
    @SerialName("vacuum")
    data object Vacuum : ModeCapabilityMode()

    @Serializable
    @SerialName("yogurt")
    data object Yogurt : ModeCapabilityMode()
}

@Serializable
@Polymorphic
sealed class ModeCapabilityInstance : CapabilityStateObjectInstance {
    @Serializable
    @SerialName("cleanup_mode")
    data object CleanupMode : ModeCapabilityInstance()

    @Serializable
    @SerialName("coffee_mode")
    data object CoffeeMode : ModeCapabilityInstance()

    @Serializable
    @SerialName("dishwashing")
    data object Dishwashing : ModeCapabilityInstance()

    @Serializable
    @SerialName("fan_speed")
    data object FanSpeed : ModeCapabilityInstance()

    @Serializable
    @SerialName("heat")
    data object Heat : ModeCapabilityInstance()

    @Serializable
    @SerialName("input_source")
    data object InputSource : ModeCapabilityInstance()

    @Serializable
    @SerialName("program")
    data object Program : ModeCapabilityInstance()

    @Serializable
    @SerialName("swing")
    data object Swing : ModeCapabilityInstance()

    @Serializable
    @SerialName("tea_mode")
    data object TeaMode : ModeCapabilityInstance()

    @Serializable
    @SerialName("thermostat")
    data object Thermostat : ModeCapabilityInstance()

    @Serializable
    @SerialName("work_speed")
    data object WorkSpeed : ModeCapabilityInstance()
}

@Serializable
@Polymorphic
sealed class RangeCapabilityParameterObjectFunction : CapabilityStateObjectInstance {
    @Serializable
    @SerialName("brightness")
    data object Brightness : RangeCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("channel")
    data object Channel : RangeCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("humidity")
    data object Humidity : RangeCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("open")
    data object Open : RangeCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("temperature")
    data object Temperature : RangeCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("volume")
    data object Volume : RangeCapabilityParameterObjectFunction()
}

@Serializable
data class Range(
    val min: Float,
    val max: Float,
    val precision: Float
) {
    override fun toString() = "[$min, $max]"
}

@Serializable
sealed class ToggleCapabilityParameterObjectFunction : CapabilityStateObjectInstance {
    @Serializable
    @SerialName("backlight")
    data object Backlight : ToggleCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("controls_locked")
    data object ControlsLocked : ToggleCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("ionization")
    data object Ionization : ToggleCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("keep_warm")
    data object KeepWarm : ToggleCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("mute")
    data object Mute : ToggleCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("oscillation")
    data object Oscillation : ToggleCapabilityParameterObjectFunction()

    @Serializable
    @SerialName("pause")
    data object Pause : ToggleCapabilityParameterObjectFunction()
}

@Serializable
sealed class VideoStreamCapabilityParameterObjectStreamProtocol {
    @Serializable
    @SerialName("hls")
    data object HLS: VideoStreamCapabilityParameterObjectStreamProtocol()
    @Serializable
    @SerialName("rtmp")
    data object RTMP: VideoStreamCapabilityParameterObjectStreamProtocol()
}

@Serializable
@Polymorphic
sealed interface CapabilityState {
    val instance: CapabilityStateObjectInstance
}

//@Serializable(CapabilityStateObjectDataSerializer::class)
@Serializable
sealed class CapabilityStateObjectData: CapabilityState{
    abstract override val instance: CapabilityStateObjectInstance
    abstract val value: CapabilityStateObjectValue
}

//object CapabilityStateObjectDataSerializer : KSerializer<CapabilityStateObjectData> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CapabilityStateObjectData") {
//        element<String>("type")
//        element<JsonElement>("value")
//    }
//
//    override fun serialize(encoder: Encoder, value: CapabilityStateObjectData) {
//        // Пока оставляем пустым
//    }
//
//    override fun deserialize(decoder: Decoder): CapabilityStateObjectData {
//        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
//        val jsonObject = input.decodeJsonElement().jsonObject
//
//        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Missing type")
//        val valueJson = jsonObject["value"] ?: throw SerializationException("Missing value")
//
//        return when (type) {
//            "devices.capabilities.on_off" -> {
//                val instance = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectInstance>(jsonObject["instance"]!!)
//                val value = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectValue>(valueJson)
//                OnOffCapabilityStateObjectData(instance, value)
//            }
//            "devices.capabilities.color_setting" -> {
//                val instance = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectInstance>(jsonObject["instance"]!!)
//                val value = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectValue>(valueJson)
//                ColorSettingCapabilityStateObjectData(instance, value)
//            }
//            "devices.capabilities.video_stream" -> {
//                val instance = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectInstance>(jsonObject["instance"]!!)
//                val value = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectDataValue>(valueJson)
//                VideoStreamCapabilityStateObjectData(instance, value)
//            }
//            "devices.capabilities.mode" -> {
//                val instance = input.json.decodeFromJsonElement<ModeCapabilityInstance>(jsonObject["instance"]!!)
//                val value = input.json.decodeFromJsonElement<ModeCapabilityMode>(valueJson)
//                ModeCapabilityStateObjectData(instance, value)
//            }
//            "devices.capabilities.range" -> {
//                val instance = input.json.decodeFromJsonElement<RangeCapabilityParameterObjectFunction>(jsonObject["instance"]!!)
//                val value = input.json.decodeFromJsonElement<RangeCapabilityStateObjectDataValue>(valueJson)
//                RangeCapabilityStateObjectData(instance, value)
//            }
//            "devices.capabilities.toggle" -> {
//                val instance = input.json.decodeFromJsonElement<ToggleCapabilityParameterObjectFunction>(jsonObject["instance"]!!)
//                val value = input.json.decodeFromJsonElement<ToggleCapabilityStateObjectDataValue>(valueJson)
//                ToggleCapabilityStateObjectData(instance, value)
//            }
//            else -> throw SerializationException("Unknown CapabilityStateObjectData type")
//        }
//    }
//}

@Serializable
sealed interface CapabilityStateObjectActionResult : CapabilityState{
    abstract override val instance: CapabilityStateObjectInstance
    @SerialName("action_result") val actionResult: ActionResult
}

@Serializable
data class ActionResult(
    val status: Status,
    @SerialName("error_code") val errorCode: ErrorCode? = null,
    @SerialName("error_message") val errorMessage: String? = null
)

@Serializable
sealed class ErrorCode {
    @Serializable
    @SerialName("DOOR_OPEN")
    data object DoorOpen : ErrorCode()

    @Serializable
    @SerialName("LID_OPEN")
    data object LidOpen : ErrorCode()

    @Serializable
    @SerialName("REMOTE_CONTROL_DISABLED")
    data object RemoteControlDisabled : ErrorCode()

    @Serializable
    @SerialName("NOT_ENOUGH_WATER")
    data object NotEnoughWater : ErrorCode()

    @Serializable
    @SerialName("LOW_CHARGE_LEVEL")
    data object LowChargeLevel : ErrorCode()

    @Serializable
    @SerialName("CONTAINER_FULL")
    data object ContainerFull : ErrorCode()

    @Serializable
    @SerialName("CONTAINER_EMPTY")
    data object ContainerEmpty : ErrorCode()

    @Serializable
    @SerialName("DRIP_TRAY_FULL")
    data object DripTrayFull : ErrorCode()

    @Serializable
    @SerialName("DEVICE_STUCK")
    data object DeviceStuck : ErrorCode()

    @Serializable
    @SerialName("DEVICE_OFF")
    data object DeviceOff : ErrorCode()

    @Serializable
    @SerialName("FIRMWARE_OUT_OF_DATE")
    data object FirmwareOutOfDate : ErrorCode()

    @Serializable
    @SerialName("NOT_ENOUGH_DETERGENT")
    data object NotEnoughDetergent : ErrorCode()

    @Serializable
    @SerialName("HUMAN_INVOLVEMENT_NEEDED")
    data object HumanInvolvementNeeded : ErrorCode()

    @Serializable
    @SerialName("DEVICE_UNREACHABLE")
    data object DeviceUnreachable : ErrorCode()

    @Serializable
    @SerialName("DEVICE_BUSY")
    data object DeviceBusy : ErrorCode()

    @Serializable
    @SerialName("INTERNAL_ERROR")
    data object InternalError : ErrorCode()

    @Serializable
    @SerialName("INVALID_ACTION")
    data object InvalidAction : ErrorCode()

    @Serializable
    @SerialName("INVALID_VALUE")
    data object InvalidValue : ErrorCode()

    @Serializable
    @SerialName("NOT_SUPPORTED_IN_CURRENT_MODE")
    data object NotSupportedInCurrentMode : ErrorCode()

    @Serializable
    @SerialName("ACCOUNT_LINKING_ERROR")
    data object AccountLinkingError : ErrorCode()

    @Serializable
    @SerialName("DEVICE_NOT_FOUND")
    data object DeviceNotFound : ErrorCode()
}

@Serializable
sealed class Status {
    @Serializable
    @SerialName("DONE")
    data object Done: Status()
    @Serializable
    @SerialName("ERROR")
    data object Error: Status()
    @Serializable
    data class CustomStatus(val value: String): Status()
}

@Serializable
@Polymorphic
sealed interface CapabilityStateObjectInstance

@Serializable
@Polymorphic
sealed class CapabilityStateObjectValue

@Serializable
data class OnOffCapabilityStateObjectData(
    override val instance: OnOffCapabilityStateObjectInstance,
    override val value: OnOffCapabilityStateObjectValue
): CapabilityStateObjectData()

@Serializable
sealed class OnOffCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @Serializable
    @SerialName("on")
    data object On: OnOffCapabilityStateObjectInstance()
}

//@Serializable(with = OnOffCapabilityStateObjectValueSerializer::class)
@Serializable
data class OnOffCapabilityStateObjectValue(val value: Boolean) : CapabilityStateObjectValue()

//object OnOffCapabilityStateObjectValueSerializer : KSerializer<OnOffCapabilityStateObjectValue> {
//    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OnOffCapabilityStateObjectValue", PrimitiveKind.BOOLEAN)
//
//    override fun serialize(encoder: Encoder, value: OnOffCapabilityStateObjectValue) {
//        encoder.encodeBoolean(value.value)
//    }
//
//    override fun deserialize(decoder: Decoder): OnOffCapabilityStateObjectValue {
//        return when (decoder) {
//            is JsonDecoder -> {
//                val element = decoder.decodeJsonElement()
//                when (element) {
//                    is JsonPrimitive -> OnOffCapabilityStateObjectValue(element.boolean)
//                    is JsonObject -> OnOffCapabilityStateObjectValue(element["value"]?.jsonPrimitive?.booleanOrNull ?: throw SerializationException("Expected 'value' field"))
//                    else -> throw SerializationException("Expected JsonObject or JsonPrimitive")
//                }
//            }
//            else -> OnOffCapabilityStateObjectValue(decoder.decodeBoolean())
//        }
//    }
//}

@Serializable
data class ColorSettingCapabilityStateObjectData(
    override val instance: ColorSettingCapabilityStateObjectInstance,
    override val value: ColorSettingCapabilityStateObjectValue
): CapabilityStateObjectData()

@Serializable
sealed class ColorSettingCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @Serializable
    @SerialName("base")
    data object Base : ColorSettingCapabilityStateObjectInstance()
    @Serializable
    @SerialName("rgb")
    data object RGB : ColorSettingCapabilityStateObjectInstance()
    @Serializable
    @SerialName("hsv")
    data object HSV : ColorSettingCapabilityStateObjectInstance()
    @Serializable
    @SerialName("temperature_k")
    data object TemperatureK : ColorSettingCapabilityStateObjectInstance()
    @Serializable
    @SerialName("scene")
    data object Scene : ColorSettingCapabilityStateObjectInstance()
}

//@Serializable(with = ColorSettingCapabilityStateObjectValueSerializer::class)
@Serializable
@Polymorphic
sealed class ColorSettingCapabilityStateObjectValue: CapabilityStateObjectValue(){
    abstract val value: Any
}

//object ColorSettingCapabilityStateObjectValueSerializer : JsonContentPolymorphicSerializer<ColorSettingCapabilityStateObjectValue>(ColorSettingCapabilityStateObjectValue::class) {
//    override fun selectDeserializer(element: JsonElement) = when (element) {
//        is JsonObject -> when {
//            "scene" in element -> ColorSettingCapabilityStateObjectValueObjectScene.serializer()
//            "h" in element && "s" in element && "v" in element -> ColorSettingCapabilityStateObjectValueObjectHSV.serializer()
//            "value" in element -> ColorSettingCapabilityStateObjectValueInteger.serializer()
//            else -> throw SerializationException("Unknown ColorSettingCapabilityStateObjectValue type")
//        }
//        is JsonPrimitive -> {
//            if (element.content.toIntOrNull() != null) {
//                ColorSettingCapabilityStateObjectValueInteger.serializer()
//            } else {
//                throw SerializationException("Expected integer value for ColorSettingCapabilityStateObjectValueInteger")
//            }
//        }
//        else -> throw SerializationException("Unknown ColorSettingCapabilityStateObjectValue type")
//    }
//}

//@Serializable(with = ColorSettingCapabilityStateObjectValueIntegerSerializer::class)
@Serializable
data class ColorSettingCapabilityStateObjectValueInteger(@SerialName("value") override val value: Int) : ColorSettingCapabilityStateObjectValue()

@Serializable
data class ColorSettingCapabilityStateObjectValueObjectScene(override val value: SceneObject)
    : ColorSettingCapabilityStateObjectValue()

@Serializable
data class ColorSettingCapabilityStateObjectValueObjectHSV(override val value: HSVObject)
    : ColorSettingCapabilityStateObjectValue()

@Serializable
data class HSVObject(val h: Int, val s: Int, val v: Int)

@Serializable
data class VideoStreamCapabilityStateObjectData(
    override val instance: VideoStreamCapabilityStateObjectInstance,
    override val value: VideoStreamCapabilityStateObjectDataValue
): CapabilityStateObjectData()

@Serializable
sealed class VideoStreamCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @Serializable
    @SerialName("get_stream")
    data object GetStream : VideoStreamCapabilityStateObjectInstance()
}

@Serializable
data class VideoStreamCapabilityStateObjectDataValue(
    val protocols: List<VideoStreamCapabilityParameterObjectStreamProtocol>
) : CapabilityStateObjectValue()

@Serializable
data class VideoStreamCapabilityStateObjectActionResultValue(
    val protocol: VideoStreamCapabilityParameterObjectStreamProtocol,
    @SerialName("stream_url") val streamUrl: String
) : CapabilityStateObjectValue()

@Serializable
data class VideoStreamCapabilityStateObjectActionResult(
    override val instance: VideoStreamCapabilityStateObjectInstance,
    val value: VideoStreamCapabilityStateObjectDataValue,
    override val actionResult: ActionResult
): CapabilityStateObjectActionResult

@Serializable
data class ModeCapabilityStateObjectData(
    override val instance: ModeCapabilityInstance,
    override val value: ModeCapabilityMode
): CapabilityStateObjectData()

@Serializable
data class ModeCapabilityStateObjectActionResult(
    override val instance: ModeCapabilityInstance,
    override val actionResult: ActionResult
): CapabilityStateObjectActionResult

//relative не реализуем (для RangeCapabilityStateObjectData в request)
@Serializable
data class RangeCapabilityStateObjectData(
    override val instance: RangeCapabilityParameterObjectFunction,
    override val value: RangeCapabilityStateObjectDataValue
): CapabilityStateObjectData()

@Serializable
data class RangeCapabilityStateObjectDataValue(
    val value: Float,
) : CapabilityStateObjectValue()

@Serializable
data class RangeCapabilityStateObjectActionResult(
    override val instance: RangeCapabilityParameterObjectFunction,
    override val actionResult: ActionResult
): CapabilityStateObjectActionResult

@Serializable
data class ToggleCapabilityStateObjectData(
    override val instance: ToggleCapabilityParameterObjectFunction,
    override val value: ToggleCapabilityStateObjectDataValue
): CapabilityStateObjectData()

@Serializable
data class ToggleCapabilityStateObjectDataValue(
    val value: Boolean
) : CapabilityStateObjectValue()

@Serializable
data class ToggleCapabilityStateObjectActionResult(
    override val instance: ToggleCapabilityParameterObjectFunction,
    override val actionResult: ActionResult
): CapabilityStateObjectActionResult

//@Serializable(with = PropertyParameterObjectSerializer::class)
@Serializable
sealed class PropertyParameterObject {
    abstract val instance: PropertyFunction
}

object PropertyParameterObjectSerializer : KSerializer<PropertyParameterObject> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PropertyParameterObject")

    override fun deserialize(decoder: Decoder): PropertyParameterObject {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonElement = jsonDecoder.decodeJsonElement()
        val instance = jsonElement.jsonObject["instance"]?.jsonPrimitive?.content
        return when (instance) {
            "float" -> jsonDecoder.json.decodeFromJsonElement(FloatPropertyParameterObject.serializer(), jsonElement)
            else -> jsonDecoder.json.decodeFromJsonElement(EventPropertyParameterObject.serializer(), jsonElement)
        }
    }

    override fun serialize(encoder: Encoder, value: PropertyParameterObject) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("Expected JsonEncoder")
        when (value) {
            is FloatPropertyParameterObject -> jsonEncoder.encodeSerializableValue(FloatPropertyParameterObject.serializer(), value)
            is EventPropertyParameterObject -> jsonEncoder.encodeSerializableValue(EventPropertyParameterObject.serializer(), value)
        }
    }
}

@Serializable
@SerialName("float")
data class FloatPropertyParameterObject(
    override val instance: FloatFunctions,
    val unit: MeasurementUnit
) : PropertyParameterObject()

@Serializable
sealed class MeasurementUnit{

    @Serializable
    @SerialName("unit.temperature.celsius")
    data object TemperatureCelsius : MeasurementUnit()

    @Serializable
    @SerialName("unit.ampere")
    data object Ampere : MeasurementUnit()

    @Serializable
    @SerialName("unit.cubic_meter")
    data object CubicMeter : MeasurementUnit()

    @Serializable
    @SerialName("unit.gigacalorie")
    data object Gigacalorie : MeasurementUnit()

    @Serializable
    @SerialName("unit.kilowatt_hour")
    data object KilowattHour : MeasurementUnit()

    @Serializable
    @SerialName("unit.illumination.lux")
    data object Lux : MeasurementUnit()

    @Serializable
    @SerialName("unit.density.mcg_m3")
    data object McgM3 : MeasurementUnit()

    @Serializable
    @SerialName("unit.percent")
    data object Percent : MeasurementUnit()

    @Serializable
    @SerialName("unit.ppm")
    data object Ppm : MeasurementUnit()

    @Serializable
    @SerialName("unit.volt")
    data object Volt : MeasurementUnit()

    @Serializable
    @SerialName("unit.watt")
    data object Watt : MeasurementUnit()

}

@Serializable
@SerialName("event")
data class EventPropertyParameterObject(
    override val instance: EventFunctions,
    val events: List<EventObject>
) : PropertyParameterObject()

@Serializable
@Polymorphic
sealed interface PropertyValue

//@Serializable(with = EventObjectValueSerializer::class)
@Serializable
sealed interface EventObjectValue : PropertyValue {
    @Serializable
    @SerialName("tilt")
    data object Tilt : EventObjectValue

    @Serializable
    @SerialName("fall")
    data object Fall : EventObjectValue

    @Serializable
    @SerialName("vibration")
    data object Vibration : EventObjectValue

    @Serializable
    @SerialName("opened")
    data object Opened : EventObjectValue

    @Serializable
    @SerialName("closed")
    data object Closed : EventObjectValue

    @Serializable
    @SerialName("click")
    data object Click : EventObjectValue

    @Serializable
    @SerialName("double_click")
    data object DoubleClick : EventObjectValue

    @Serializable
    @SerialName("long_press")
    data object LongPress : EventObjectValue

    @Serializable
    @SerialName("detected")
    data object Detected : EventObjectValue

    @Serializable
    @SerialName("not_detected")
    data object NotDetected : EventObjectValue

    @Serializable
    @SerialName("high")
    data object High : EventObjectValue

    @Serializable
    @SerialName("low")
    data object Low : EventObjectValue

    @Serializable
    @SerialName("normal")
    data object Normal : EventObjectValue

    @Serializable
    @SerialName("empty")
    data object Empty : EventObjectValue

    @Serializable
    @SerialName("dry")
    data object Dry : EventObjectValue

    @Serializable
    @SerialName("leak")
    data object Leak : EventObjectValue
}

//object EventObjectValueSerializer: KSerializer<EventObjectValue> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("EventObjectValue") {
//        element<String>("value")
//    }
//
//    override fun deserialize(decoder: Decoder): EventObjectValue {
//        return when (val value = decoder.decodeString()) {
//            "tilt", "fall", "vibration" -> VibrationEventObjectValue.valueOf(value.toUpperCase())
//            "opened", "closed" -> OpenInstanceObjectValue.valueOf(value.toUpperCase())
//            "click", "double_click", "long_press" -> ButtonEventObjectValue.valueOf(value.toUpperCase().replace("_", ""))
//            "detected", "not_detected" -> when (decoder.decodeString()) {
//                "motion" -> MotionEventObjectValue.valueOf(value.toUpperCase())
//                "smoke" -> SmokeEventObjectValue.valueOf(value.toUpperCase())
//                "gas" -> GasEventObjectValue.valueOf(value.toUpperCase())
//                else -> throw SerializationException("Unknown EventObjectValue: $value")
//            }
//            "low", "normal", "high" -> when (decoder.decodeString()) {
//                "battery_level" -> BatteryLevelEventObjectValue.valueOf(value.toUpperCase())
//                "smoke" -> SmokeEventObjectValue.valueOf(value.toUpperCase())
//                "gas" -> GasEventObjectValue.valueOf(value.toUpperCase())
//                else -> throw SerializationException("Unknown EventObjectValue: $value")
//            }
//            "empty" -> when (decoder.decodeString()) {
//                "food_level" -> FoodLevelEventObjectValue.valueOf(value.toUpperCase())
//                "water_level" -> WaterLevelEventObjectValue.valueOf(value.toUpperCase())
//                else -> throw SerializationException("Unknown EventObjectValue: $value")
//            }
//            "dry", "leak" -> WaterLeakEventObjectValue.valueOf(value.toUpperCase())
//            else -> CustomEventObjectValue(value)
//        }
//    }
//
//    override fun serialize(encoder: Encoder, value: EventObjectValue) {
//        when (value) {
//            is VibrationEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is OpenInstanceObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is ButtonEventObjectValue -> encoder.encodeString(value.name.toLowerCase().replace("_", ""))
//            is MotionEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is SmokeEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is GasEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is BatteryLevelEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is FoodLevelEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is WaterLevelEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is WaterLeakEventObjectValue -> encoder.encodeString(value.name.toLowerCase())
//            is CustomEventObjectValue -> encoder.encodeString(value.value)
//        }
//    }
//}

@Serializable
data class EventObject(val value: EventObjectValue)

@Serializable
data class FloatObjectValue(val value: Float) : PropertyValue

@Serializable
@Polymorphic
sealed interface VibrationEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface OpenInstanceObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface ButtonEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface MotionEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface SmokeEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface GasEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface BatteryLevelEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface FoodLevelEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface WaterLevelEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed interface WaterLeakEventObjectValue : EventObjectValue

@Serializable
@Polymorphic
sealed class PropertyFunction

@Serializable
sealed class FloatFunctions : PropertyFunction() {
    @Serializable
    @SerialName("amperage")
    data object Amperage : FloatFunctions()

    @Serializable
    @SerialName("battery_level")
    data object BatteryLevel : FloatFunctions()

    @Serializable
    @SerialName("co2_level")
    data object Co2Level : FloatFunctions()

    @Serializable
    @SerialName("electricity_meter")
    data object ElectricityMeter : FloatFunctions()

    @Serializable
    @SerialName("food_level")
    data object FoodLevel : FloatFunctions()

    @Serializable
    @SerialName("gas_meter")
    data object GasMeter : FloatFunctions()

    @Serializable
    @SerialName("heat_meter")
    data object HeatMeter : FloatFunctions()

    @Serializable
    @SerialName("humidity")
    data object Humidity : FloatFunctions()

    @Serializable
    @SerialName("illumination")
    data object Illumination : FloatFunctions()

    @Serializable
    @SerialName("meter")
    data object Meter : FloatFunctions()

    @Serializable
    @SerialName("pm10_density")
    data object Pm10Density : FloatFunctions()

    @Serializable
    @SerialName("pm1_density")
    data object Pm1Density : FloatFunctions()

    @Serializable
    @SerialName("pm2.5_density")
    data object Pm2_5Density : FloatFunctions()

    @Serializable
    @SerialName("power")
    data object Power : FloatFunctions()

    @Serializable
    @SerialName("pressure")
    data object Pressure : FloatFunctions()

    @Serializable
    @SerialName("temperature")
    data object Temperature : FloatFunctions()

    @Serializable
    @SerialName("tvoc")
    data object Tvoc : FloatFunctions()

    @Serializable
    @SerialName("voltage")
    data object Voltage : FloatFunctions()

    @Serializable
    @SerialName("water_level")
    data object WaterLevel : FloatFunctions()

    @Serializable
    @SerialName("water_meter")
    data object WaterMeter : FloatFunctions()
}

//@Serializable(with = EventFunctionsSerializer::class)
@Serializable
sealed class EventFunctions : PropertyFunction() {
    @Serializable
    @SerialName("vibration")
    data object Vibration : EventFunctions()

    @Serializable
    @SerialName("open")
    data object Open : EventFunctions()

    @Serializable
    @SerialName("button")
    data object Button : EventFunctions()

    @Serializable
    @SerialName("motion")
    data object Motion : EventFunctions()

    @Serializable
    @SerialName("smoke")
    data object Smoke : EventFunctions()

    @Serializable
    @SerialName("gas")
    data object Gas : EventFunctions()

    @Serializable
    @SerialName("battery_level")
    data object BatteryLevel : EventFunctions()

    @Serializable
    @SerialName("food_level")
    data object FoodLevel : EventFunctions()

    @Serializable
    @SerialName("water_level")
    data object WaterLevel : EventFunctions()

    @Serializable
    @SerialName("water_leak")
    data object WaterLeak : EventFunctions()

    @Serializable
    @SerialName("value")
    data class Custom(val value: String) : EventFunctions()
}

object EventFunctionsSerializer : KSerializer<EventFunctions> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("EventFunctions") {
        element<String>("type")
    }

    override fun deserialize(decoder: Decoder): EventFunctions {
        val value = decoder.decodeString()
        return when (value) {
            "vibration" -> EventFunctions.Vibration
            "open" -> EventFunctions.Open
            "button" -> EventFunctions.Button
            "motion" -> EventFunctions.Motion
            "smoke" -> EventFunctions.Smoke
            "gas" -> EventFunctions.Gas
            "battery_level" -> EventFunctions.BatteryLevel
            "food_level" -> EventFunctions.FoodLevel
            "water_level" -> EventFunctions.WaterLevel
            "water_leak" -> EventFunctions.WaterLeak
            else -> EventFunctions.Custom(value)
        }
    }

    override fun serialize(encoder: Encoder, value: EventFunctions) {
        when (value) {
            is EventFunctions.Vibration -> encoder.encodeString("vibration")
            is EventFunctions.Open -> encoder.encodeString("open")
            is EventFunctions.Button -> encoder.encodeString("button")
            is EventFunctions.Motion -> encoder.encodeString("motion")
            is EventFunctions.Smoke -> encoder.encodeString("smoke")
            is EventFunctions.Gas -> encoder.encodeString("gas")
            is EventFunctions.BatteryLevel -> encoder.encodeString("battery_level")
            is EventFunctions.FoodLevel -> encoder.encodeString("food_level")
            is EventFunctions.WaterLevel -> encoder.encodeString("water_level")
            is EventFunctions.WaterLeak -> encoder.encodeString("water_leak")
            is EventFunctions.Custom -> encoder.encodeString(value.value)
        }
    }
}

@Serializable
sealed class PropertyType {
    @Serializable
    @SerialName("devices.properties.float")
    data object Float : PropertyType()
    @Serializable
    @SerialName("devices.properties.event")
    data object Event : PropertyType()
}

@Serializable
sealed interface PropertyStateObject{
    val type: PropertyType
    val state: PropertyState
}

@Serializable
sealed class PropertyState(
    val propertyFunction: PropertyFunction,
    val propertyValue: PropertyValue
)