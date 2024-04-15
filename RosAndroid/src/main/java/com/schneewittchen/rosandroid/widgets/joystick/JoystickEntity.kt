package com.schneewittchen.rosandroid.widgets.joystick

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import geometry_msgs.Twist
import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.1.1
 * @created on 31.01.20
 * @updated on 10.05.20
 * @modified by Nico Studt
 */
@Serializable
class JoystickEntity : PublisherWidgetEntity() {
    @JvmField
    var xAxisMapping: String
    @JvmField
    var yAxisMapping: String
    @JvmField
    var xScaleLeft: Float
    @JvmField
    var xScaleRight: Float
    @JvmField
    var yScaleLeft: Float
    @JvmField
    var yScaleRight: Float


    init {
        this.width = 4
        this.height = 4
        this.topic = Topic("cmd_vel", Twist._TYPE)
        this.immediatePublish = false
        this.publishRate = 20f
        this.xAxisMapping = "Angular/Z"
        this.yAxisMapping = "Linear/X"
        this.xScaleLeft = 1f
        this.xScaleRight = -1f
        this.yScaleLeft = -1f
        this.yScaleRight = 1f
    }
}
