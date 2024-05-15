package ru.hse.smart_control.domain.connection.http.yandex

import com.tatry.yandextest.data.local.entity.device.DeviceRelations
import com.tatry.yandextest.domain.model.devices.ResponseModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import com.tatry.yandextest.domain.model.user.UserModel

interface YandexRepository {
    // Network
    suspend fun getUserInfoFromNetwork(token: String) : UserInfoModel
    suspend fun getDeviceStateFromNetwork(token: String, devId: String) : GetDeviceStateResponse
    suspend fun controlDevicesActionsFromNetwork(token: String, deviceList: DeviceActionsRequestModel): DeviceActionsAnswerModel
    suspend fun deleteDevice(token: String, devId: String): ResponseModel
}