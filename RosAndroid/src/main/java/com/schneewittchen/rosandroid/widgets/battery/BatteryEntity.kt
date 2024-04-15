package com.schneewittchen.rosandroid.widgets.battery

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import sensor_msgs.BatteryState

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 13.05.2021
 */
@Serializable
class BatteryEntity : SubscriberWidgetEntity() {
    @JvmField
    var displayVoltage: Boolean


    init {
        this.width = 1
        this.height = 2
        this.topic = Topic("battery", BatteryState._TYPE)
        this.displayVoltage = false
    }
}
