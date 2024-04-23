import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateRequest
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageGroupCapabilitiesStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityType
import ru.hse.smart_control.model.entities.universal.scheme.ColorModel
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.DeviceTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.TestConstants
import ru.hse.smart_control.model.entities.universal.scheme.YandexApiResponseMapper
import kotlin.test.Test
import kotlin.test.assertEquals

class YandexApiResponseMapperTest {

    private val mapper = YandexApiResponseMapper()

    @Test
    fun `test mapUserInfoResponse`() {
        val response = Json.decodeFromString<YandexUserInfoResponse>(TestConstants.responseUserInfoJson)
        val result = mapper.mapUserInfoResponse(response)

        assertEquals(4, result.rooms.size)
        assertEquals("ca82a680-0317-4bec-b92e-5c3dd27c61eb", result.rooms[0].id)
        assertEquals("Балкон", result.rooms[0].name)
        assertEquals(listOf("51e797a4-93cf-4bc4-832e-698b6703467c"), result.rooms[0].devices)
        assertEquals("c9a8269c-9939-429b-bb56-05f5abae2937", result.rooms[0].householdId)

        assertEquals(1, result.groups.size)
        assertEquals("d7eded8d-bdb4-4541-beba-7bbf88fea853", result.groups[0].id)
        assertEquals("Освещение", result.groups[0].name)
        assertEquals(emptyList(), result.groups[0].aliases)
        assertEquals("devices.types.light", result.groups[0].type.type.code())
        assertEquals(listOf("d7e57431-7953-49aa-b46e-589495b71986"), result.groups[0].devices)
        assertEquals("f80b6641-8880-49d5-be31-1b35745c321a", result.groups[0].householdId)
        assertEquals(2, result.groups[0].capabilities.size)
        assertEquals(CapabilityType.COLOR_SETTING.codifiedEnum(), result.groups[0].capabilities[0].type.type)
        assertEquals(ColorSettingCapabilityStateObjectInstanceWrapper(ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()), result.groups[0].capabilities[0].state?.instance)
        assertEquals(6500, (result.groups[0].capabilities[0].state?.value as ColorSettingCapabilityStateObjectValueInteger).value)

        assertEquals(3, result.devices.size)
        assertEquals("4a7a2b29-3788-4e09-b5ef-387447185c96", result.devices[0].id)
        assertEquals("Яндекс Лайт", result.devices[0].name)
        assertEquals(DeviceTypeWrapper(DeviceType.YANDEX_SMART_SPEAKER.codifiedEnum()), result.devices[0].type)
        assertEquals("L00BN1200M42ZY.yandexmicro", result.devices[0].externalId)
        assertEquals("Q", result.devices[0].skillId)
        assertEquals("f80b6641-8880-49d5-be31-1b35745c321a", result.devices[0].householdId)
        assertEquals(null, result.devices[0].room)
        assertEquals(emptyList(), result.devices[0].groups)
        assertEquals(emptyList(), result.devices[0].capabilities)
        assertEquals(1, result.devices[0].properties.size)

        assertEquals(0, result.scenarios.size)

        assertEquals(2, result.households.size)
        assertEquals("c9a8269c-9939-429b-bb56-05f5abae2937", result.households[0].id)
        assertEquals("Мой дом", result.households[0].name)
//        assertEquals("households.types.personal", result.households[0].type)
    }

    @Test
    fun `test mapDeviceStateResponse`() {
        val response = Json.decodeFromString<YandexDeviceStateResponse>(TestConstants.responseDeviceStateJson)
        val result = mapper.mapDeviceStateResponse(response)

        assertEquals("51e797a4-93cf-4bc4-832e-698b6703467c", result.id)
        assertEquals("Лампа", result.name)
        assertEquals(emptyList(), result.aliases)
        assertEquals(DeviceTypeWrapper(DeviceType.LIGHT.codifiedEnum()), result.type)
        assertEquals("bf9159632e4fb1987bi7am", result.externalId)
        assertEquals("35e2897a-c583-495a-9e33-f5d6f0f4cb49", result.skillId)
        assertEquals("", result.householdId)
        assertEquals("ca82a680-0317-4bec-b92e-5c3dd27c61eb", result.room)
        assertEquals(emptyList(), result.groups)

        assertEquals(3, result.capabilities.size)
        assertEquals(CapabilityType.COLOR_SETTING.codifiedEnum(), result.capabilities[0].type.type)
        assertEquals(true, result.capabilities[0].retrievable)
        assertEquals(null, result.capabilities[0].state)
        assertEquals(0.0f, result.capabilities[0].lastUpdated)

        val colorSettingParameters = result.capabilities[0].parameters as ColorSettingCapabilityParameterObject
        assertEquals(ColorModel.HSV.codifiedEnum(), colorSettingParameters.colorModel?.colorModel)
        assertEquals(2700, colorSettingParameters.temperatureK?.min)
        assertEquals(6500, colorSettingParameters.temperatureK?.max)
        assertEquals(null, colorSettingParameters.colorScene)

        assertEquals(emptyList(), result.properties)
    }

    @Test
    fun `test mapManageGroupCapabilitiesRequest`() {
        val request = Json.decodeFromString<YandexManageGroupCapabilitiesStateRequest>(TestConstants.requestManageGroupCapabilitiesJson)
        val result = request.actions

        assertEquals(1, result.size)
        assertEquals(CapabilityType.ON_OFF.code, result[0]["type"]?.jsonPrimitive?.content)

        val state = result[0]["state"]?.jsonObject
        assertEquals(OnOffCapabilityStateObjectInstance.ON.code, state?.get("instance")?.jsonPrimitive?.content)
        assertEquals(true, state?.get("value")?.jsonPrimitive?.boolean)
    }

    @Test
    fun `test mapManageGroupCapabilitiesResponse`() {
        val response = Json.decodeFromString<YandexManageGroupCapabilitiesStateResponse>(
            TestConstants.responseManageGroupCapabilitiesJson
        )
        val result = response.devices

        assertEquals(3, result.size)
        assertEquals("lamp-id-1", result[0]["id"]?.jsonPrimitive?.content)

        val capabilities = result[0]["capabilities"]?.jsonArray
        assertEquals(1, capabilities?.size)
        assertEquals(CapabilityType.ON_OFF.code, capabilities?.get(0)?.jsonObject?.get("type")?.jsonPrimitive?.content)

        val state = capabilities?.get(0)?.jsonObject?.get("state")?.jsonObject
        assertEquals(OnOffCapabilityStateObjectInstance.ON.code, state?.get("instance")?.jsonPrimitive?.content)

        val actionResult = state?.get("action_result")?.jsonObject
        assertEquals("DONE", actionResult?.get("status")?.jsonPrimitive?.content)
    }

    @Test
    fun `test mapManageDeviceCapabilitiesRequest`() {
        val request = Json.decodeFromString<YandexManageDeviceCapabilitiesStateRequest>(TestConstants.requestManageDeviceCapabilitiesJson)
        val result = request.devices

        assertEquals(1, result.size)
        assertEquals("lamp-id-1", result[0]["id"]?.jsonPrimitive?.content)

        val actions = result[0]["actions"]?.jsonArray
        assertEquals(3, actions?.size)

        val onOffAction = actions?.get(0)?.jsonObject
        assertEquals(CapabilityType.ON_OFF.code, onOffAction?.get("type")?.jsonPrimitive?.content)
        val onOffState = onOffAction?.get("state")?.jsonObject
        assertEquals(OnOffCapabilityStateObjectInstance.ON.code, onOffState?.get("instance")?.jsonPrimitive?.content)
        assertEquals(true, onOffState?.get("value")?.jsonPrimitive?.boolean)

        val rangeAction = actions?.get(1)?.jsonObject
        assertEquals(CapabilityType.RANGE.code, rangeAction?.get("type")?.jsonPrimitive?.content)
        val rangeState = rangeAction?.get("state")?.jsonObject
        assertEquals("brightness", rangeState?.get("instance")?.jsonPrimitive?.content)
        assertEquals(50.0, rangeState?.get("value")?.jsonPrimitive?.double)

        val colorSettingAction = actions?.get(2)?.jsonObject
        assertEquals(CapabilityType.COLOR_SETTING.code, colorSettingAction?.get("type")?.jsonPrimitive?.content)
        val colorSettingState = colorSettingAction?.get("state")?.jsonObject
        assertEquals("temperature_k", colorSettingState?.get("instance")?.jsonPrimitive?.content)
        assertEquals(4000, colorSettingState?.get("value")?.jsonPrimitive?.int)
    }

    @Test
    fun `test mapManageDeviceCapabilitiesResponse`() {
        val response = Json.decodeFromString<YandexManageDeviceCapabilitiesStateResponse>(
            TestConstants.responseManageDeviceCapabilitiesJson
        )
        val result = response.devices

        assertEquals(1, result.size)
        assertEquals("lamp-id-1", result[0]["id"]?.jsonPrimitive?.content)

        val capabilities = result[0]["capabilities"]?.jsonArray
        assertEquals(3, capabilities?.size)

        val onOffCapability = capabilities?.get(0)?.jsonObject
        assertEquals(CapabilityType.ON_OFF.code, onOffCapability?.get("type")?.jsonPrimitive?.content)
        val onOffState = onOffCapability?.get("state")?.jsonObject
        assertEquals(OnOffCapabilityStateObjectInstance.ON.code, onOffState?.get("instance")?.jsonPrimitive?.content)
        val onOffActionResult = onOffState?.get("action_result")?.jsonObject
        assertEquals("DONE", onOffActionResult?.get("status")?.jsonPrimitive?.content)

        val rangeCapability = capabilities?.get(1)?.jsonObject
        assertEquals(CapabilityType.RANGE.code, rangeCapability?.get("type")?.jsonPrimitive?.content)
        val rangeState = rangeCapability?.get("state")?.jsonObject
        assertEquals("brightness", rangeState?.get("instance")?.jsonPrimitive?.content)
        val rangeActionResult = rangeState?.get("action_result")?.jsonObject
        assertEquals("DONE", rangeActionResult?.get("status")?.jsonPrimitive?.content)

        val colorSettingCapability = capabilities?.get(2)?.jsonObject
        assertEquals(CapabilityType.COLOR_SETTING.code, colorSettingCapability?.get("type")?.jsonPrimitive?.content)
        val colorSettingState = colorSettingCapability?.get("state")?.jsonObject
        assertEquals("temperature_k", colorSettingState?.get("instance")?.jsonPrimitive?.content)
        val colorSettingActionResult = colorSettingState?.get("action_result")?.jsonObject
        assertEquals("DONE", colorSettingActionResult?.get("status")?.jsonPrimitive?.content)
    }
}