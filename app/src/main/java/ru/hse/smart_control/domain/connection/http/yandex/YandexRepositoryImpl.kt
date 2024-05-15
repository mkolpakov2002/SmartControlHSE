package ru.hse.smart_control.domain.connection.http.yandex

import android.app.Application
import com.tatry.yandextest.data.network.RetrofitInstance
import com.tatry.yandextest.domain.model.devices.ResponseModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel

class YandexRepositoryImpl: YandexRepository {

    // Network
    override suspend fun getUserInfoFromNetwork(token: String): UserInfoModel {
        return RetrofitInstance.yandexApi.getUserInfo(token)
    }

    override suspend fun controlDevicesActionsFromNetwork(token: String, deviceList: DeviceActionsRequestModel):
            DeviceActionsAnswerModel {
        return RetrofitInstance.yandexApi.controlDeviceActions(
            token, deviceList)
    }

    override suspend fun getDeviceStateFromNetwork(token: String, devId: String): GetDeviceStateResponse {
        return RetrofitInstance.yandexApi.getDeviceState(token, devId)
    }

    override suspend fun deleteDevice(token: String, devId: String): ResponseModel {
        return RetrofitInstance.yandexApi.deleteDevice(token, devId)
    }



}