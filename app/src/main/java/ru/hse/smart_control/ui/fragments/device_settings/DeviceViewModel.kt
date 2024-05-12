package ru.hse.smart_control.ui.fragments.device_settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.smart_control.model.db.AppDatabase
import ru.hse.smart_control.model.entities.UniversalSchemeEntity

class DeviceViewModel(application: Application) : AndroidViewModel(application) {
    private val _currentUniversalSchemeEntity = MutableLiveData<UniversalSchemeEntity>()
    val currentUniversalSchemeEntity: LiveData<UniversalSchemeEntity> = _currentUniversalSchemeEntity
    private val database = AppDatabase.getInstance(application.applicationContext)
    private val deviceDao = database.deviceOldItemTypeDao()

    fun setCurrentDevice(universalSchemeEntity: UniversalSchemeEntity) {
        _currentUniversalSchemeEntity.value = universalSchemeEntity
    }

    fun updateCurrentDevice(universalSchemeEntity: UniversalSchemeEntity) {
        _currentUniversalSchemeEntity.value = universalSchemeEntity
    }

    fun saveDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            deviceDao?.insertAll(currentUniversalSchemeEntity.value!!)
        }
    }

    fun deleteDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            deviceDao?.delete(currentUniversalSchemeEntity.value!!.id)
        }
    }
}