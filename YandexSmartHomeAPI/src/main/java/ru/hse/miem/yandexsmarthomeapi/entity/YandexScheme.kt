package ru.hse.miem.yandexsmarthomeapi.entity

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
    @SerialName("aliases") val aliases: List<String>?
    @SerialName("type") val type: DeviceType
    @SerialName("groups") val groups: List<String>?
    @SerialName("room") val room: String?
    @SerialName("external_id") val externalId: String?
    @SerialName("skill_id") val skillId: String?
    @SerialName("capabilities") val capabilities: List<Capability>?
    @SerialName("properties") val properties: List<Property>?
}

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
    @SerialName("type") val type: CapabilityType,
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

@Serializable
enum class DeviceState {
    @SerialName("online")
    ONLINE,
    @SerialName("offline")
    OFFLINE,
    @SerialName("not_found")
    NOT_FOUND,
    @SerialName("split")
    SPLIT
}

@Serializable(with = CapabilityTypeSerializer::class)
enum class CapabilityType {
    @SerialName("devices.capabilities.color_setting")
    COLOR_SETTING,
    @SerialName("devices.capabilities.on_off")
    ON_OFF,
    @SerialName("devices.capabilities.range")
    RANGE,
    @SerialName("devices.capabilities.mode")
    MODE,
    @SerialName("devices.capabilities.toggle")
    TOGGLE,
    @SerialName("devices.capabilities.video_stream")
    VIDEO_STREAM;

    companion object {
        fun fromString(value: String): CapabilityType = when (value) {
            "devices.capabilities.color_setting" -> COLOR_SETTING
            "devices.capabilities.on_off" -> ON_OFF
            "devices.capabilities.range" -> RANGE
            "devices.capabilities.mode" -> MODE
            "devices.capabilities.toggle" -> TOGGLE
            "devices.capabilities.video_stream" -> VIDEO_STREAM
            else -> throw IllegalArgumentException("Unknown CapabilityType value: $value")
        }
        fun toString(value: CapabilityType) = when (value) {
            COLOR_SETTING -> "devices.capabilities.color_setting"
            ON_OFF -> "devices.capabilities.on_off"
            RANGE -> "devices.capabilities.range"
            MODE -> "devices.capabilities.mode"
            TOGGLE -> "devices.capabilities.toggle"
            VIDEO_STREAM -> "devices.capabilities.video_stream"
        }
    }
}

sealed interface Capability{
    @SerialName("type")  val type: CapabilityType
    @SerialName("retrievable") val retrievable: Boolean?
    @SerialName("parameters") val parameters: CapabilityParameterObject?
    @SerialName("state") val state: CapabilityStateObjectData?
    @SerialName("last_updated") val lastUpdated: Float?
}

@Serializable(with = DeviceCapabilityObjectSerializer::class)
data class DeviceCapabilityObject(
    override val type: CapabilityType,
    val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    override val lastUpdated: Float
) : Capability

@Serializable(with = CapabilityObjectSerializer::class)
data class CapabilityObject(
    override val type: CapabilityType,
    override val retrievable: Boolean? = null,
    override val parameters: CapabilityParameterObject? = null,
    override val state: CapabilityStateObjectData?,
    @SerialName("last_updated") override val lastUpdated: Float? = null
) : Capability

@Serializable(with = CapabilityActionResultObjectSerializer::class)
data class CapabilityActionResultObject(
    @SerialName("type") val type: CapabilityType,
    @SerialName("state") val state: StateResultObject
)

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

@Serializable(with = CapabilityParameterObjectSerializer::class)
@Polymorphic
sealed class CapabilityParameterObject

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

@Serializable
enum class ModeCapabilityInstance : CapabilityStateObjectInstance {
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
data class StateResultObject(
    override val instance: CapabilityStateObjectInstance,
    @SerialName("action_result") val actionResult: ActionResult
) : CapabilityState

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

@Serializable
enum class Status {
    @SerialName("DONE") DONE,
    @SerialName("ERROR") ERROR
}

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

@Serializable(with = ColorSettingCapabilityStateObjectValueSerializer::class)
sealed interface ColorSettingCapabilityStateObjectValue : CapabilityStateObjectValue

@Serializable
data class ColorSettingCapabilityStateObjectValueInteger(val value: Int) :
    ColorSettingCapabilityStateObjectValue

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

@Serializable
data class RangeCapabilityStateObjectData(
    override val instance: RangeCapabilityParameterObjectFunction,
    override val value: RangeCapabilityStateObjectDataValue,
    @SerialName("relative") val relative: Boolean? = null
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

@Serializable
data class EventObject(val value: EventObjectValue)

@Serializable
data class FloatObjectValue(val value: Float) : PropertyValue

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

@Serializable
data class DeviceActionsResultObject(
    val id: String,
    val capabilities: List<CapabilityActionResultObject>
)

@Serializable
data class GroupDeviceInfoObject(
    val id: String,
    val name: String,
    val type: DeviceType
)