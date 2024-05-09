package ru.hse.smart_control.model.entities.universal.scheme.common.smart_home

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.serializer.codifiedEnumSerializer
import ru.hse.smart_control.model.entities.universal.scheme.common.TypeConnectionParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.Capability
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityActionResultObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.GroupCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.DevicePropertyObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.MeasurementUnit
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.property.Property

@Serializable
data class SmartHomeInfo(
    @SerialName("rooms") val rooms: List<RoomObject>,
    @SerialName("groups") val groups: List<GroupObject>,
    @SerialName("devices") val devices: List<DeviceObject>,
    @SerialName("scenarios") val scenarios: List<ScenarioObject>,
    @SerialName("households") val households: List<HouseholdObject>
) : TypeConnectionParameterObject

@Serializable
data class MeasurementUnitWrapper(
    @Serializable(with = MeasurementUnit.CodifiedSerializer::class)
    val unit: CodifiedEnum<MeasurementUnit, String>
)

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
    @SerialName("type") val type: DeviceTypeWrapper,
    @SerialName("capabilities") val capabilities: List<GroupCapabilityObject>,
    @SerialName("devices") val devices: List<String>,
    @SerialName("household_id") val householdId: String
)

@Serializable
sealed interface BaseDeviceObject {
    @SerialName("id") val id: String
    @SerialName("name") val name: String
    @SerialName("aliases") val aliases: List<String>?
    @SerialName("type") val type: DeviceTypeWrapper
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
data class DeviceTypeWrapper(
    @Serializable(with = DeviceType.CodifiedSerializer::class)
    val type: CodifiedEnum<DeviceType, String>
)

@Serializable
data class DeviceObject(
    override val id: String,
    override val name: String,
    override val aliases: List<String>,
    override val type: DeviceTypeWrapper,
    @SerialName("external_id") override val externalId: String,
    @SerialName("skill_id") override val skillId: String,
    @SerialName("household_id") val householdId: String,
    @SerialName("room") override val room: String? = null,
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
    val type: DeviceTypeWrapper
)