package ru.hse.smart_control.ui.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDestination
import kotlinx.coroutines.flow.Flow
import ru.hse.smart_control.model.db.AppDatabase
import ru.hse.smart_control.model.entities.UniversalSchemeEntity


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentVisibleFragment = MutableLiveData<NavDestination>()

    fun setCurrentVisibleFragment(destination: NavDestination) {
        _currentVisibleFragment.value = destination
    }

    fun getCurrentVisibleFragment(): NavDestination? {
        return _currentVisibleFragment.value
    }

    // Используем функцию-расширение для проверки разрешений


    private val database = AppDatabase.getInstance(application.applicationContext)
    private val deviceDao = database.deviceOldItemTypeDao()
    private val _devices = deviceDao?.getAll()
    val devices: Flow<List<UniversalSchemeEntity>>?
        get() = _devices
}