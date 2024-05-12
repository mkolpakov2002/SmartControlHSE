package ru.hse.smart_control.model.entities.universal.scheme.common.ros

import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalDeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalPayload

@Serializable
data class ROSInfo(
    @SerialName("devices") val devices: MutableList<ROSDeviceObject> = mutableListOf()
): UniversalPayload

@Serializable
data class ROSDeviceObject(
    val configEntity: ConfigEntity,
    val masterEntity: MasterEntity,
    val sshEntity: SSHEntity,
    val uiWidgets: MutableList<BaseEntity>
): UniversalDeviceObject