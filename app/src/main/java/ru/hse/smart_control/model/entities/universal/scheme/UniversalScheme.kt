package ru.hse.smart_control.model.entities.universal.scheme

import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.hse.miem.yandexsmarthomeapi.entity.UserInfoResponse

@Serializable
data class UniversalScheme(val objects: List<UniversalSchemeObject>)

@Serializable
sealed interface UniversalSchemeObject {
    val type: TypeDeviceConnection
    val parameter: TypeConnectionParameterObject
}

@Serializable
data class SmartHomeTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.SMART_HOME,
    override val parameter: SmartHomeTypeConnectionParameterObject,
    var smartHomeApiUrl: String
) : UniversalSchemeObject

@Serializable
data class SmartHomeTypeConnectionParameterObject(
    val userInfoResponse: UserInfoResponse
) : TypeConnectionParameterObject

@Serializable
data class RosTypeConnectionObject(
    override val type: TypeDeviceConnection = TypeDeviceConnection.ROS,
    override val parameter: RosTypeConnectionParameterObject
) : UniversalSchemeObject

@Serializable
sealed interface TypeConnectionParameterObject

@Serializable
data class RosTypeConnectionParameterObject(
    @SerialName("config_entity")
    val configEntity: ConfigEntity,
    @SerialName("master_entity")
    val masterEntity: MasterEntity,
    @SerialName("ssh_entity")
    val sshEntity: SSHEntity,
    @SerialName("ui_widgets")
    val uiWidgets: List<BaseEntity>
): TypeConnectionParameterObject

@Serializable
enum class TypeDeviceConnection(val typeProtocol: TypeProtocol){
    ROS(TypeProtocol.INTERNET),
    SMART_HOME(TypeProtocol.INTERNET),
    ARDUINO_BT(TypeProtocol.BLUETOOTH)
}

@Serializable
enum class TypeProtocol{
    BLUETOOTH,
    BLE,
    INTERNET,
    USB
}

