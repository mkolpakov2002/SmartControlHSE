package ru.hse.smart_control.model.entities.universal.scheme.common

import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ArduinoInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.ROSInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.SmartHomeInfo

data class UniversalScheme(
    var id: Long,
    var objects: List<UniversalSchemeObject> = emptyList()
)

sealed interface UniversalSchemeObject {
    val type: TypeDeviceConnection
    val payload: UniversalPayload?
}

data class SmartHomeTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.SMART_HOME,
    override val payload: SmartHomeInfo?,
    var smartHomeApiUrl: String
) : UniversalSchemeObject

data class RosTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.ROS,
    override val payload: ROSInfo?
) : UniversalSchemeObject

data class ArduinoTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.ARDUINO_BT,
    override val payload: ArduinoInfo?
) : UniversalSchemeObject

interface UniversalPayload

interface UniversalDeviceObject

enum class TypeDeviceConnection(val typeProtocol: TypeProtocol) {
    ROS(TypeProtocol.INTERNET),
    SMART_HOME(TypeProtocol.INTERNET),
    ARDUINO_BT(TypeProtocol.BLUETOOTH)
}

enum class TypeProtocol {
    BLUETOOTH,
    BLE,
    INTERNET,
    USB
}