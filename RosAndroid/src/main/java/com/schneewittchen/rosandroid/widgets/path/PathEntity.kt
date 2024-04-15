package com.schneewittchen.rosandroid.widgets.path

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberLayerEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import nav_msgs.Path

/**
 * Path entity represents a widget which subscribes
 * to a topic with message type "nav_msgs.Path".
 * Usable in 2D widgets.
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 08.03.21
 */
@Serializable
class PathEntity : SubscriberLayerEntity() {
    @JvmField
    var lineWidth: Float
    @JvmField
    var lineColor: String


    init {
        this.topic = Topic("/move_base/TebLocalPlannerROS/local_plan", Path._TYPE)
        this.lineWidth = 4f
        this.lineColor = "ff0000ff"
    }
}
