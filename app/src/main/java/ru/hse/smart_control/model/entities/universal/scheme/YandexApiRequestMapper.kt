package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull

class YandexApiRequestMapper {
    fun mapUserInfoToJson(userInfo: UserInfo): String {
        return Json.encodeToString(mapUserInfo(userInfo))
    }

    fun mapDeviceStateToJson(deviceObject: DeviceObject): String {
        return Json.encodeToString(mapDeviceState(deviceObject))
    }

    fun mapManageGroupCapabilitiesToJson(groupCapabilities: List<GroupCapabilityObject>): String {
        return Json.encodeToString(mapManageGroupCapabilities(groupCapabilities))
    }

    fun mapManageDeviceCapabilitiesToJson(deviceCapabilities: List<DeviceCapabilityObject>): String {
        return Json.encodeToString(mapManageDeviceCapabilities(deviceCapabilities))
    }

    private fun mapUserInfo(userInfo: UserInfo): Map<String, Any> {
        return mapOf(
            "rooms" to userInfo.rooms.map { mapRoom(it) },
            "groups" to userInfo.groups.map { mapGroup(it) },
            "devices" to userInfo.devices.map { mapDevice(it) },
            "scenarios" to userInfo.scenarios.map { mapScenario(it) },
            "households" to userInfo.households.map { mapHousehold(it) }
        )
    }

    private fun mapRoom(roomObject: RoomObject): Map<String, Any> {
        return mapOf(
            "id" to roomObject.id,
            "name" to roomObject.name,
            "devices" to roomObject.devices,
            "household_id" to roomObject.householdId
        )
    }

    private fun mapGroup(groupObject: GroupObject): Map<String, Any> {
        return mapOf(
            "id" to groupObject.id,
            "name" to groupObject.name,
            "aliases" to groupObject.aliases,
            "type" to groupObject.type.type.code(),
            "capabilities" to groupObject.capabilities.map { mapGroupCapability(it) },
            "devices" to groupObject.devices,
            "household_id" to groupObject.householdId
        )
    }

    private fun mapGroupCapability(groupCapabilityObject: GroupCapabilityObject): Map<String, Any?> {
        return mapOf(
            "type" to groupCapabilityObject.type.type.code(),
            "retrievable" to groupCapabilityObject.retrievable,
            "parameters" to mapCapabilityParameters(groupCapabilityObject.parameters),
            "state" to groupCapabilityObject.state?.let { mapCapabilityState(it) }
        )
    }

    private fun mapDevice(deviceObject: DeviceObject): Map<String, Any?> {
        return mapOf(
            "id" to deviceObject.id,
            "name" to deviceObject.name,
            "aliases" to deviceObject.aliases,
            "type" to deviceObject.type.type.code(),
            "external_id" to deviceObject.externalId,
            "skill_id" to deviceObject.skillId,
            "household_id" to deviceObject.householdId,
            "room" to deviceObject.room,
            "groups" to deviceObject.groups,
            "capabilities" to deviceObject.capabilities.map { mapDeviceCapability(it) },
            "properties" to deviceObject.properties.map { mapDeviceProperty(it) },
            "quasar_info" to deviceObject.quasarInfo?.let { mapQuasarInfo(it) }
        )
    }

    private fun mapDeviceCapability(deviceCapabilityObject: DeviceCapabilityObject): Map<String, Any?> {
        return mapOf(
            "type" to deviceCapabilityObject.type.type.code(),
            "reportable" to deviceCapabilityObject.reportable,
            "retrievable" to deviceCapabilityObject.retrievable,
            "parameters" to mapCapabilityParameters(deviceCapabilityObject.parameters),
            "state" to deviceCapabilityObject.state?.let { mapCapabilityState(it) },
            "last_updated" to deviceCapabilityObject.lastUpdated
        )
    }

    private fun mapDeviceProperty(devicePropertyObject: DevicePropertyObject): Map<String, Any?> {
        return mapOf(
            "type" to devicePropertyObject.type.type.code(),
            "reportable" to devicePropertyObject.reportable,
            "retrievable" to devicePropertyObject.retrievable,
            "parameters" to mapPropertyParameters(devicePropertyObject.parameters),
            "state" to devicePropertyObject.state?.let { mapPropertyState(it) },
            "last_updated" to devicePropertyObject.lastUpdated
        )
    }

    private fun mapQuasarInfo(quasarInfo: QuasarInfo): Map<String, String> {
        return mapOf(
            "device_id" to quasarInfo.deviceId,
            "platform" to quasarInfo.platform
        )
    }

    private fun mapScenario(scenarioObject: ScenarioObject): Map<String, Any> {
        return mapOf(
            "id" to scenarioObject.id,
            "name" to scenarioObject.name,
            "is_active" to scenarioObject.isActive
        )
    }

    private fun mapHousehold(householdObject: HouseholdObject): Map<String, String> {
        return mapOf(
            "id" to householdObject.id,
            "name" to householdObject.name
        )
    }

    private fun mapDeviceState(deviceObject: DeviceObject): Map<String, Any?> {
        return mapOf(
            "id" to deviceObject.id,
            "name" to deviceObject.name,
            "aliases" to deviceObject.aliases,
            "type" to deviceObject.type.type.code(),
            "external_id" to deviceObject.externalId,
            "skill_id" to deviceObject.skillId,
            "household_id" to deviceObject.householdId,
            "room" to deviceObject.room,
            "groups" to deviceObject.groups,
            "capabilities" to deviceObject.capabilities.map { mapDeviceCapability(it) },
            "properties" to deviceObject.properties.map { mapDeviceProperty(it) },
            "quasar_info" to deviceObject.quasarInfo?.let { mapQuasarInfo(it) }
        )
    }

    private fun mapManageGroupCapabilities(groupCapabilities: List<GroupCapabilityObject>): Map<String, List<Map<String, Any?>>> {
        return mapOf(
            "actions" to groupCapabilities.map { mapGroupCapability(it) }
        )
    }

    private fun mapManageDeviceCapabilities(deviceCapabilities: List<DeviceCapabilityObject>): Map<String, List<Map<String, Any?>>> {
        return mapOf(
            "devices" to listOf(
                mapOf(
                    "id" to deviceCapabilities[0].state?.instance?.let { it as RangeCapabilityWrapper }?.range?.knownOrNull()?.code,
                    "actions" to deviceCapabilities.map { mapDeviceCapability(it) }
                )
            )
        )
    }

    private fun mapCapabilityParameters(capabilityParameterObject: CapabilityParameterObject): Map<String, Any?> {
        return when (capabilityParameterObject) {
            is ColorSettingCapabilityParameterObject -> mapOf(
                "color_model" to capabilityParameterObject.colorModel?.colorModel?.knownOrNull()?.code,
                "temperature_k" to capabilityParameterObject.temperatureK?.let { mapTemperatureK(it) },
                "color_scene" to capabilityParameterObject.colorScene?.let { mapColorScene(it) }
            )
            is OnOffCapabilityParameterObject -> mapOf(
                "split" to capabilityParameterObject.split
            )
            is ModeCapabilityParameterObject -> mapOf(
                "instance" to capabilityParameterObject.instance.mode.knownOrNull()?.code,
                "modes" to capabilityParameterObject.modes.map { mapModeObject(it) }
            )
            is RangeCapabilityParameterObject -> mapOf(
                "instance" to capabilityParameterObject.instance.range.knownOrNull()?.code,
                "random_access" to capabilityParameterObject.randomAccess,
                "range" to capabilityParameterObject.range?.let { mapRange(it) },
                "looped" to capabilityParameterObject.looped
            )
            is ToggleCapabilityParameterObject -> mapOf(
                "instance" to capabilityParameterObject.instance.toggle.knownOrNull()?.code
            )
            is VideoStreamCapabilityParameterObject -> mapOf(
                "protocols" to capabilityParameterObject.protocols.map { it.streamProtocol.knownOrNull()?.code }
            )
        }
    }

    private fun mapTemperatureK(temperatureK: TemperatureK): Map<String, Int> {
        return mapOf(
            "min" to temperatureK.min,
            "max" to temperatureK.max
        )
    }

    private fun mapColorScene(colorScene: ColorScene): Map<String, List<Map<String, String>>> {
        return mapOf(
            "scenes" to colorScene.scenes.map { mapScene(it) }
        )
    }

    private fun mapScene(scene: Scene): Map<String, String> {
        val id = scene.id.scene.knownOrNull()?.code ?: ""
        return mapOf(
            "id" to id
        )
    }

    private fun mapModeObject(modeObject: ModeObject): Map<String, String> {
        val value = modeObject.value.mode.knownOrNull()?.code ?: ""
        return mapOf(
            "value" to value
        )
    }

    private fun mapRange(range: Range): Map<String, Float> {
        return mapOf(
            "min" to range.min,
            "max" to range.max,
            "precision" to range.precision
        )
    }

    private fun mapCapabilityState(capabilityStateObjectData: CapabilityStateObjectData): Map<String, Any?>? {
        return when (capabilityStateObjectData) {
            is OnOffCapabilityStateObjectData -> mapOf(
                "instance" to capabilityStateObjectData.instance.onOff.knownOrNull()?.code,
                "value" to capabilityStateObjectData.value.value
            )
            is ColorSettingCapabilityStateObjectData -> mapOf(
                "instance" to capabilityStateObjectData.instance.colorSetting.knownOrNull()?.code,
                "value" to when (val value = capabilityStateObjectData.value) {
                    is ColorSettingCapabilityStateObjectValueInteger -> value.value
                    is ColorSettingCapabilityStateObjectValueObjectScene -> mapOf(
                        "id" to value.value.scene.knownOrNull()?.code
                    )
                    is ColorSettingCapabilityStateObjectValueObjectHSV -> mapOf(
                        "h" to value.value.h,
                        "s" to value.value.s,
                        "v" to value.value.v
                    )
                    else -> null
                }
            )
            is RangeCapabilityStateObjectData -> mapOf(
                "instance" to capabilityStateObjectData.instance.range.knownOrNull()?.code,
                "value" to capabilityStateObjectData.value.value,
                "relative" to capabilityStateObjectData.relative
            )
            is ModeCapabilityStateObjectData -> mapOf(
                "instance" to capabilityStateObjectData.instance.mode.knownOrNull()?.code,
                "value" to capabilityStateObjectData.value.mode.knownOrNull()?.code
            )
            is ToggleCapabilityStateObjectData -> mapOf(
                "instance" to capabilityStateObjectData.instance.toggle.knownOrNull()?.code,
                "value" to capabilityStateObjectData.value.value
            )
            else -> null
        }
    }

    private fun mapPropertyParameters(propertyParameterObject: PropertyParameterObject): Map<String, Any?> {
        return when (propertyParameterObject) {
            is FloatPropertyParameterObject -> mapOf(
                "instance" to propertyParameterObject.instance.function.knownOrNull()?.code,
                "unit" to propertyParameterObject.unit.unit.knownOrNull()?.code
            )
            is EventPropertyParameterObject -> mapOf(
                "instance" to propertyParameterObject.instance.function.knownOrNull()?.code,
                "events" to propertyParameterObject.events.map { mapEventObject(it) }
            )
        }
    }

    private fun mapEventObject(eventObject: EventObject): Map<String, String> {
        val value = eventObject.value.value.knownOrNull()?.code ?: ""
        return mapOf(
            "value" to value
        )
    }

    private fun mapPropertyState(propertyStateObjectData: PropertyStateObjectData): Map<String, Any?> {
        return mapOf(
            "type" to propertyStateObjectData.type.type.knownOrNull()?.code,
            "state" to when (propertyStateObjectData) {
                is FloatPropertyStateObjectData -> mapFloatPropertyState(propertyStateObjectData.state)
                is EventPropertyStateObjectData -> mapEventPropertyState(propertyStateObjectData.state)
                else -> null
            }
        )
    }

    private fun mapFloatPropertyState(floatPropertyState: FloatPropertyState): Map<String, Any?> {
        return mapOf(
            "function" to floatPropertyState.propertyFunction.function.knownOrNull()?.code,
            "value" to floatPropertyState.propertyValue.value
        )
    }

    private fun mapEventPropertyState(eventPropertyState: EventPropertyState): Map<String, Any?> {
        return mapOf(
            "function" to eventPropertyState.propertyFunction.function.knownOrNull()?.code,
            "value" to eventPropertyState.propertyValue.value.value.knownOrNull()?.code
        )
    }
}