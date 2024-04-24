import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityType
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.DeviceCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.DeviceObject
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

class YandexApiRequestMapperTest {

    private val mapper = YandexApiRequestMapper()

    @Test
    fun `test mapUserInfoToJson`() {
        val userInfo = Json.decodeFromString<UserInfo>(TestConstants.responseUserInfoJson)
        val result = mapper.mapUserInfoToJson(userInfo)
        assertEquals(TestConstants.responseUserInfoJson, result)
    }

    @Test
    fun `test mapDeviceStateToJson`() {
        val deviceObject = Json.decodeFromString<DeviceObject>(TestConstants.responseDeviceStateJson)
        val result = mapper.mapDeviceStateToJson(deviceObject)
        assertEquals(TestConstants.responseDeviceStateJson, result)
    }

    @Test
    fun `test mapManageGroupCapabilitiesToJson`() {
        val groupCapabilities = listOf(
            GroupCapabilityObject(
                type = CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()),
                retrievable = true,
                parameters = OnOffCapabilityParameterObject(split = false),
                state = OnOffCapabilityStateObjectData(
                    instance = OnOffCapabilityStateObjectInstanceWrapper(
                        OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                    value = OnOffCapabilityStateObjectValue(true)
                )
            )
        )
        val result = mapper.mapManageGroupCapabilitiesToJson(groupCapabilities)
        assertEquals(TestConstants.requestManageGroupCapabilitiesJson, result)
    }

    @Test
    fun `test mapManageDeviceCapabilitiesToJson`() {
        val deviceCapabilities = listOf(
            DeviceCapabilityObject(
                type = CapabilityTypeWrapper(CapabilityType.ON_OFF.codifiedEnum()),
                reportable = true,
                retrievable = true,
                parameters = OnOffCapabilityParameterObject(split = false),
                state = OnOffCapabilityStateObjectData(
                    instance = OnOffCapabilityStateObjectInstanceWrapper(OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                    value = OnOffCapabilityStateObjectValue(true)
                ),
                lastUpdated = 0.0f
            ),
            DeviceCapabilityObject(
                type = CapabilityTypeWrapper(CapabilityType.RANGE.codifiedEnum()),
                reportable = true,
                retrievable = true,
                parameters = RangeCapabilityParameterObject(
                    instance = RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
                    randomAccess = true,
                    range = Range(1.0f, 100.0f, 1.0f),
                    looped = false
                ),
                state = RangeCapabilityStateObjectData(
                    instance = RangeCapabilityWrapper(RangeCapability.BRIGHTNESS.codifiedEnum()),
                    value = RangeCapabilityStateObjectDataValue(50.0f)
                ),
                lastUpdated = 0.0f
            ),
            DeviceCapabilityObject(
                type = CapabilityTypeWrapper(CapabilityType.COLOR_SETTING.codifiedEnum()),
                reportable = true,
                retrievable = true,
                parameters = ColorSettingCapabilityParameterObject(
                    temperatureK = TemperatureK(2700, 6500)
                ),
                state = ColorSettingCapabilityStateObjectData(
                    instance = ColorSettingCapabilityStateObjectInstanceWrapper(
                        ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
                    value = ColorSettingCapabilityStateObjectValueInteger(4000)
                ),
                lastUpdated = 0.0f
            )
        )
        val result = mapper.mapManageDeviceCapabilitiesToJson(deviceCapabilities)
        assertEquals(TestConstants.requestManageDeviceCapabilitiesJson, result)
    }
}