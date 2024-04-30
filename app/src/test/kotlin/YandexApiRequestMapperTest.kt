import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityType
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.DeviceTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.GroupCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.Range
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapability
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityWrapper
import ru.hse.smart_control.model.entities.universal.scheme.TemperatureK
import ru.hse.smart_control.model.entities.universal.scheme.TestConstants
import ru.hse.smart_control.model.entities.universal.scheme.UserInfo
import ru.hse.smart_control.model.entities.universal.scheme.YandexApiRequestMapper
import ru.hse.smart_control.model.entities.universal.scheme.YandexApiResponseMapper

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
                            instance = OnOffCapabilityStateObjectInstanceWrapper(OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
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
                            instance = ColorSettingCapabilityStateObjectInstanceWrapper(ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
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