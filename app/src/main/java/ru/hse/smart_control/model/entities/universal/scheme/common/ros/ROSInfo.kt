package ru.hse.smart_control.model.entities.universal.scheme.common.ros

import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity
import ru.hse.smart_control.model.entities.universal.scheme.common.TypeConnectionParameterObject

data class ROSInfo(
    val configEntity: ConfigEntity,
    val masterEntity: MasterEntity,
    val sshEntity: SSHEntity,
    val uiWidgets: List<BaseEntity>
): TypeConnectionParameterObject