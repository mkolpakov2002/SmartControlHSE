package ru.hse.smart_control.ui.fragments.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.smart_control.domain.ApiResponse
import ru.hse.smart_control.domain.UserRepositoryImpl
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.hse.smart_control.domain.Result

class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val userRepository = UserRepositoryImpl()
            return AuthViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}

class AuthViewModel(
    private val userRepository: UserRepositoryImpl
): ViewModel() {

//    val registerResult = MutableLiveData<ApiResponse<Unit>>()

    private var _registerResult = MutableSharedFlow<ApiResponse<Unit>>()
    var registerResult = _registerResult.asSharedFlow()

    private var _userToken = MutableSharedFlow<TokenModel>()
    var userToken = _userToken.asSharedFlow()

    private var eventChannel = MutableSharedFlow<UserEvent>()
    var events = eventChannel.asSharedFlow()

//    private val eventChannel = Channel<UserEvent>()
//    val events = eventChannel.receiveAsFlow()

//    fun registerUser(user: RegisterModel) {
//        viewModelScope.launch(Dispatchers.IO) {
//            userRepository.register(user)
//        }
//    }

//    fun register(user: RegisterModel): LiveData<ApiResponse<Unit>> = liveData {
////        val response = userRepository.register(user).collect { apiResponse1 ->
////            emit(apiResponse1)
////        }
//        registerResult = userRepository.register(user).asLiveData()
//    }

//    fun register(user: RegisterModel) {
//        viewModelScope.launch {
//            val response = userRepository.register(user).collectLatest { apiResponse ->
////                registerResult.postValue(apiResponse)
//                registerResult.value = apiResponse
//            }
//        }
//    }

    fun register(user: RegisterModel) {
        viewModelScope.launch {
            userRepository.register(user)
//                .catch { e ->
//                registerResult.value = ApiResponse.Failure(null,"e.message")
//            }
                .collect { apiResponse ->
//                registerResult.value = apiResponse
                _registerResult.emit(apiResponse)
            }
        }
    }

    fun register2(user: RegisterModel) {
        viewModelScope.launch {
//            userRepository.register2(user)


                when(val result = userRepository.register2(user)) {
                    is Result.Error -> {
                        val errorMessage = result.error.toString()
//                        eventChannel.send(UserEvent.Error(errorMessage))
                        eventChannel.emit(UserEvent.Error(errorMessage))
                    }
                    is Result.Success -> {
                        result.data
                    }
                }
//            eventChannel.emit(userRepository.register2(user))

        }
    }

    fun loginUser(user: LoginModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _userToken.emit(userRepository.loginUser(user))
        }
    }
}

sealed interface UserEvent {
    data class Error(val error: String): UserEvent
}