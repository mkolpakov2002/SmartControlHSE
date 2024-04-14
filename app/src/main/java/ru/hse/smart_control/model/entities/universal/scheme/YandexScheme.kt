package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
//@Serializable
//sealed class DeviceType {
//    @Serializable
//    @SerialName("devices.types.light")
//    data object Light : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.socket")
//    data object Socket : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.switch")
//    data object Switch : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.thermostat")
//    data object Thermostat : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.thermostat.ac")
//    data object ThermostatAC : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.media_device")
//    data object MediaDevice : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.media_device.tv")
//    data object MediaDeviceTV : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.media_device.tv_box")
//    data object MediaDeviceTVBox : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.media_device.receiver")
//    data object MediaDeviceReceiver : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.cooking")
//    data object Cooking : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.cooking.coffee_maker")
//    data object CoffeeMaker : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.cooking.kettle")
//    data object Kettle : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.cooking.multicooker")
//    data object Multicooker : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.openable")
//    data object Openable : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.openable.curtain")
//    data object OpenableCurtain : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.humidifier")
//    data object Humidifier : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.purifier")
//    data object Purifier : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.vacuum_cleaner")
//    data object VacuumCleaner : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.washing_machine")
//    data object WashingMachine : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.dishwasher")
//    data object Dishwasher : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.iron")
//    data object Iron : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor")
//    data object Sensor : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.motion")
//    data object SensorMotion : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.door")
//    data object SensorDoor : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.window")
//    data object SensorWindow : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.water_leak")
//    data object SensorWaterLeak : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.smoke")
//    data object SensorSmoke : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.gas")
//    data object SensorGas : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.vibration")
//    data object SensorVibration : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.button")
//    data object SensorButton : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.sensor.illumination")
//    data object SensorIllumination : DeviceType()
//
//    @Serializable
//    @SerialName("devices.types.other")
//    data object Other : DeviceType()
//
//    @Serializable
//    data class CustomDeviceType(
//        @SerialName(value = "serialName") val serialName: String
//    ) : DeviceType()
//}

@Serializable
enum class DeviceType(@SerialName("serialName") val serialName: String) {
    @SerialName("devices.types.smart_speaker.yandex.station.micro")
    YANDEX_SMART_SPEAKER("devices.types.smart_speaker.yandex.station.micro"),
    @SerialName("devices.types.light")
    LIGHT("devices.types.light"),

    @SerialName("devices.types.socket")
    SOCKET("devices.types.socket"),

    @SerialName("devices.types.switch")
    SWITCH("devices.types.switch"),

    @SerialName("devices.types.thermostat")
    THERMOSTAT("devices.types.thermostat"),

    @SerialName("devices.types.thermostat.ac")
    THERMOSTAT_AC("devices.types.thermostat.ac"),

    @SerialName("devices.types.media_device")
    MEDIA_DEVICE("devices.types.media_device"),

    @SerialName("devices.types.media_device.tv")
    MEDIA_DEVICE_TV("devices.types.media_device.tv"),

    @SerialName("devices.types.media_device.tv_box")
    MEDIA_DEVICE_TV_BOX("devices.types.media_device.tv_box"),

    @SerialName("devices.types.media_device.receiver")
    MEDIA_DEVICE_RECEIVER("devices.types.media_device.receiver"),

    @SerialName("devices.types.cooking")
    COOKING("devices.types.cooking"),

    @SerialName("devices.types.cooking.coffee_maker")
    COFFEE_MAKER("devices.types.cooking.coffee_maker"),

    @SerialName("devices.types.cooking.kettle")
    KETTLE("devices.types.cooking.kettle"),

    @SerialName("devices.types.cooking.multicooker")
    MULTICOOKER("devices.types.cooking.multicooker"),

    @SerialName("devices.types.openable")
    OPENABLE("devices.types.openable"),

    @SerialName("devices.types.openable.curtain")
    OPENABLE_CURTAIN("devices.types.openable.curtain"),

    @SerialName("devices.types.humidifier")
    HUMIDIFIER("devices.types.humidifier"),

    @SerialName("devices.types.purifier")
    PURIFIER("devices.types.purifier"),

    @SerialName("devices.types.vacuum_cleaner")
    VACUUM_CLEANER("devices.types.vacuum_cleaner"),

    @SerialName("devices.types.washing_machine")
    WASHING_MACHINE("devices.types.washing_machine"),

    @SerialName("devices.types.dishwasher")
    DISHWASHER("devices.types.dishwasher"),

    @SerialName("devices.types.iron")
    IRON("devices.types.iron"),

    @SerialName("devices.types.sensor")
    SENSOR("devices.types.sensor"),

    @SerialName("devices.types.sensor.motion")
    SENSOR_MOTION("devices.types.sensor.motion"),

    @SerialName("devices.types.sensor.door")
    SENSOR_DOOR("devices.types.sensor.door"),

    @SerialName("devices.types.sensor.window")
    SENSOR_WINDOW("devices.types.sensor.window"),

    @SerialName("devices.types.sensor.water_leak")
    SENSOR_WATER_LEAK("devices.types.sensor.water_leak"),

    @SerialName("devices.types.sensor.smoke")
    SENSOR_SMOKE("devices.types.sensor.smoke"),

    @SerialName("devices.types.sensor.gas")
    SENSOR_GAS("devices.types.sensor.gas"),

    @SerialName("devices.types.sensor.vibration")
    SENSOR_VIBRATION("devices.types.sensor.vibration"),

    @SerialName("devices.types.sensor.button")
    SENSOR_BUTTON("devices.types.sensor.button"),

    @SerialName("devices.types.sensor.illumination")
    SENSOR_ILLUMINATION("devices.types.sensor.illumination"),

    @SerialName("devices.types.other")
    OTHER("devices.types.other"),

    @SerialName("custom")
    CUSTOM("custom");

}

//object DeviceTypeSerializer : KSerializer<DeviceType> {
//    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DeviceType", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: DeviceType) {
//        when (value) {
//            is DeviceType.Light -> encoder.encodeString("devices.types.light")
//            is DeviceType.Socket -> encoder.encodeString("devices.types.socket")
//            is DeviceType.Switch -> encoder.encodeString("devices.types.switch")
//            is DeviceType.Thermostat -> encoder.encodeString("devices.types.thermostat")
//            is DeviceType.ThermostatAC -> encoder.encodeString("devices.types.thermostat.ac")
//            is DeviceType.MediaDevice -> encoder.encodeString("devices.types.media_device")
//            is DeviceType.MediaDeviceTV -> encoder.encodeString("devices.types.media_device.tv")
//            is DeviceType.MediaDeviceTVBox -> encoder.encodeString("devices.types.media_device.tv_box")
//            is DeviceType.MediaDeviceReceiver -> encoder.encodeString("devices.types.media_device.receiver")
//            is DeviceType.Cooking -> encoder.encodeString("devices.types.cooking")
//            is DeviceType.CoffeeMaker -> encoder.encodeString("devices.types.cooking.coffee_maker")
//            is DeviceType.Kettle -> encoder.encodeString("devices.types.cooking.kettle")
//            is DeviceType.Multicooker -> encoder.encodeString("devices.types.cooking.multicooker")
//            is DeviceType.Openable -> encoder.encodeString("devices.types.openable")
//            is DeviceType.OpenableCurtain -> encoder.encodeString("devices.types.openable.curtain")
//            is DeviceType.Humidifier -> encoder.encodeString("devices.types.humidifier")
//            is DeviceType.Purifier -> encoder.encodeString("devices.types.purifier")
//            is DeviceType.VacuumCleaner -> encoder.encodeString("devices.types.vacuum_cleaner")
//            is DeviceType.WashingMachine -> encoder.encodeString("devices.types.washing_machine")
//            is DeviceType.Dishwasher -> encoder.encodeString("devices.types.dishwasher")
//            is DeviceType.Iron -> encoder.encodeString("devices.types.iron")
//            is DeviceType.Sensor -> encoder.encodeString("devices.types.sensor")
//            is DeviceType.SensorMotion -> encoder.encodeString("devices.types.sensor.motion")
//            is DeviceType.SensorDoor -> encoder.encodeString("devices.types.sensor.door")
//            is DeviceType.SensorWindow -> encoder.encodeString("devices.types.sensor.window")
//            is DeviceType.SensorWaterLeak -> encoder.encodeString("devices.types.sensor.water_leak")
//            is DeviceType.SensorSmoke -> encoder.encodeString("devices.types.sensor.smoke")
//            is DeviceType.SensorGas -> encoder.encodeString("devices.types.sensor.gas")
//            is DeviceType.SensorVibration -> encoder.encodeString("devices.types.sensor.vibration")
//            is DeviceType.SensorButton -> encoder.encodeString("devices.types.sensor.button")
//            is DeviceType.SensorIllumination -> encoder.encodeString("devices.types.sensor.illumination")
//            is DeviceType.Other -> encoder.encodeString("devices.types.other")
//            is DeviceType.CustomDeviceType -> encoder.encodeString(value.serialName)
//        }
//    }
//
//    override fun deserialize(decoder: Decoder): DeviceType {
//        return when (decoder) {
//            is JsonDecoder -> {
//                val element = decoder.decodeJsonElement()
//                when (element) {
//                    is JsonPrimitive -> {
//                        when (element.content) {
//                            "devices.types.light" -> DeviceType.Light
//                            "devices.types.socket" -> DeviceType.Socket
//                            "devices.types.switch" -> DeviceType.Switch
//                            "devices.types.thermostat" -> DeviceType.Thermostat
//                            "devices.types.thermostat.ac" -> DeviceType.ThermostatAC
//                            "devices.types.media_device" -> DeviceType.MediaDevice
//                            "devices.types.media_device.tv" -> DeviceType.MediaDeviceTV
//                            "devices.types.media_device.tv_box" -> DeviceType.MediaDeviceTVBox
//                            "devices.types.media_device.receiver" -> DeviceType.MediaDeviceReceiver
//                            "devices.types.cooking" -> DeviceType.Cooking
//                            "devices.types.cooking.coffee_maker" -> DeviceType.CoffeeMaker
//                            "devices.types.cooking.kettle" -> DeviceType.Kettle
//                            "devices.types.cooking.multicooker" -> DeviceType.Multicooker
//                            "devices.types.openable" -> DeviceType.Openable
//                            "devices.types.openable.curtain" -> DeviceType.OpenableCurtain
//                            "devices.types.humidifier" -> DeviceType.Humidifier
//                            "devices.types.purifier" -> DeviceType.Purifier
//                            "devices.types.vacuum_cleaner" -> DeviceType.VacuumCleaner
//                            "devices.types.washing_machine" -> DeviceType.WashingMachine
//                            "devices.types.dishwasher" -> DeviceType.Dishwasher
//                            "devices.types.iron" -> DeviceType.Iron
//                            "devices.types.sensor" -> DeviceType.Sensor
//                            "devices.types.sensor.motion" -> DeviceType.SensorMotion
//                            "devices.types.sensor.door" -> DeviceType.SensorDoor
//                            "devices.types.sensor.window" -> DeviceType.SensorWindow
//                            "devices.types.sensor.water_leak" -> DeviceType.SensorWaterLeak
//                            "devices.types.sensor.smoke" -> DeviceType.SensorSmoke
//                            "devices.types.sensor.gas" -> DeviceType.SensorGas
//                            "devices.types.sensor.vibration" -> DeviceType.SensorVibration
//                            "devices.types.sensor.button" -> DeviceType.SensorButton
//                            "devices.types.sensor.illumination" -> DeviceType.SensorIllumination
//                            "devices.types.other" -> DeviceType.Other
//                            else -> DeviceType.CustomDeviceType(element.content)
//                        }
//                    }
//                    is JsonObject -> DeviceType.CustomDeviceType(element["serialName"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Expected 'serialName' field"))
//                    else -> throw SerializationException("Expected JsonObject or JsonPrimitive")
//                }
//            }
//            else -> {
//                when (val value = decoder.decodeString()) {
//                    "devices.types.light" -> DeviceType.Light
//                    "devices.types.socket" -> DeviceType.Socket
//                    "devices.types.switch" -> DeviceType.Switch
//                    "devices.types.thermostat" -> DeviceType.Thermostat
//                    "devices.types.thermostat.ac" -> DeviceType.ThermostatAC
//                    "devices.types.media_device" -> DeviceType.MediaDevice
//                    "devices.types.media_device.tv" -> DeviceType.MediaDeviceTV
//                    "devices.types.media_device.tv_box" -> DeviceType.MediaDeviceTVBox
//                    "devices.types.media_device.receiver" -> DeviceType.MediaDeviceReceiver
//                    "devices.types.cooking" -> DeviceType.Cooking
//                    "devices.types.cooking.coffee_maker" -> DeviceType.CoffeeMaker
//                    "devices.types.cooking.kettle" -> DeviceType.Kettle
//                    "devices.types.cooking.multicooker" -> DeviceType.Multicooker
//                    "devices.types.openable" -> DeviceType.Openable
//                    "devices.types.openable.curtain" -> DeviceType.OpenableCurtain
//                    "devices.types.humidifier" -> DeviceType.Humidifier
//                    "devices.types.purifier" -> DeviceType.Purifier
//                    "devices.types.vacuum_cleaner" -> DeviceType.VacuumCleaner
//                    "devices.types.washing_machine" -> DeviceType.WashingMachine
//                    "devices.types.dishwasher" -> DeviceType.Dishwasher
//                    "devices.types.iron" -> DeviceType.Iron
//                    "devices.types.sensor" -> DeviceType.Sensor
//                    "devices.types.sensor.motion" -> DeviceType.SensorMotion
//                    "devices.types.sensor.door" -> DeviceType.SensorDoor
//                    "devices.types.sensor.window" -> DeviceType.SensorWindow
//                    "devices.types.sensor.water_leak" -> DeviceType.SensorWaterLeak
//                    "devices.types.sensor.smoke" -> DeviceType.SensorSmoke
//                    "devices.types.sensor.gas" -> DeviceType.SensorGas
//                    "devices.types.sensor.vibration" -> DeviceType.SensorVibration
//                    "devices.types.sensor.button" -> DeviceType.SensorButton
//                    "devices.types.sensor.illumination" -> DeviceType.SensorIllumination
//                    "devices.types.other" -> DeviceType.Other
//                    else -> DeviceType.CustomDeviceType(value)
//                }
//            }
//        }
//    }
//}

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

@Serializable(with = GroupCapabilityObjectSerializer::class)
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

//@Serializable
//sealed class DeviceState {
//    @Serializable
//    @SerialName("online")
//    data object Online : DeviceState()
//    @Serializable
//    @SerialName("offline")
//    data object Offline : DeviceState()
//}

@Serializable
enum class DeviceState {
    @SerialName("online")
    ONLINE,
    @SerialName("offline")
    OFFLINE
}

sealed interface Capability{
    @SerialName("type")  val type: String
    @SerialName("retrievable") val retrievable: Boolean?
    @SerialName("parameters") val parameters: CapabilityParameterObject?
    @SerialName("state") val state: CapabilityStateObjectData?
    @SerialName("last_updated") val lastUpdated: Float?
}

@Serializable(with = DeviceCapabilityObjectSerializer::class)
data class DeviceCapabilityObject(
    override val type: String,
    val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    override val lastUpdated: Float
) : Capability

@Serializable(with = CapabilityObjectSerializer::class)
data class CapabilityObject(
    override val type: String,
    override val retrievable: Boolean? = null,
    override val parameters: CapabilityParameterObject? = null,
    override val state: CapabilityStateObjectData?,
    @SerialName("last_updated") override val lastUpdated: Float? = null
) : Capability

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

@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = CapabilityParameterObjectSerializer::class)
@Polymorphic
sealed class CapabilityParameterObject

//@OptIn(ExperimentalSerializationApi::class)
//object CapabilityParameterObjectSerializer : KSerializer<CapabilityParameterObject> {
//    override val descriptor: SerialDescriptor = PolymorphicSerializer(CapabilityParameterObject::class).descriptor
//
//    override fun serialize(encoder: Encoder, value: CapabilityParameterObject) {
//        val polymorphicSerializer = PolymorphicSerializer(CapabilityParameterObject::class)
//        polymorphicSerializer.serialize(encoder, value)
//    }
//
//    override fun deserialize(decoder: Decoder): CapabilityParameterObject {
//        val polymorphicSerializer = PolymorphicSerializer(CapabilityParameterObject::class)
//        return polymorphicSerializer.deserialize(decoder)
//    }
//}

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
    val looped: Boolean? = null
): CapabilityParameterObject() {
    init {
        unit = when (instance) {
            RangeCapabilityParameterObjectFunction.BRIGHTNESS,
            RangeCapabilityParameterObjectFunction.HUMIDITY,
            RangeCapabilityParameterObjectFunction.OPEN -> MeasurementUnit.PERCENT
            else -> {
                //RangeCapabilityInstanceOld.TEMPERATURE
                MeasurementUnit.TEMPERATURE_CELSIUS
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
enum class ColorModel {
    @SerialName("rgb") RGB,
    @SerialName("hsv") HSV
}

//interface ColorModel {
//    val stringValue: String
//
//    companion object {
//        fun fromString(value: String): ColorModel = when (value.uppercase()) {
//            RGB.stringValue -> RGB
//            HSV.stringValue -> HSV
//            else -> throw IllegalArgumentException("Unknown ColorModel value: $value")
//        }
//    }
//}
//
//@Serializable
//@SerialName("rgb")
//object RGB : ColorModel {
//    override val stringValue: String = "RGB"
//}
//
//@Serializable
//@SerialName("hsv")
//object HSV : ColorModel {
//    override val stringValue: String = "HSV"
//}

//object ColorModelSerializer : KSerializer<ColorModel> {
//    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ColorModel", PrimitiveKind.STRING)
//
//    override fun deserialize(decoder: Decoder): ColorModel {
//        val value = decoder.decodeString()
//        return ColorModel.fromString(value)
//    }
//
//    override fun serialize(encoder: Encoder, value: ColorModel) {
//        encoder.encodeString(value.stringValue.lowercase())
//    }
//}

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

//@Serializable
//@Polymorphic
//sealed class SceneObject {
//    @Serializable
//    @SerialName("alarm")
//    data object Alarm : SceneObject()
//
//    @Serializable
//    @SerialName("alice")
//    data object Alice : SceneObject()
//
//    @Serializable
//    @SerialName("candle")
//    data object Candle : SceneObject()
//
//    @Serializable
//    @SerialName("dinner")
//    data object Dinner : SceneObject()
//
//    @Serializable
//    @SerialName("fantasy")
//    data object Fantasy : SceneObject()
//
//    @Serializable
//    @SerialName("garland")
//    data object Garland : SceneObject()
//
//    @Serializable
//    @SerialName("jungle")
//    data object Jungle : SceneObject()
//
//    @Serializable
//    @SerialName("movie")
//    data object Movie : SceneObject()
//
//    @Serializable
//    @SerialName("neon")
//    data object Neon : SceneObject()
//
//    @Serializable
//    @SerialName("night")
//    data object Night : SceneObject()
//
//    @Serializable
//    @SerialName("ocean")
//    data object Ocean : SceneObject()
//
//    @Serializable
//    @SerialName("party")
//    data object Party : SceneObject()
//
//    @Serializable
//    @SerialName("reading")
//    data object Reading : SceneObject()
//
//    @Serializable
//    @SerialName("rest")
//    data object Rest : SceneObject()
//
//    @Serializable
//    @SerialName("romance")
//    data object Romance : SceneObject()
//
//    @Serializable
//    @SerialName("siren")
//    data object Siren : SceneObject()
//
//    @Serializable
//    @SerialName("sunrise")
//    data object Sunrise : SceneObject()
//
//    @Serializable
//    @SerialName("sunset")
//    data object Sunset : SceneObject()
//}

@Serializable
enum class SceneObject {
    @SerialName("alarm") ALARM,
    @SerialName("alice") ALICE,
    @SerialName("candle") CANDLE,
    @SerialName("dinner") DINNER,
    @SerialName("fantasy") FANTASY,
    @SerialName("garland") GARLAND,
    @SerialName("jungle") JUNGLE,
    @SerialName("movie") MOVIE,
    @SerialName("neon") NEON,
    @SerialName("night") NIGHT,
    @SerialName("ocean") OCEAN,
    @SerialName("party") PARTY,
    @SerialName("reading") READING,
    @SerialName("rest") REST,
    @SerialName("romance") ROMANCE,
    @SerialName("siren") SIREN,
    @SerialName("sunrise") SUNRISE,
    @SerialName("sunset") SUNSET
}

@Serializable
data class ModeObject(val value: ModeCapabilityMode)

//@Serializable
//sealed class ModeCapabilityMode : CapabilityStateObjectValue() {
//    @Serializable
//    @SerialName("auto")
//    data object Auto : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("eco")
//    data object Eco : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("smart")
//    data object Smart : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("turbo")
//    data object Turbo : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("cool")
//    data object Cool : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("dry")
//    data object Dry : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("fan_only")
//    data object FanOnly : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("heat")
//    data object Heat : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("preheat")
//    data object Preheat : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("high")
//    data object High : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("low")
//    data object Low : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("medium")
//    data object Medium : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("max")
//    data object Max : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("min")
//    data object Min : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("fast")
//    data object Fast : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("slow")
//    data object Slow : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("express")
//    data object Express : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("normal")
//    data object Normal : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("quiet")
//    data object Quiet : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("horizontal")
//    data object Horizontal : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("stationary")
//    data object Stationary : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("vertical")
//    data object Vertical : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("one")
//    data object One : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("two")
//    data object Two : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("three")
//    data object Three : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("four")
//    data object Four : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("five")
//    data object Five : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("six")
//    data object Six : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("seven")
//    data object Seven : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("eight")
//    data object Eight : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("nine")
//    data object Nine : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("ten")
//    data object Ten : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("americano")
//    data object Americano : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("cappuccino")
//    data object Cappuccino : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("double")
//    data object Double : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("espresso")
//    data object Espresso : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("double_espresso")
//    data object DoubleEspresso : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("latte")
//    data object Latte : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("black_tea")
//    data object BlackTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("flower_tea")
//    data object FlowerTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("green_tea")
//    data object GreenTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("herbal_tea")
//    data object HerbalTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("oolong_tea")
//    data object OolongTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("puerh_tea")
//    data object PuerhTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("red_tea")
//    data object RedTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("white_tea")
//    data object WhiteTea : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("glass")
//    data object Glass : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("intensive")
//    data object Intensive : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("pre_rinse")
//    data object PreRinse : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("aspic")
//    data object Aspic : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("baby_food")
//    data object BabyFood : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("baking")
//    data object Baking : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("bread")
//    data object Bread : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("boiling")
//    data object Boiling : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("cereals")
//    data object Cereals : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("cheesecake")
//    data object Cheesecake : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("deep_fryer")
//    data object DeepFryer : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("dessert")
//    data object Dessert : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("fowl")
//    data object Fowl : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("frying")
//    data object Frying : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("macaroni")
//    data object Macaroni : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("milk_porridge")
//    data object MilkPorridge : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("multicooker")
//    data object MultiCooker : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("pasta")
//    data object Pasta : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("pilaf")
//    data object Pilaf : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("pizza")
//    data object Pizza : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("sauce")
//    data object Sauce : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("slow_cook")
//    data object SlowCook : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("soup")
//    data object Soup : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("steam")
//    data object Steam : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("stewing")
//    data object Stewing : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("vacuum")
//    data object Vacuum : ModeCapabilityMode()
//
//    @Serializable
//    @SerialName("yogurt")
//    data object Yogurt : ModeCapabilityMode()
//}

@Serializable
enum class ModeCapabilityMode : CapabilityStateObjectValue {
    @SerialName("auto") AUTO,
    @SerialName("eco") ECO,
    @SerialName("smart") SMART,
    @SerialName("turbo") TURBO,
    @SerialName("cool") COOL,
    @SerialName("dry") DRY,
    @SerialName("fan_only") FAN_ONLY,
    @SerialName("heat") HEAT,
    @SerialName("preheat") PREHEAT,
    @SerialName("high") HIGH,
    @SerialName("low") LOW,
    @SerialName("medium") MEDIUM,
    @SerialName("max") MAX,
    @SerialName("min") MIN,
    @SerialName("fast") FAST,
    @SerialName("slow") SLOW,
    @SerialName("express") EXPRESS,
    @SerialName("normal") NORMAL,
    @SerialName("quiet") QUIET,
    @SerialName("horizontal") HORIZONTAL,
    @SerialName("stationary") STATIONARY,
    @SerialName("vertical") VERTICAL,
    @SerialName("one") ONE,
    @SerialName("two") TWO,
    @SerialName("three") THREE,
    @SerialName("four") FOUR,
    @SerialName("five") FIVE,
    @SerialName("six") SIX,
    @SerialName("seven") SEVEN,
    @SerialName("eight") EIGHT,
    @SerialName("nine") NINE,
    @SerialName("ten") TEN,
    @SerialName("americano") AMERICANO,
    @SerialName("cappuccino") CAPPUCCINO,
    @SerialName("double") DOUBLE,
    @SerialName("espresso") ESPRESSO,
    @SerialName("double_espresso") DOUBLE_ESPRESSO,
    @SerialName("latte") LATTE,
    @SerialName("black_tea") BLACK_TEA,
    @SerialName("flower_tea") FLOWER_TEA,
    @SerialName("green_tea") GREEN_TEA,
    @SerialName("herbal_tea") HERBAL_TEA,
    @SerialName("oolong_tea") OOLONG_TEA,
    @SerialName("puerh_tea") PUERH_TEA,
    @SerialName("red_tea") RED_TEA,
    @SerialName("white_tea") WHITE_TEA,
    @SerialName("glass") GLASS,
    @SerialName("intensive") INTENSIVE,
    @SerialName("pre_rinse") PRE_RINSE,
    @SerialName("aspic") ASPIC,
    @SerialName("baby_food") BABY_FOOD,
    @SerialName("baking") BAKING,
    @SerialName("bread") BREAD,
    @SerialName("boiling") BOILING,
    @SerialName("cereals") CEREALS,
    @SerialName("cheesecake") CHEESECAKE,
    @SerialName("deep_fryer") DEEP_FRYER,
    @SerialName("dessert") DESSERT,
    @SerialName("fowl") FOWL,
    @SerialName("frying") FRYING,
    @SerialName("macaroni") MACARONI,
    @SerialName("milk_porridge") MILK_PORRIDGE,
    @SerialName("multicooker") MULTICOOKER,
    @SerialName("pasta") PASTA,
    @SerialName("pilaf") PILAF,
    @SerialName("pizza") PIZZA,
    @SerialName("sauce") SAUCE,
    @SerialName("slow_cook") SLOW_COOK,
    @SerialName("soup") SOUP,
    @SerialName("steam") STEAM,
    @SerialName("stewing") STEWING,
    @SerialName("vacuum") VACUUM,
    @SerialName("yogurt") YOGURT
}

//@Serializable
//@Polymorphic
//sealed class ModeCapabilityInstance : CapabilityStateObjectInstance {
//    @Serializable
//    @SerialName("cleanup_mode")
//    data object CleanupMode : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("coffee_mode")
//    data object CoffeeMode : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("dishwashing")
//    data object Dishwashing : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("fan_speed")
//    data object FanSpeed : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("heat")
//    data object Heat : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("input_source")
//    data object InputSource : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("program")
//    data object Program : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("swing")
//    data object Swing : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("tea_mode")
//    data object TeaMode : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("thermostat")
//    data object Thermostat : ModeCapabilityInstance()
//
//    @Serializable
//    @SerialName("work_speed")
//    data object WorkSpeed : ModeCapabilityInstance()
//}

@Serializable
enum class ModeCapabilityInstance : CapabilityStateObjectInstance{
    @SerialName("cleanup_mode") CLEANUP_MODE,
    @SerialName("coffee_mode") COFFEE_MODE,
    @SerialName("dishwashing") DISHWASHING,
    @SerialName("fan_speed") FAN_SPEED,
    @SerialName("heat") HEAT,
    @SerialName("input_source") INPUT_SOURCE,
    @SerialName("program") PROGRAM,
    @SerialName("swing") SWING,
    @SerialName("tea_mode") TEA_MODE,
    @SerialName("thermostat") THERMOSTAT,
    @SerialName("work_speed") WORK_SPEED
}

//@Serializable
//@Polymorphic
//sealed class RangeCapabilityParameterObjectFunction : CapabilityStateObjectInstance {
//    @Serializable
//    @SerialName("brightness")
//    data object Brightness : RangeCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("channel")
//    data object Channel : RangeCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("humidity")
//    data object Humidity : RangeCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("open")
//    data object Open : RangeCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("temperature")
//    data object Temperature : RangeCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("volume")
//    data object Volume : RangeCapabilityParameterObjectFunction()
//}

@Serializable
enum class RangeCapabilityParameterObjectFunction : CapabilityStateObjectInstance {
    @SerialName("brightness") BRIGHTNESS,
    @SerialName("channel") CHANNEL,
    @SerialName("humidity") HUMIDITY,
    @SerialName("open") OPEN,
    @SerialName("temperature") TEMPERATURE,
    @SerialName("volume") VOLUME
}

@Serializable
data class Range(
    val min: Float,
    val max: Float,
    val precision: Float
) {
    override fun toString() = "[$min, $max]"
}

//@Serializable
//sealed class ToggleCapabilityParameterObjectFunction : CapabilityStateObjectInstance {
//    @Serializable
//    @SerialName("backlight")
//    data object Backlight : ToggleCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("controls_locked")
//    data object ControlsLocked : ToggleCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("ionization")
//    data object Ionization : ToggleCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("keep_warm")
//    data object KeepWarm : ToggleCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("mute")
//    data object Mute : ToggleCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("oscillation")
//    data object Oscillation : ToggleCapabilityParameterObjectFunction()
//
//    @Serializable
//    @SerialName("pause")
//    data object Pause : ToggleCapabilityParameterObjectFunction()
//}

@Serializable
enum class ToggleCapabilityParameterObjectFunction : CapabilityStateObjectInstance {
    @SerialName("backlight") BACKLIGHT,
    @SerialName("controls_locked") CONTROLS_LOCKED,
    @SerialName("ionization") IONIZATION,
    @SerialName("keep_warm") KEEP_WARM,
    @SerialName("mute") MUTE,
    @SerialName("oscillation") OSCILLATION,
    @SerialName("pause") PAUSE
}

//@Serializable
//sealed class VideoStreamCapabilityParameterObjectStreamProtocol {
//    @Serializable
//    @SerialName("hls")
//    data object HLS: VideoStreamCapabilityParameterObjectStreamProtocol()
//    @Serializable
//    @SerialName("rtmp")
//    data object RTMP: VideoStreamCapabilityParameterObjectStreamProtocol()
//}

@Serializable
enum class VideoStreamCapabilityParameterObjectStreamProtocol {
    @SerialName("hls") HLS,
    @SerialName("rtmp") RTMP
}

@Serializable
@Polymorphic
sealed interface CapabilityState {
    val instance: CapabilityStateObjectInstance
}

@Serializable
@Polymorphic
sealed class CapabilityStateObjectData: CapabilityState {
    abstract override val instance: CapabilityStateObjectInstance
    abstract val value: CapabilityStateObjectValue
}

@Serializable
sealed interface CapabilityStateObjectActionResult : CapabilityState {
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
enum class ErrorCode {
    DOOR_OPEN, LID_OPEN, REMOTE_CONTROL_DISABLED, NOT_ENOUGH_WATER, LOW_CHARGE_LEVEL,
    CONTAINER_FULL, CONTAINER_EMPTY, DRIP_TRAY_FULL, DEVICE_STUCK, DEVICE_OFF,
    FIRMWARE_OUT_OF_DATE, NOT_ENOUGH_DETERGENT, HUMAN_INVOLVEMENT_NEEDED, DEVICE_UNREACHABLE,
    DEVICE_BUSY, INTERNAL_ERROR, INVALID_ACTION, INVALID_VALUE, NOT_SUPPORTED_IN_CURRENT_MODE,
    ACCOUNT_LINKING_ERROR, DEVICE_NOT_FOUND
}

//@Serializable
//sealed class ErrorCode {
//    @Serializable
//    @SerialName("DOOR_OPEN")
//    data object DoorOpen : ErrorCode()
//
//    @Serializable
//    @SerialName("LID_OPEN")
//    data object LidOpen : ErrorCode()
//
//    @Serializable
//    @SerialName("REMOTE_CONTROL_DISABLED")
//    data object RemoteControlDisabled : ErrorCode()
//
//    @Serializable
//    @SerialName("NOT_ENOUGH_WATER")
//    data object NotEnoughWater : ErrorCode()
//
//    @Serializable
//    @SerialName("LOW_CHARGE_LEVEL")
//    data object LowChargeLevel : ErrorCode()
//
//    @Serializable
//    @SerialName("CONTAINER_FULL")
//    data object ContainerFull : ErrorCode()
//
//    @Serializable
//    @SerialName("CONTAINER_EMPTY")
//    data object ContainerEmpty : ErrorCode()
//
//    @Serializable
//    @SerialName("DRIP_TRAY_FULL")
//    data object DripTrayFull : ErrorCode()
//
//    @Serializable
//    @SerialName("DEVICE_STUCK")
//    data object DeviceStuck : ErrorCode()
//
//    @Serializable
//    @SerialName("DEVICE_OFF")
//    data object DeviceOff : ErrorCode()
//
//    @Serializable
//    @SerialName("FIRMWARE_OUT_OF_DATE")
//    data object FirmwareOutOfDate : ErrorCode()
//
//    @Serializable
//    @SerialName("NOT_ENOUGH_DETERGENT")
//    data object NotEnoughDetergent : ErrorCode()
//
//    @Serializable
//    @SerialName("HUMAN_INVOLVEMENT_NEEDED")
//    data object HumanInvolvementNeeded : ErrorCode()
//
//    @Serializable
//    @SerialName("DEVICE_UNREACHABLE")
//    data object DeviceUnreachable : ErrorCode()
//
//    @Serializable
//    @SerialName("DEVICE_BUSY")
//    data object DeviceBusy : ErrorCode()
//
//    @Serializable
//    @SerialName("INTERNAL_ERROR")
//    data object InternalError : ErrorCode()
//
//    @Serializable
//    @SerialName("INVALID_ACTION")
//    data object InvalidAction : ErrorCode()
//
//    @Serializable
//    @SerialName("INVALID_VALUE")
//    data object InvalidValue : ErrorCode()
//
//    @Serializable
//    @SerialName("NOT_SUPPORTED_IN_CURRENT_MODE")
//    data object NotSupportedInCurrentMode : ErrorCode()
//
//    @Serializable
//    @SerialName("ACCOUNT_LINKING_ERROR")
//    data object AccountLinkingError : ErrorCode()
//
//    @Serializable
//    @SerialName("DEVICE_NOT_FOUND")
//    data object DeviceNotFound : ErrorCode()
//}

@Serializable
enum class Status {
    @SerialName("DONE") DONE,
    @SerialName("ERROR") ERROR
}

//@Serializable
//sealed class Status {
//    @Serializable
//    @SerialName("DONE")
//    data object Done: Status()
//    @Serializable
//    @SerialName("ERROR")
//    data object Error: Status()
//    @Serializable
//    data class CustomStatus(val value: String): Status()
//}

@Serializable
@Polymorphic
sealed interface CapabilityStateObjectInstance

@Serializable
@Polymorphic
sealed interface CapabilityStateObjectValue

@Serializable
data class OnOffCapabilityStateObjectData(
    override val instance: OnOffCapabilityStateObjectInstance,
    override val value: OnOffCapabilityStateObjectValue
): CapabilityStateObjectData()

@Serializable
enum class OnOffCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @SerialName("on") ON
}

//@Serializable
//sealed class OnOffCapabilityStateObjectInstance : CapabilityStateObjectInstance {
//    @Serializable
//    @SerialName("on")
//    data object On: OnOffCapabilityStateObjectInstance()
//}

@Serializable
data class ColorSettingCapabilityStateObjectData(
    override val instance: ColorSettingCapabilityStateObjectInstance,
    override val value: ColorSettingCapabilityStateObjectValue
): CapabilityStateObjectData()

@Serializable
enum class ColorSettingCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @SerialName("base") BASE,
    @SerialName("rgb") RGB,
    @SerialName("hsv") HSV,
    @SerialName("temperature_k") TEMPERATURE_K,
    @SerialName("scene") SCENE
}

//@Serializable
//sealed class ColorSettingCapabilityStateObjectInstance : CapabilityStateObjectInstance {
//    @Serializable
//    @SerialName("base")
//    data object Base : ColorSettingCapabilityStateObjectInstance()
//    @Serializable
//    @SerialName("rgb")
//    data object RGB : ColorSettingCapabilityStateObjectInstance()
//    @Serializable
//    @SerialName("hsv")
//    data object HSV : ColorSettingCapabilityStateObjectInstance()
//    @Serializable
//    @SerialName("temperature_k")
//    data object TemperatureK : ColorSettingCapabilityStateObjectInstance()
//    @Serializable
//    @SerialName("scene")
//    data object Scene : ColorSettingCapabilityStateObjectInstance()
//}

@Serializable(with = ColorSettingCapabilityStateObjectValueSerializer::class)
sealed interface ColorSettingCapabilityStateObjectValue : CapabilityStateObjectValue

//object ColorSettingCapabilityStateObjectValueSerializer : JsonContentPolymorphicSerializer<ColorSettingCapabilityStateObjectValue>(ColorSettingCapabilityStateObjectValue::class) {
//    override fun selectDeserializer(element: JsonElement) = when (element) {
//        is JsonObject -> when {
//            "scene" in element -> ColorSettingCapabilityStateObjectValueObjectScene.serializer()
//            "h" in element && "s" in element && "v" in element -> ColorSettingCapabilityStateObjectValueObjectHSV.serializer()
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

@Serializable
data class ColorSettingCapabilityStateObjectValueInteger(val value: Int) : ColorSettingCapabilityStateObjectValue

@Serializable
data class ColorSettingCapabilityStateObjectValueObjectScene(val value: SceneObject)
    : ColorSettingCapabilityStateObjectValue

@Serializable
data class ColorSettingCapabilityStateObjectValueObjectHSV(val value: HSVObject)
    : ColorSettingCapabilityStateObjectValue

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
) : CapabilityStateObjectValue

@Serializable
data class VideoStreamCapabilityStateObjectActionResultValue(
    val protocol: VideoStreamCapabilityParameterObjectStreamProtocol,
    @SerialName("stream_url") val streamUrl: String
) : CapabilityStateObjectValue

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
) : CapabilityStateObjectValue

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
) : CapabilityStateObjectValue

@Serializable
data class ToggleCapabilityStateObjectActionResult(
    override val instance: ToggleCapabilityParameterObjectFunction,
    override val actionResult: ActionResult
): CapabilityStateObjectActionResult

@Serializable(with = PropertyParameterObjectSerializer::class)
sealed class PropertyParameterObject {
    abstract val instance: Function
}

@Serializable
@SerialName("float")
data class FloatPropertyParameterObject(
    override val instance: Function,
    val unit: MeasurementUnit
) : PropertyParameterObject()

@Serializable
enum class MeasurementUnit(val serialName: String) {
    @SerialName("unit.temperature.celsius")
    TEMPERATURE_CELSIUS("unit.temperature.celsius"),

    @SerialName("unit.ampere")
    AMPERE("unit.ampere"),

    @SerialName("unit.cubic_meter")
    CUBIC_METER("unit.cubic_meter"),

    @SerialName("unit.gigacalorie")
    GIGACALORIE("unit.gigacalorie"),

    @SerialName("unit.kilowatt_hour")
    KILOWATT_HOUR("unit.kilowatt_hour"),

    @SerialName("unit.illumination.lux")
    LUX("unit.illumination.lux"),

    @SerialName("unit.density.mcg_m3")
    MCG_M3("unit.density.mcg_m3"),

    @SerialName("unit.percent")
    PERCENT("unit.percent"),

    @SerialName("unit.ppm")
    PPM("unit.ppm"),

    @SerialName("unit.volt")
    VOLT("unit.volt"),

    @SerialName("unit.watt")
    WATT("unit.watt");
}

@Serializable
@SerialName("event")
data class EventPropertyParameterObject(
    override val instance: Function,
    val events: List<EventObject>
) : PropertyParameterObject()

@Serializable
@Polymorphic
sealed interface PropertyValue

//@Serializable(with = EventObjectValueSerializer::class)
//@Serializable
//sealed interface EventObjectValue : PropertyValue {
//    @Serializable
//    @SerialName("tilt")
//    data object Tilt : EventObjectValue
//
//    @Serializable
//    @SerialName("fall")
//    data object Fall : EventObjectValue
//
//    @Serializable
//    @SerialName("vibration")
//    data object Vibration : EventObjectValue
//
//    @Serializable
//    @SerialName("opened")
//    data object Opened : EventObjectValue
//
//    @Serializable
//    @SerialName("closed")
//    data object Closed : EventObjectValue
//
//    @Serializable
//    @SerialName("click")
//    data object Click : EventObjectValue
//
//    @Serializable
//    @SerialName("double_click")
//    data object DoubleClick : EventObjectValue
//
//    @Serializable
//    @SerialName("long_press")
//    data object LongPress : EventObjectValue
//
//    @Serializable
//    @SerialName("detected")
//    data object Detected : EventObjectValue
//
//    @Serializable
//    @SerialName("not_detected")
//    data object NotDetected : EventObjectValue
//
//    @Serializable
//    @SerialName("high")
//    data object High : EventObjectValue
//
//    @Serializable
//    @SerialName("low")
//    data object Low : EventObjectValue
//
//    @Serializable
//    @SerialName("normal")
//    data object Normal : EventObjectValue
//
//    @Serializable
//    @SerialName("empty")
//    data object Empty : EventObjectValue
//
//    @Serializable
//    @SerialName("dry")
//    data object Dry : EventObjectValue
//
//    @Serializable
//    @SerialName("leak")
//    data object Leak : EventObjectValue
//}

@Serializable
enum class EventObjectValue(@SerialName("value") val value: String) {
    @SerialName("speech_finished")
    SPEECH_FINISHED("speech_finished"),

    @SerialName("tilt")
    TILT("tilt"),

    @SerialName("fall")
    FALL("fall"),

    @SerialName("vibration")
    VIBRATION("vibration"),

    @SerialName("opened")
    OPENED("opened"),

    @SerialName("closed")
    CLOSED("closed"),

    @SerialName("click")
    CLICK("click"),

    @SerialName("double_click")
    DOUBLE_CLICK("double_click"),

    @SerialName("long_press")
    LONG_PRESS("long_press"),

    @SerialName("detected")
    DETECTED("detected"),

    @SerialName("not_detected")
    NOT_DETECTED("not_detected"),

    @SerialName("high")
    HIGH("high"),

    @SerialName("low")
    LOW("low"),

    @SerialName("normal")
    NORMAL("normal"),

    @SerialName("empty")
    EMPTY("empty"),

    @SerialName("dry")
    DRY("dry"),

    @SerialName("leak")
    LEAK("leak");
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

//@Serializable
//@Polymorphic
//sealed interface VibrationEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface OpenInstanceObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface ButtonEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface MotionEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface SmokeEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface GasEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface BatteryLevelEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface FoodLevelEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface WaterLevelEventObjectValue : EventObjectValue
//
//@Serializable
//@Polymorphic
//sealed interface WaterLeakEventObjectValue : EventObjectValue

//@Serializable
//@Polymorphic
//sealed class PropertyFunction

//@Serializable
//sealed class Function {
//    @Serializable
//    @SerialName("amperage")
//    data object Amperage : Function()
//
//    @Serializable
//    @SerialName("battery_level")
//    data object BatteryLevel : Function()
//
//    @Serializable
//    @SerialName("co2_level")
//    data object Co2Level : Function()
//
//    @Serializable
//    @SerialName("electricity_meter")
//    data object ElectricityMeter : Function()
//
//    @Serializable
//    @SerialName("food_level")
//    data object FoodLevel : Function()
//
//    @Serializable
//    @SerialName("gas_meter")
//    data object GasMeter : Function()
//
//    @Serializable
//    @SerialName("heat_meter")
//    data object HeatMeter : Function()
//
//    @Serializable
//    @SerialName("humidity")
//    data object Humidity : Function()
//
//    @Serializable
//    @SerialName("illumination")
//    data object Illumination : Function()
//
//    @Serializable
//    @SerialName("meter")
//    data object Meter : Function()
//
//    @Serializable
//    @SerialName("pm10_density")
//    data object Pm10Density : Function()
//
//    @Serializable
//    @SerialName("pm1_density")
//    data object Pm1Density : Function()
//
//    @Serializable
//    @SerialName("pm2.5_density")
//    data object Pm2_5Density : Function()
//
//    @Serializable
//    @SerialName("power")
//    data object Power : Function()
//
//    @Serializable
//    @SerialName("pressure")
//    data object Pressure : Function()
//
//    @Serializable
//    @SerialName("temperature")
//    data object Temperature : Function()
//
//    @Serializable
//    @SerialName("tvoc")
//    data object Tvoc : Function()
//
//    @Serializable
//    @SerialName("voltage")
//    data object Voltage : Function()
//
//    @Serializable
//    @SerialName("water_level")
//    data object WaterLevel : Function()
//
//    @Serializable
//    @SerialName("water_meter")
//    data object WaterMeter : Function()
//
//    @Serializable
//    @SerialName("vibration")
//    data object Vibration : Function()
//
//    @Serializable
//    @SerialName("open")
//    data object Open : Function()
//
//    @Serializable
//    @SerialName("button")
//    data object Button : Function()
//
//    @Serializable
//    @SerialName("motion")
//    data object Motion : Function()
//
//    @Serializable
//    @SerialName("smoke")
//    data object Smoke : Function()
//
//    @Serializable
//    @SerialName("gas")
//    data object Gas : Function()
//
//    @Serializable
//    @SerialName("water_leak")
//    data object WaterLeak : Function()
//
//    //TODO
//    @Serializable
//    @SerialName("value")
//    data class Custom(val value: String) : Function()
//}

@Serializable
enum class Function(@SerialName("value") val value: String) {
    @SerialName("voice_activity")
    VOICE_ACTIVITY("voice_activity"),

    @SerialName("amperage")
    AMPERAGE("amperage"),

    @SerialName("battery_level")
    BATTERY_LEVEL("battery_level"),

    @SerialName("co2_level")
    CO2_LEVEL("co2_level"),

    @SerialName("electricity_meter")
    ELECTRICITY_METER("electricity_meter"),

    @SerialName("food_level")
    FOOD_LEVEL("food_level"),

    @SerialName("gas_meter")
    GAS_METER("gas_meter"),

    @SerialName("heat_meter")
    HEAT_METER("heat_meter"),

    @SerialName("humidity")
    HUMIDITY("humidity"),

    @SerialName("illumination")
    ILLUMINATION("illumination"),

    @SerialName("meter")
    METER("meter"),

    @SerialName("pm10_density")
    PM10_DENSITY("pm10_density"),

    @SerialName("pm1_density")
    PM1_DENSITY("pm1_density"),

    @SerialName("pm2.5_density")
    PM2_5_DENSITY("pm2.5_density"),

    @SerialName("power")
    POWER("power"),

    @SerialName("pressure")
    PRESSURE("pressure"),

    @SerialName("temperature")
    TEMPERATURE("temperature"),

    @SerialName("tvoc")
    TVOC("tvoc"),

    @SerialName("voltage")
    VOLTAGE("voltage"),

    @SerialName("water_level")
    WATER_LEVEL("water_level"),

    @SerialName("water_meter")
    WATER_METER("water_meter"),

    @SerialName("vibration")
    VIBRATION("vibration"),

    @SerialName("open")
    OPEN("open"),

    @SerialName("button")
    BUTTON("button"),

    @SerialName("motion")
    MOTION("motion"),

    @SerialName("smoke")
    SMOKE("smoke"),

    @SerialName("gas")
    GAS("gas"),

    @SerialName("water_leak")
    WATER_LEAK("water_leak"),

    @SerialName("custom")
    CUSTOM("custom");

}

//object EventFunctionsSerializer : KSerializer<EventFunctions> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("EventFunctions") {
//        element<String>("type")
//    }
//
//    override fun deserialize(decoder: Decoder): EventFunctions {
//        val value = decoder.decodeString()
//        return when (value) {
//            "vibration" -> EventFunctions.Vibration
//            "open" -> EventFunctions.Open
//            "button" -> EventFunctions.Button
//            "motion" -> EventFunctions.Motion
//            "smoke" -> EventFunctions.Smoke
//            "gas" -> EventFunctions.Gas
//            "battery_level" -> EventFunctions.BatteryLevel
//            "food_level" -> EventFunctions.FoodLevel
//            "water_level" -> EventFunctions.WaterLevel
//            "water_leak" -> EventFunctions.WaterLeak
//            else -> EventFunctions.Custom(value)
//        }
//    }
//
//    override fun serialize(encoder: Encoder, value: EventFunctions) {
//        when (value) {
//            is EventFunctions.Vibration -> encoder.encodeString("vibration")
//            is EventFunctions.Open -> encoder.encodeString("open")
//            is EventFunctions.Button -> encoder.encodeString("button")
//            is EventFunctions.Motion -> encoder.encodeString("motion")
//            is EventFunctions.Smoke -> encoder.encodeString("smoke")
//            is EventFunctions.Gas -> encoder.encodeString("gas")
//            is EventFunctions.BatteryLevel -> encoder.encodeString("battery_level")
//            is EventFunctions.FoodLevel -> encoder.encodeString("food_level")
//            is EventFunctions.WaterLevel -> encoder.encodeString("water_level")
//            is EventFunctions.WaterLeak -> encoder.encodeString("water_leak")
//            is EventFunctions.Custom -> encoder.encodeString(value.value)
//        }
//    }
//}

//@Serializable
//sealed class PropertyType {
//    @Serializable
//    @SerialName("devices.properties.float")
//    data object Float : PropertyType()
//    @Serializable
//    @SerialName("devices.properties.event")
//    data object Event : PropertyType()
//}

@Serializable
enum class PropertyType(@SerialName("value") val value: String) {
    @SerialName("devices.properties.float")
    FLOAT("devices.properties.float"),

    @SerialName("devices.properties.event")
    EVENT("devices.properties.event");
}

@Serializable
sealed interface PropertyStateObject{
    val type: PropertyType
    val state: PropertyState
}

@Serializable
sealed class PropertyState(
    val propertyFunction: Function,
    val propertyValue: PropertyValue
)

@Serializable
data class DeviceActionsObject(
    val id: String,
    val actions: List<CapabilityObject>
)