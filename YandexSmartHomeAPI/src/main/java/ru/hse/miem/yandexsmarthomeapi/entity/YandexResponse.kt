package ru.hse.miem.yandexsmarthomeapi.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Response {
    val status: String
    @SerialName("request_id")
    val requestId: String
}

@Serializable
sealed class ApiResponse {
    @Serializable
    data class SuccessUserInfo(val data: UserInfoResponse) : ApiResponse()
    @Serializable
    data class SuccessDeviceState(val data: DeviceStateResponse) : ApiResponse()
    @Serializable
    data class SuccessDeviceGroup(val data: DeviceGroupResponse) : ApiResponse()
    @Serializable
    data class SuccessManageDeviceCapabilitiesState(val data: ManageDeviceCapabilitiesStateResponse) : ApiResponse()
    @Serializable
    data class SuccessManageGroupCapabilitiesState(val data: ManageGroupCapabilitiesStateResponse) : ApiResponse()
    @Serializable
    data class Error(val error: ErrorModelResponse) : ApiResponse()
}

@Serializable
data class ErrorModelResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    val error: String? = null
): Response

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-user-info.html#format-response
 */
@Serializable
data class UserInfoResponse(
    @SerialName("request_id") override val requestId: String,
    @SerialName("status") override val status: String,
    @SerialName("rooms") val rooms: List<RoomObject>,
    @SerialName("groups") val groups: List<GroupObject>,
    @SerialName("devices") val devices: List<DeviceObject>,
    @SerialName("scenarios") val scenarios: List<ScenarioObject>,
    @SerialName("households") val households: List<HouseholdObject>
): Response

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html
 */
@Serializable
data class DeviceStateResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    override val id: String,
    override val name: String,
    override val aliases: List<String>,
    override val type: DeviceType,
    @SerialName("state") val state: DeviceState,
    override val groups: List<String>,
    override val room: String?,
    @SerialName("external_id") override val externalId: String,
    @SerialName("skill_id") override val skillId: String,
    override val capabilities: List<CapabilityObject>,
    override val properties: List<PropertyObject>
) : BaseDeviceObject, Response

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-capabilities.html#output-structure
 */
@Serializable
data class ManageDeviceCapabilitiesStateResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    @SerialName("devices") val devices: List<DeviceActionsResultObject>
) : Response

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-group-device-info.html#output-structure
 */
@Serializable
data class DeviceGroupResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    val id: String,
    val name: String,
    val aliases: List<String>,
    val type: DeviceType,
    val state: DeviceState,
    val capabilities: List<CapabilityObject>,
    val devices: List<GroupDeviceInfoObject>
): Response

/**
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-group-capabilities.html#output-structure
 */
@Serializable
data class ManageGroupCapabilitiesStateResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    val devices: List<DeviceActionsResultObject>
): Response