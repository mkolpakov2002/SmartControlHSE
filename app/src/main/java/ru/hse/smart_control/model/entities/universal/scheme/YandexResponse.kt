package ru.hse.smart_control.model.entities.universal.scheme

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
    data class Error(val error: ErrorModelResponse) : ApiResponse()
}

@Serializable
data class ErrorModelResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    val error: String
): Response

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
    override val status: String,
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

@Serializable
data class DeviceActionResponse(
    @SerialName("status") override val status: String,
    @SerialName("request_id") override val requestId: String,
    @SerialName("devices") val devices: List<DeviceActionsResultObject>
) : Response