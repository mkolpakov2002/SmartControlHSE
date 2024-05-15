package ru.hse.smart_control.ui.fragments.control

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UiSettingsViewModel : ViewModel() {

    private var _element = MutableSharedFlow<WidgetModel>()
    var element = _element.asSharedFlow()

    private var _widgetList = MutableSharedFlow<List<WidgetModel>>()
    var widgetList = _widgetList.asSharedFlow()

    private var _devName = MutableSharedFlow<String>()
    var devName = _devName.asSharedFlow()

    private var _widgetId = MutableSharedFlow<String>()
    var widgetId = _widgetId.asSharedFlow()

    private var _devCapabilityType = MutableSharedFlow<String>()
    var devCapabilityType = _devCapabilityType.asSharedFlow()

    private var _devCapabilitySubType = MutableSharedFlow<String>()
    var devCapabilitySubType = _devCapabilitySubType.asSharedFlow()

    private var _widgetType = MutableSharedFlow<String>()
    var widgetType = _widgetType.asSharedFlow()



    private var _widgetWidth = MutableSharedFlow<Int>()
    var widgetWidth = _widgetWidth.asSharedFlow()

    private var _widgetHeight = MutableSharedFlow<Int>()
    var widgetHeight = _widgetHeight.asSharedFlow()

    fun setElement(widget: WidgetModel) {
        viewModelScope.launch {
            _element.emit(widget)
        }
    }

    private val mutableItemList = MutableLiveData<MutableList<WidgetModel>>(mutableListOf())

    val itemList: LiveData<List<WidgetModel>> = mutableItemList as LiveData<List<WidgetModel>>

    fun addItem(item: WidgetModel) {
        val currentList = mutableItemList.value ?: mutableListOf()
        currentList.add(item)
        mutableItemList.value = currentList
    }

    fun removeItem(item: WidgetModel) {
        val currentList = mutableItemList.value ?: mutableListOf()
        currentList.remove(item)
        mutableItemList.value = currentList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeItemById(id: Int) {
        val currentList = mutableItemList.value ?: mutableListOf()
        currentList.removeIf { it.id == id }
        mutableItemList.value = currentList
    }

    fun setDevName(name: String) {
        viewModelScope.launch {
            _devName.emit(name)
        }
    }

    fun getWidgetId(id: String) {
        viewModelScope.launch {
            _widgetId.emit(id)
        }
    }


    fun setDevCapabilityType(capabilityType: String) {
        viewModelScope.launch {
            _devCapabilityType.emit(capabilityType)
        }
    }

    fun setDevCapabilitySubType(capabilitySubType: String) {
        viewModelScope.launch {
            _devCapabilitySubType.emit(capabilitySubType)
        }
    }

    fun setWidgetType(type: String) {
        viewModelScope.launch {
            _widgetType.emit(type)
        }
    }

    private var _posLeft = MutableSharedFlow<Int>()
    var posLeft = _posLeft.asSharedFlow()

    private var _posTop = MutableSharedFlow<Int>()
    var posTop = _posTop.asSharedFlow()

    fun setPosLeft(left: Int) {
        viewModelScope.launch {
            _posLeft.emit(left)
        }
    }

    fun setPosTop(top: Int) {
        viewModelScope.launch {
            _posTop.emit(top)
        }
    }

    fun setWidgetHeight(height: Int) {
        viewModelScope.launch {
            _widgetHeight.emit(height)
        }
    }

    fun setWidgetWidth(width: Int) {
        viewModelScope.launch {
            _widgetWidth.emit(width)
        }
    }

}