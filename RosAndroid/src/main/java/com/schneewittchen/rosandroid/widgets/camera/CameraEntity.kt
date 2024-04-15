package com.schneewittchen.rosandroid.widgets.camera

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import sensor_msgs.Image

/**
 * TODO: Description
 *
 * @author Nils Rottmann
 * @version 1.0.0
 * @created on 27.04.2020
 * @updated on 27.10.2020
 * @modified by Nico Studt
 */
@Serializable
class CameraEntity : SubscriberWidgetEntity() {
    var colorScheme: Int = 0
    var drawBehind: Boolean = false
    var useTimeStamp: Boolean = false


    init {
        this.width = 8
        this.height = 6
        this.topic = Topic("camera/image_raw", Image._TYPE)
    }
}

