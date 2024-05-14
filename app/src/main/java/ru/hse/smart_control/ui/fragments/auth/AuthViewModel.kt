package ru.hse.smart_control.ui.fragments.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.hse.smart_control.domain.ApiResponse
import ru.hse.smart_control.domain.UserRepositoryImpl
import ru.hse.smart_control.model.user.LoginModel
import ru.hse.smart_control.model.user.RegisterModel
import ru.hse.smart_control.model.user.TokenModel

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
) : ViewModel() {

    private var _registerResult = MutableSharedFlow<ApiResponse<Unit>>()
    var registerResult = _registerResult.asSharedFlow()

    private var _loginResult = MutableSharedFlow<ApiResponse<TokenModel>>()
    var loginResult = _loginResult.asSharedFlow()

    private var _userToken = MutableSharedFlow<TokenModel>()
    var userToken = _userToken.asSharedFlow()

    fun register(user: RegisterModel) {
        viewModelScope.launch {
            userRepository.register(user)
                .collect {
                    _registerResult.emit(it)
                }
        }
    }

    fun loginUser(user: LoginModel) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginUser(user).collect {
                _loginResult.emit(it)
            }
        }
    }
}