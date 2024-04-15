package com.schneewittchen.rosandroid.widgets.gps

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import sensor_msgs.NavSatFix

/**
 * TODO: Description
 *
 * @author Nils Rottmann
 * @version 1.0.0
 * @created on 05.05.20
 * @updated on 27.10.2020
 * @modified by Nico Studt
 */
@Serializable
class GpsEntity : SubscriberWidgetEntity() {
    init {
        this.width = 8
        this.height = 8
        this.topic = Topic("gps", NavSatFix._TYPE)
    }
}

