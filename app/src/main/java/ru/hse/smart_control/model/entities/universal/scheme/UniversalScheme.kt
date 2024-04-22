package ru.hse.smart_control.model.entities.universal.scheme

import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.serializer.codifiedEnumSerializer

@Serializable
data class UniversalScheme(val objects: List<UniversalSchemeObject>)

@Serializable
sealed interface UniversalSchemeObject {
    val type: TypeDeviceConnection
    val payload: TypeConnectionParameterObject
}

@Serializable
data class SmartHomeTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.SMART_HOME,
    override val payload: UserInfo,
    var smartHomeApiUrl: String
) : UniversalSchemeObject

@Serializable
data class UserInfo(
    @SerialName("rooms") val rooms: List<RoomObject>,
    @SerialName("groups") val groups: List<GroupObject>,
    @SerialName("devices") val devices: List<DeviceObject>,
    @SerialName("scenarios") val scenarios: List<ScenarioObject>,
    @SerialName("households") val households: List<HouseholdObject>
) : TypeConnectionParameterObject

@Serializable
data class RosTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.ROS,
    override val payload: RosTypeConnectionParameterObject
) : UniversalSchemeObject

@Serializable
sealed interface TypeConnectionParameterObject

@Serializable
data class RosTypeConnectionParameterObject(
    @SerialName("config_entity")
    val configEntity: ConfigEntity,
    @SerialName("master_entity")
    val masterEntity: MasterEntity,
    @SerialName("ssh_entity")
    val sshEntity: SSHEntity,
    @SerialName("ui_widgets")
    val uiWidgets: List<BaseEntity>
): TypeConnectionParameterObject

@Serializable
enum class TypeDeviceConnection(val typeProtocol: TypeProtocol){
    ROS(TypeProtocol.INTERNET),
    SMART_HOME(TypeProtocol.INTERNET),
    ARDUINO_BT(TypeProtocol.BLUETOOTH)
}

@Serializable
enum class TypeProtocol{
    BLUETOOTH,
    BLE,
    INTERNET,
    USB
}

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

enum class DeviceType(override val code: String) : Codified<String> {
    YANDEX_SMART_SPEAKER("devices.types.smart_speaker.yandex.station.micro"),
    LIGHT("devices.types.light"),
    SOCKET("devices.types.socket"),
    SWITCH("devices.types.switch"),
    THERMOSTAT("devices.types.thermostat"),
    THERMOSTAT_AC("devices.types.thermostat.ac"),
    MEDIA_DEVICE("devices.types.media_device"),
    MEDIA_DEVICE_TV("devices.types.media_device.tv"),
    MEDIA_DEVICE_TV_BOX("devices.types.media_device.tv_box"),
    MEDIA_DEVICE_RECEIVER("devices.types.media_device.receiver"),
    COOKING("devices.types.cooking"),
    COFFEE_MAKER("devices.types.cooking.coffee_maker"),
    KETTLE("devices.types.cooking.kettle"),
    MULTICOOKER("devices.types.cooking.multicooker"),
    OPENABLE("devices.types.openable"),
    OPENABLE_CURTAIN("devices.types.openable.curtain"),
    HUMIDIFIER("devices.types.humidifier"),
    PURIFIER("devices.types.purifier"),
    VACUUM_CLEANER("devices.types.vacuum_cleaner"),
    WASHING_MACHINE("devices.types.washing_machine"),
    DISHWASHER("devices.types.dishwasher"),
    IRON("devices.types.iron"),
    SENSOR("devices.types.sensor"),
    SENSOR_MOTION("devices.types.sensor.motion"),
    SENSOR_DOOR("devices.types.sensor.door"),
    SENSOR_WINDOW("devices.types.sensor.window"),
    SENSOR_WATER_LEAK("devices.types.sensor.water_leak"),
    SENSOR_SMOKE("devices.types.sensor.smoke"),
    SENSOR_GAS("devices.types.sensor.gas"),
    SENSOR_VIBRATION("devices.types.sensor.vibration"),
    SENSOR_BUTTON("devices.types.sensor.button"),
    SENSOR_ILLUMINATION("devices.types.sensor.illumination"),
    OTHER("devices.types.other");
    object CodifiedSerializer : KSerializer<CodifiedEnum<DeviceType, String>> by codifiedEnumSerializer()
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

@Serializable
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
    override val state: PropertyStateObjectData?,
    @SerialName("last_updated") override val lastUpdated: Float,
) : Property

@Serializable
data class QuasarInfo(
    @SerialName("device_id") val deviceId: String,
    @SerialName("platform") val platform: String
)

enum class DeviceState(override val code: String) : Codified<String> {
    ONLINE("online"),
    OFFLINE("offline"),
    NOT_FOUND("not_found"),
    SPLIT("split");
    object CodifiedSerializer : KSerializer<CodifiedEnum<DeviceState, String>> by codifiedEnumSerializer()
}

enum class CapabilityType(override val code: String) : Codified<String> {
    COLOR_SETTING("devices.capabilities.color_setting"),
    ON_OFF("devices.capabilities.on_off"),
    RANGE("devices.capabilities.range"),
    MODE("devices.capabilities.mode"),
    TOGGLE("devices.capabilities.toggle"),
    VIDEO_STREAM("devices.capabilities.video_stream");
    object CodifiedSerializer : KSerializer<CodifiedEnum<CapabilityType, String>> by codifiedEnumSerializer()
}

sealed interface Capability{
    @SerialName("type")  val type: CapabilityType
    @SerialName("retrievable") val retrievable: Boolean?
    @SerialName("parameters") val parameters: CapabilityParameterObject?
    @SerialName("state") val state: CapabilityStateObjectData?
    @SerialName("last_updated") val lastUpdated: Float?
}

@Serializable
data class DeviceCapabilityObject(
    override val type: CapabilityType,
    val reportable: Boolean,
    override val retrievable: Boolean,
    override val parameters: CapabilityParameterObject,
    override val state: CapabilityStateObjectData?,
    override val lastUpdated: Float
) : Capability

@Serializable
data class CapabilityObject(
    override val type: CapabilityType,
    override val retrievable: Boolean? = null,
    override val parameters: CapabilityParameterObject? = null,
    override val state: CapabilityStateObjectData?,
    @SerialName("last_updated") override val lastUpdated: Float? = null
) : Capability

@Serializable
data class CapabilityActionResultObject(
    @SerialName("type") val type: CapabilityType,
    @SerialName("state") val state: StateResultObject
)

@Serializable
sealed interface Property{
    @SerialName("type") val type: String
    @SerialName("retrievable") val retrievable: Boolean
    @SerialName("parameters") val parameters: PropertyParameterObject
    @SerialName("state") val state: PropertyStateObjectData?
    @SerialName("last_updated") val lastUpdated: Float
}

@Serializable
data class PropertyObject(
    override val type: String,
    override val retrievable: Boolean,
    override val parameters: PropertyParameterObject,
    override val state: PropertyStateObjectData?,
    @SerialName("last_updated") override val lastUpdated: Float
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

enum class ColorModel(override val code: String) : Codified<String> {
    RGB("rgb"),
    HSV("hsv");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ColorModel, String>> by codifiedEnumSerializer()
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

enum class SceneObject(override val code: String) : Codified<String> {
    ALARM("alarm"),
    ALICE("alice"),
    CANDLE("candle"),
    DINNER("dinner"),
    FANTASY("fantasy"),
    GARLAND("garland"),
    JUNGLE("jungle"),
    MOVIE("movie"),
    NEON("neon"),
    NIGHT("night"),
    OCEAN("ocean"),
    PARTY("party"),
    READING("reading"),
    REST("rest"),
    ROMANCE("romance"),
    SIREN("siren"),
    SUNRISE("sunrise"),
    SUNSET("sunset");
    object CodifiedSerializer : KSerializer<CodifiedEnum<SceneObject, String>> by codifiedEnumSerializer()
}

@Serializable
data class ModeObject(val value: ModeCapabilityMode)

enum class ModeCapabilityMode(override val code: String) : Codified<String>, CapabilityStateObjectValue {
    AUTO("auto"),
    ECO("eco"),
    SMART("smart"),
    TURBO("turbo"),
    COOL("cool"),
    DRY("dry"),
    FAN_ONLY("fan_only"),
    HEAT("heat"),
    PREHEAT("preheat"),
    HIGH("high"),
    LOW("low"),
    MEDIUM("medium"),
    MAX("max"),
    MIN("min"),
    FAST("fast"),
    SLOW("slow"),
    EXPRESS("express"),
    NORMAL("normal"),
    QUIET("quiet"),
    HORIZONTAL("horizontal"),
    STATIONARY("stationary"),
    VERTICAL("vertical"),
    ONE("one"),
    TWO("two"),
    THREE("three"),
    FOUR("four"),
    FIVE("five"),
    SIX("six"),
    SEVEN("seven"),
    EIGHT("eight"),
    NINE("nine"),
    TEN("ten"),
    AMERICANO("americano"),
    CAPPUCCINO("cappuccino"),
    DOUBLE("double"),
    ESPRESSO("espresso"),
    DOUBLE_ESPRESSO("double_espresso"),
    LATTE("latte"),
    BLACK_TEA("black_tea"),
    FLOWER_TEA("flower_tea"),
    GREEN_TEA("green_tea"),
    HERBAL_TEA("herbal_tea"),
    OOLONG_TEA("oolong_tea"),
    PUERH_TEA("puerh_tea"),
    RED_TEA("red_tea"),
    WHITE_TEA("white_tea"),
    GLASS("glass"),
    INTENSIVE("intensive"),
    PRE_RINSE("pre_rinse"),
    ASPIC("aspic"),
    BABY_FOOD("baby_food"),
    BAKING("baking"),
    BREAD("bread"),
    BOILING("boiling"),
    CEREALS("cereals"),
    CHEESECAKE("cheesecake"),
    DEEP_FRYER("deep_fryer"),
    DESSERT("dessert"),
    FOWL("fowl"),
    FRYING("frying"),
    MACARONI("macaroni"),
    MILK_PORRIDGE("milk_porridge"),
    MULTICOOKER("multicooker"),
    PASTA("pasta"),
    PILAF("pilaf"),
    PIZZA("pizza"),
    SAUCE("sauce"),
    SLOW_COOK("slow_cook"),
    SOUP("soup"),
    STEAM("steam"),
    STEWING("stewing"),
    VACUUM("vacuum"),
    YOGURT("yogurt");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ModeCapabilityMode, String>> by codifiedEnumSerializer()
}

enum class ModeCapabilityInstance(override val code: String) : Codified<String>, CapabilityStateObjectInstance {
    CLEANUP_MODE("cleanup_mode"),
    COFFEE_MODE("coffee_mode"),
    DISHWASHING("dishwashing"),
    FAN_SPEED("fan_speed"),
    HEAT("heat"),
    INPUT_SOURCE("input_source"),
    PROGRAM("program"),
    SWING("swing"),
    TEA_MODE("tea_mode"),
    THERMOSTAT("thermostat"),
    WORK_SPEED("work_speed");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ModeCapabilityInstance, String>> by codifiedEnumSerializer()
}

enum class RangeCapabilityParameterObjectFunction(override val code: String) : Codified<String>, CapabilityStateObjectInstance {
    BRIGHTNESS("brightness"),
    CHANNEL("channel"),
    HUMIDITY("humidity"),
    OPEN("open"),
    TEMPERATURE("temperature"),
    VOLUME("volume");
    object CodifiedSerializer : KSerializer<CodifiedEnum<RangeCapabilityParameterObjectFunction, String>> by codifiedEnumSerializer()
}

@Serializable
data class Range(
    val min: Float,
    val max: Float,
    val precision: Float
) {
    override fun toString() = "[$min, $max]"
}

enum class ToggleCapabilityParameterObjectFunction(override val code: String) : Codified<String>, CapabilityStateObjectInstance {
    BACKLIGHT("backlight"),
    CONTROLS_LOCKED("controls_locked"),
    IONIZATION("ionization"),
    KEEP_WARM("keep_warm"),
    MUTE("mute"),
    OSCILLATION("oscillation"),
    PAUSE("pause");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ToggleCapabilityParameterObjectFunction, String>> by codifiedEnumSerializer()
}

enum class VideoStreamCapabilityParameterObjectStreamProtocol(override val code: String) : Codified<String> {
    HLS("hls"),
    RTMP("rtmp");
    object CodifiedSerializer : KSerializer<CodifiedEnum<VideoStreamCapabilityParameterObjectStreamProtocol, String>> by codifiedEnumSerializer()
}

@Serializable
sealed interface CapabilityState {
    val instance: CapabilityStateObjectInstance
}

@Serializable
data class StateResultObject(
    override val instance: CapabilityStateObjectInstance,
    @SerialName("action_result") val actionResult: ActionResult
) : CapabilityState

@Serializable
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

enum class ErrorCode(override val code: String) : Codified<String> {
    DOOR_OPEN("DOOR_OPEN"),
    LID_OPEN("LID_OPEN"),
    REMOTE_CONTROL_DISABLED("REMOTE_CONTROL_DISABLED"),
    NOT_ENOUGH_WATER("NOT_ENOUGH_WATER"),
    LOW_CHARGE_LEVEL("LOW_CHARGE_LEVEL"),
    CONTAINER_FULL("CONTAINER_FULL"),
    CONTAINER_EMPTY("CONTAINER_EMPTY"),
    DRIP_TRAY_FULL("DRIP_TRAY_FULL"),
    DEVICE_STUCK("DEVICE_STUCK"),
    DEVICE_OFF("DEVICE_OFF"),
    FIRMWARE_OUT_OF_DATE("FIRMWARE_OUT_OF_DATE"),
    NOT_ENOUGH_DETERGENT("NOT_ENOUGH_DETERGENT"),
    HUMAN_INVOLVEMENT_NEEDED("HUMAN_INVOLVEMENT_NEEDED"),
    DEVICE_UNREACHABLE("DEVICE_UNREACHABLE"),
    DEVICE_BUSY("DEVICE_BUSY"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    INVALID_ACTION("INVALID_ACTION"),
    INVALID_VALUE("INVALID_VALUE"),
    NOT_SUPPORTED_IN_CURRENT_MODE("NOT_SUPPORTED_IN_CURRENT_MODE"),
    ACCOUNT_LINKING_ERROR("ACCOUNT_LINKING_ERROR"),
    DEVICE_NOT_FOUND("DEVICE_NOT_FOUND");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ErrorCode, String>> by codifiedEnumSerializer()
}

enum class Status(override val code: String) : Codified<String> {
    DONE("DONE"),
    ERROR("ERROR");
    object CodifiedSerializer : KSerializer<CodifiedEnum<Status, String>> by codifiedEnumSerializer()
}

@Serializable
sealed interface CapabilityStateObjectInstance

@Serializable
sealed interface CapabilityStateObjectValue

@Serializable
data class OnOffCapabilityStateObjectData(
    override val instance: OnOffCapabilityStateObjectInstance,
    override val value: OnOffCapabilityStateObjectValue
): CapabilityStateObjectData()

@Serializable
data class OnOffCapabilityStateObjectValue(val value: Boolean) : CapabilityStateObjectValue

@Serializable
enum class OnOffCapabilityStateObjectInstance(override val code: String) : Codified<String>, CapabilityStateObjectInstance {
    ON("on");
    object CodifiedSerializer : KSerializer<CodifiedEnum<OnOffCapabilityStateObjectInstance, String>> by codifiedEnumSerializer()
}

@Serializable
data class ColorSettingCapabilityStateObjectData(
    override val instance: ColorSettingCapabilityStateObjectInstance,
    override val value: ColorSettingCapabilityStateObjectValue
): CapabilityStateObjectData()

@Serializable
enum class ColorSettingCapabilityStateObjectInstance(override val code: String) : Codified<String>, CapabilityStateObjectInstance {
    BASE("base"),
    RGB("rgb"),
    HSV("hsv"),
    TEMPERATURE_K("temperature_k"),
    SCENE("scene");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ColorSettingCapabilityStateObjectInstance, String>> by codifiedEnumSerializer()
}

@Serializable
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

@Serializable
sealed class PropertyParameterObject {
    abstract val instance: PropertyFunction
}

@Serializable
@SerialName("float")
data class FloatPropertyParameterObject(
    override val instance: PropertyFunction,
    val unit: MeasurementUnit
) : PropertyParameterObject()

enum class MeasurementUnit(override val code: String) : Codified<String> {
    TEMPERATURE_CELSIUS("unit.temperature.celsius"),
    AMPERE("unit.ampere"),
    CUBIC_METER("unit.cubic_meter"),
    GIGACALORIE("unit.gigacalorie"),
    KILOWATT_HOUR("unit.kilowatt_hour"),
    LUX("unit.illumination.lux"),
    MCG_M3("unit.density.mcg_m3"),
    PERCENT("unit.percent"),
    PPM("unit.ppm"),
    VOLT("unit.volt"),
    WATT("unit.watt");
    object CodifiedSerializer : KSerializer<CodifiedEnum<MeasurementUnit, String>> by codifiedEnumSerializer()
}

@Serializable
@SerialName("event")
data class EventPropertyParameterObject(
    override val instance: PropertyFunction,
    val events: List<EventObject>
) : PropertyParameterObject()

@Serializable
sealed interface PropertyValue

enum class EventObjectValue(override val code: String) : Codified<String> {
    SPEECH_FINISHED("speech_finished"),
    TILT("tilt"),
    FALL("fall"),
    VIBRATION("vibration"),
    OPENED("opened"),
    CLOSED("closed"),
    CLICK("click"),
    DOUBLE_CLICK("double_click"),
    LONG_PRESS("long_press"),
    DETECTED("detected"),
    NOT_DETECTED("not_detected"),
    HIGH("high"),
    LOW("low"),
    NORMAL("normal"),
    EMPTY("empty"),
    DRY("dry"),
    LEAK("leak");

    object CodifiedSerializer : KSerializer<CodifiedEnum<EventObjectValue, String>> by codifiedEnumSerializer()
}

@Serializable
data class EventObject(val value: EventObjectValue): PropertyValue

@Serializable
data class FloatObjectValue(val value: Float) : PropertyValue

enum class PropertyFunction(override val code: String) : Codified<String> {
    VOICE_ACTIVITY("voice_activity"),
    AMPERAGE("amperage"),
    BATTERY_LEVEL("battery_level"),
    CO2_LEVEL("co2_level"),
    ELECTRICITY_METER("electricity_meter"),
    FOOD_LEVEL("food_level"),
    GAS_METER("gas_meter"),
    HEAT_METER("heat_meter"),
    HUMIDITY("humidity"),
    ILLUMINATION("illumination"),
    METER("meter"),
    PM10_DENSITY("pm10_density"),
    PM1_DENSITY("pm1_density"),
    PM2_5_DENSITY("pm2.5_density"),
    POWER("power"),
    PRESSURE("pressure"),
    TEMPERATURE("temperature"),
    TVOC("tvoc"),
    VOLTAGE("voltage"),
    WATER_LEVEL("water_level"),
    WATER_METER("water_meter"),
    VIBRATION("vibration"),
    OPEN("open"),
    BUTTON("button"),
    MOTION("motion"),
    SMOKE("smoke"),
    GAS("gas"),
    WATER_LEAK("water_leak"),
    CUSTOM("custom");
    object CodifiedSerializer : KSerializer<CodifiedEnum<PropertyFunction, String>> by codifiedEnumSerializer()
}

enum class PropertyType(override val code: String) : Codified<String> {
    FLOAT("devices.properties.float"),
    EVENT("devices.properties.event");
    object CodifiedSerializer : KSerializer<CodifiedEnum<PropertyType, String>> by codifiedEnumSerializer()
}

@Serializable
sealed interface PropertyStateObjectData{
    val type: PropertyType
    val state: PropertyState
}

@Serializable
sealed interface PropertyState{
    val propertyFunction: PropertyFunction
    val propertyValue: PropertyValue
}

@Serializable
data class FloatPropertyStateObjectData(
    override val type: PropertyType = PropertyType.FLOAT,
    override val state: FloatPropertyState
) : PropertyStateObjectData

@Serializable
data class EventPropertyStateObjectData(
    override val type: PropertyType = PropertyType.EVENT,
    override val state: EventPropertyState
) : PropertyStateObjectData

@Serializable
data class FloatPropertyState(
    override val propertyFunction: PropertyFunction,
    override val propertyValue: FloatObjectValue
) : PropertyState

@Serializable
data class EventPropertyState(
    override val propertyFunction: PropertyFunction,
    override val propertyValue: EventObject
) : PropertyState

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