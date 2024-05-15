package ru.hse.smart_control.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.hse.smart_control.App
import ru.hse.smart_control.domain.connection.http.yandex.YandexRepositoryImpl

private const val TAG = "MainFragment555"

class YandexViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YandexViewModel::class.java)) {
            val repo = YandexRepositoryImpl(App.INSTANCE)
            return YandexViewModel(repo) as T
        }
        throw RuntimeException("Unknown class name")
    }
}

class YandexViewModel(
    private val repo: YandexRepositoryImpl
) : ViewModel() {

    private var _userInfo = MutableStateFlow(UserInfoModel())
    var userInfo = _userInfo.asStateFlow()

//    private var _deviceList = MutableStateFlow<MutableList<DeviceModel>>(mutableListOf())
//    var deviceList = _deviceList.asStateFlow()
//
//    private var _userToken = MutableStateFlow("token")
//    var userToken = _userToken.asStateFlow()
//
//    private var _devState = MutableStateFlow(GetDeviceStateResponse())
//    var devState = _devState.asStateFlow()

    private var _devAction = MutableStateFlow(DeviceActionsAnswerModel())
    var devAction = _devAction.asStateFlow()

    fun uploadUserInfo(token:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.value = repo.getUserInfoFromNetwork(token)
        }
    }

    fun postAction(token: String, deviceList: DeviceActionsRequestModel) {
        viewModelScope.launch {
            _devAction.value = repo.controlDevicesActionsFromNetwork(
                token = token,
                deviceList = deviceList)
        }
    }

}