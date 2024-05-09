import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.smart_control.model.entities.universal.scheme.TestConstants

import ru.hse.smart_control.model.entities.universal.scheme.YandexApiRequestMapper
import ru.hse.smart_control.model.entities.universal.scheme.YandexApiResponseMapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.GroupCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapability
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.RangeCapabilityWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.TemperatureK

class YandexApiRequestMapperTest {

    private val requestMapper = YandexApiRequestMapper()
    private val responseMapper = YandexApiResponseMapper()

    private val json = Json {
        prettyPrint = true
    }

    @Test
    fun `test mapManageDeviceCapabilitiesStateRequest`() {
        val deviceObjects = listOf(
            DeviceObject(
                id = "lamp-id-1",
                name = "",
                aliases = emptyList(),
                type = DeviceTypeWrapper(DeviceType.SOCKET.codifiedEnum()),
                externalId = "",
                skillId = "",
                householdId = "",
                room = "",
                groups = emptyList(),
                capabilities = listOf(
                    DeviceCapabilityObject(
                        type = CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()),
                        reportable = true,
                        retrievable = true,
                        parameters = OnOffCapabilityParameterObject(split = false),
                        state = OnOffCapabilityStateObjectData(
                            instance = OnOffCapabilityStateObjectInstanceWrapper(
                                OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                            value = OnOffCapabilityStateObjectValue(true)
                        ),
                        lastUpdated = 0f
                    ),
                    DeviceCapabilityObject(
                        type = CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()),
                        reportable = true,
                        retrievable = true,
                        parameters = RangeCapabilityParameterObject(
                            instance = RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
                            randomAccess = true
                        ),
                        state = RangeCapabilityStateObjectData(
                            instance = RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
                            value = RangeCapabilityStateObjectDataValue(50.0f)
                        ),
                        lastUpdated = 0f
                    ),
                    DeviceCapabilityObject(
                        type = CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()),
                        reportable = true,
                        retrievable = true,
                        parameters = ColorSettingCapabilityParameterObject(
                            temperatureK = TemperatureK(4000, 4000)
                        ),
                        state = ColorSettingCapabilityStateObjectData(
                            instance = ColorSettingCapabilityStateObjectInstanceWrapper(
                                ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
                            value = ColorSettingCapabilityStateObjectValueInteger(4000)
                        ),
                        lastUpdated = 0f
                    )
                ),
                properties = emptyList()
            )
        )

        val requestObject = requestMapper.mapToManageDeviceCapabilitiesStateRequest(deviceObjects)
        val serializedRequest = json.encodeToString(requestObject)

        assertEquals(TestConstants.requestManageDeviceCapabilitiesJson, serializedRequest)
    }

    @Test
    fun `test mapManageGroupCapabilitiesStateRequest`() {
        val groupCapabilityObjects = listOf(
            GroupCapabilityObject(
                type = CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()),
                retrievable = true,
                parameters = OnOffCapabilityParameterObject(split = false),
                state = OnOffCapabilityStateObjectData(
                    instance = OnOffCapabilityStateObjectInstanceWrapper(OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                    value = OnOffCapabilityStateObjectValue(true)
                )
            )
        )

        val requestObject = requestMapper.mapToManageGroupCapabilitiesStateRequest(groupCapabilityObjects)
        val serializedRequest = json.encodeToString(requestObject)

        assertEquals(TestConstants.requestManageGroupCapabilitiesJson, serializedRequest)
    }
}