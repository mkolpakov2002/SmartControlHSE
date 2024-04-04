package ru.hse.control_system_v2.model.entities.universal.scheme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Response {
    val status: String
    @SerialName("request_id")
    val requestId: String
}

@Serializable
sealed class UserHomeInfoApiResponse {
    @Serializable
    data class Success(val data: UserInfoResponse) : UserHomeInfoApiResponse()
    @Serializable
    data class Error(val error: UserInfoErrorModel) : UserHomeInfoApiResponse()
}

@Serializable
data class UserInfoErrorModel(
    override val status: String,
    override val requestId: String,
    val error: String
): Response

@Serializable
data class UserInfoResponse(
    override val requestId: String,
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
}

@Serializable
data class CustomDeviceType(
    @SerialName(value = "serialName") val serialName: String
) : DeviceType()

@Serializable
data class DeviceObject(
    override val id: String,
    override val name: String,
    override val aliases: List<String>,
    override val type: DeviceType,
    override val externalId: String,
    override val skillId: String,
    @SerialName("household_id") val householdId: String,
    override val room: String?,
    override val groups: List<String>,
    override val capabilities: List<DeviceCapabilityObject>,
    override val properties: List<DevicePropertyObject>
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
    @SerialName("name") val name: String
)

@Serializable
data class GroupCapabilityObject(
    @SerialName("type") val type: String,
    @SerialName("retrievable") val retrievable: Boolean,
    @SerialName("parameters") val parameters: CapabilityParameterObject,
    @SerialName("state") val state: CapabilityStateObjectData?
)

@Serializable
data class DeviceCapabilityObject(
    override val type: String,
    @SerialName("reportable") val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    override val lastUpdated: Float
) : Capability

@Serializable
data class DevicePropertyObject(
    override val type: String,
    @SerialName("reportable") val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: PropertyParameterObject,
    override val state: PropertyStateObject,
    override val lastUpdated: Float
) : Property

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html
 */
@Serializable
data class DeviceStateResponse(
    override val status: String,
    override val requestId: String,
    @SerialName("device") val device: DeviceStateObject
) : Response

@Serializable
data class DeviceStateObject(
    override val id: String,
    override val name: String,
    override val aliases: List<String>,
    override val type: DeviceType,
    @SerialName("state") val state: DeviceState,
    override val groups: List<String>,
    override val room: String?,
    override val externalId: String,
    override val skillId: String,
    override val capabilities: List<CapabilityObject>,
    override val properties: List<PropertyObject>
) : BaseDeviceObject

@Serializable
enum class DeviceState {
    @SerialName("online")
    ONLINE,
    @SerialName("offline")
    OFFLINE
}

sealed interface Capability{
    @SerialName("type")  val type: String
    @SerialName("retrievable") val retrievable: Boolean
    @SerialName("parameters") val parameters: CapabilityParameterObject
    @SerialName("state") val state: CapabilityStateObjectData?
    @SerialName("last_updated") val lastUpdated: Float
}

@Serializable
data class CapabilityObject(
    override val type: String,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    override val lastUpdated: Float
) : Capability

@Serializable
sealed interface Property{
    @SerialName("type")  val type: String
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
    override val lastUpdated: Float
) : Property

@Serializable
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
) {
    companion object {
        fun fromList(scenes: List<SceneObject>) = ColorScene(scenes.map { Scene(it) })
    }
}

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
data class OnOffCapabilityParameterObject(
    val split: Boolean
): CapabilityParameterObject()

@Serializable
data class ModeCapabilityParameterObject(
    val instance: ModeCapabilityInstance,
    val modes: List<ModeObject>
): CapabilityParameterObject() {
    companion object {
        fun fromList(instance: ModeCapabilityInstance, modes: List<ModeCapabilityMode>) =
            ModeCapabilityParameterObject(instance, modes.map { ModeObject(it) })
    }
}

@Serializable
data class ModeObject(
    val value: ModeCapabilityMode
)

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

@Serializable
data class RangeCapabilityParameterObject(
    val instance: RangeCapabilityParameterObjectFunction,
    var unit: RangeCapabilityParameterObjectUnit? = null,
    @SerialName("random_access") val randomAccess: Boolean,
    val range: Range? = null
): CapabilityParameterObject() {
    init {
        unit = when (instance) {
            RangeCapabilityParameterObjectFunction.BRIGHTNESS,
            RangeCapabilityParameterObjectFunction.HUMIDITY,
            RangeCapabilityParameterObjectFunction.OPEN -> RangeCapabilityParameterObjectUnit.PERCENT
            else -> {
                //RangeCapabilityInstanceOld.TEMPERATURE
                RangeCapabilityParameterObjectUnit.TEMPERATURE_CELSIUS
            }
        }
    }
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
enum class RangeCapabilityParameterObjectUnit : MeasurementUnit {
    @SerialName("unit.percent") PERCENT,
    @SerialName("unit.temperature.celsius") TEMPERATURE_CELSIUS
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
data class ToggleCapabilityParameterObject(
    val instance: ToggleCapabilityParameterObjectFunction
): CapabilityParameterObject()

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
data class VideoStreamCapabilityParameterObject(
    val protocols: List<VideoStreamCapabilityParameterObjectStreamProtocol>
): CapabilityParameterObject()

@Serializable
enum class VideoStreamCapabilityParameterObjectStreamProtocol {
    @SerialName("hls") HLS
}

@Serializable
sealed interface CapabilityState{
    val instance: CapabilityStateObjectInstance
}

@Serializable
sealed interface CapabilityStateObjectData: CapabilityState{
    override val instance: CapabilityStateObjectInstance
    val value: CapabilityStateObjectValue
}

@Serializable
sealed interface CapabilityStateObjectActionResult : CapabilityState{
    override val instance: CapabilityStateObjectInstance
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
sealed interface CapabilityStateObjectInstance

@Serializable
sealed interface CapabilityStateObjectValue

@Serializable
data class OnOffCapabilityStateObjectData(
    override val instance: OnOffCapabilityStateObjectInstance,
    override val value: OnOffCapabilityStateObjectValue
): CapabilityStateObjectData

@Serializable
enum class OnOffCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @SerialName("on") ON
}

@Serializable
data class OnOffCapabilityStateObjectValue(val value: Boolean) : CapabilityStateObjectValue

data class ColorSettingCapabilityStateObjectData(
    override val instance: ColorSettingCapabilityStateObjectInstance,
    override val value: ColorSettingCapabilityStateObjectValue
): CapabilityStateObjectData

@Serializable
enum class ColorSettingCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @SerialName("base") BASE,
    @SerialName("rgb") RGB,
    @SerialName("hsv") HSV,
    @SerialName("temperature_k") TEMPERATURE_K,
    @SerialName("scene") SCENE
}

@Serializable
sealed interface ColorSettingCapabilityStateObjectValue : CapabilityStateObjectValue

@Serializable
data class ColorSettingCapabilityStateObjectValueInteger(val value: Int) : ColorSettingCapabilityStateObjectValue

@Serializable
sealed interface ColorSettingCapabilityStateObjectValueObject : ColorSettingCapabilityStateObjectValue

@Serializable
data class ColorSettingCapabilityStateObjectValueObjectScene(val value: SceneObject)
    : ColorSettingCapabilityStateObjectValueObject

@Serializable
data class ColorSettingCapabilityStateObjectValueObjectHSV(val value: HSVObject)
    : ColorSettingCapabilityStateObjectValueObject

@Serializable
data class HSVObject(val h: Int, val s: Int, val v: Int)

@Serializable
data class VideoStreamCapabilityStateObjectData(
    override val instance: VideoStreamCapabilityStateObjectInstance,
    override val value: VideoStreamCapabilityStateObjectDataValue
): CapabilityStateObjectData

@Serializable
enum class VideoStreamCapabilityStateObjectInstance : CapabilityStateObjectInstance {
    @SerialName("get_stream") GET_STREAM
}

@Serializable
sealed interface VideoStreamCapabilityStateObjectValue : CapabilityStateObjectValue

@Serializable
data class VideoStreamCapabilityStateObjectDataValue(
    val protocols: List<VideoStreamCapabilityParameterObjectStreamProtocol>
) : VideoStreamCapabilityStateObjectValue

@Serializable
data class VideoStreamCapabilityStateObjectActionResultValue(
    val protocol: VideoStreamCapabilityParameterObjectStreamProtocol,
    @SerialName("stream_url") val streamUrl: String
) : VideoStreamCapabilityStateObjectValue

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
): CapabilityStateObjectData

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
): CapabilityStateObjectData

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
): CapabilityStateObjectData

@Serializable
data class ToggleCapabilityStateObjectDataValue(
    val value: Boolean
) : CapabilityStateObjectValue

@Serializable
data class ToggleCapabilityStateObjectActionResult(
    override val instance: ToggleCapabilityParameterObjectFunction,
    override val actionResult: ActionResult
): CapabilityStateObjectActionResult

@Serializable
sealed interface PropertyParameterObject {
    val instance: PropertyFunction
}

@Serializable
data class FloatPropertyParameterObject(
    override val instance: FloatFunctions,
    val unit: FloatPropertyParameterObjectUnit
) : PropertyParameterObject

@Serializable
sealed interface MeasurementUnit

@Serializable
enum class FloatPropertyParameterObjectUnit: MeasurementUnit {
    @SerialName("unit.ampere") AMPERE,
    @SerialName("unit.cubic_meter") CUBIC_METER,
    @SerialName("unit.gigacalorie") GIGACALORIE,
    @SerialName("unit.kilowatt_hour") KILOWATT_HOUR,
    @SerialName("unit.illumination.lux") LUX,
    @SerialName("unit.density.mcg_m3") MCG_M3,
    @SerialName("unit.percent") PERCENT,
    @SerialName("unit.ppm") PPM,
    @SerialName("unit.volt") VOLT,
    @SerialName("unit.watt") WATT
}

@Serializable
data class EventPropertyParameterObject(
    override val instance: EventFunctions,
    val events: List<EventObject>
) : PropertyParameterObject

@Serializable
sealed interface PropertyValue

@Serializable
sealed interface EventObjectValue : PropertyValue

@Serializable
data class EventObject(val value: EventObjectValue)

@Serializable
data class FloatObjectValue(val value: Float) : PropertyValue

@Serializable
enum class VibrationEventObjectValue : EventObjectValue {
    @SerialName("tilt") TILT,
    @SerialName("fall") FALL,
    @SerialName("vibration") VIBRATION
}

@Serializable
enum class OpenInstanceObjectValue : EventObjectValue {
    @SerialName("opened") OPENED,
    @SerialName("closed") CLOSED
}

@Serializable
enum class ButtonEventObjectValue : EventObjectValue {
    @SerialName("click") CLICK,
    @SerialName("double_click") DOUBLE_CLICK,
    @SerialName("long_press") LONG_PRESS
}

@Serializable
enum class MotionEventObjectValue : EventObjectValue {
    @SerialName("detected") DETECTED,
    @SerialName("not_detected") NOT_DETECTED
}

@Serializable
enum class SmokeEventObjectValue : EventObjectValue {
    @SerialName("detected") DETECTED,
    @SerialName("not_detected") NOT_DETECTED,
    @SerialName("high") HIGH
}

@Serializable
enum class GasEventObjectValue : EventObjectValue {
    @SerialName("detected") DETECTED,
    @SerialName("not_detected") NOT_DETECTED,
    @SerialName("high") HIGH
}

@Serializable
enum class BatteryLevelEventObjectValue : EventObjectValue {
    @SerialName("low") LOW,
    @SerialName("normal") NORMAL,
    @SerialName("high") HIGH
}

@Serializable
enum class FoodLevelEventObjectValue : EventObjectValue {
    @SerialName("empty") EMPTY,
    @SerialName("low") LOW,
    @SerialName("normal") NORMAL
}

@Serializable
enum class WaterLevelEventObjectValue : EventObjectValue {
    @SerialName("empty") EMPTY,
    @SerialName("low") LOW,
    @SerialName("normal") NORMAL
}

@Serializable
enum class WaterLeakEventObjectValue : EventObjectValue {
    @SerialName("dry") DRY,
    @SerialName("leak") LEAK
}

@Serializable
data class CustomEventObjectValue(val value: String) : EventObjectValue

@Serializable
sealed interface PropertyFunction

@Serializable
enum class FloatFunctions : PropertyFunction {
    @SerialName("amperage") AMPERAGE,
    @SerialName("battery_level") BATTERY_LEVEL,
    @SerialName("co2_level") CO2_LEVEL,
    @SerialName("electricity_meter") ELECTRICITY_METER,
    @SerialName("food_level") FOOD_LEVEL,
    @SerialName("gas_meter") GAS_METER,
    @SerialName("heat_meter") HEAT_METER,
    @SerialName("humidity") HUMIDITY,
    @SerialName("illumination") ILLUMINATION,
    @SerialName("meter") METER,
    @SerialName("pm10_density") PM10_DENSITY,
    @SerialName("pm1_density") PM1_DENSITY,
    @SerialName("pm2.5_density") PM2_5_DENSITY,
    @SerialName("power") POWER,
    @SerialName("pressure") PRESSURE,
    @SerialName("temperature") TEMPERATURE,
    @SerialName("tvoc") TVOC,
    @SerialName("voltage") VOLTAGE,
    @SerialName("water_level") WATER_LEVEL,
    @SerialName("water_meter") WATER_METER
}

@Serializable
sealed class EventFunctions : PropertyFunction {
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
}

@Serializable
enum class PropertyType {
    @SerialName("devices.properties.float")
    FLOAT,
    @SerialName("devices.properties.event")
    EVENT;
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