package com.schneewittchen.rosandroid.widgets.pose

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberLayerEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import geometry_msgs.PoseWithCovarianceStamped
import kotlinx.serialization.Serializable

/**
 * Pose entity represents a widget which subscribes
 * to a topic with message type "geometry_msgs.PoseStamped".
 * Usable in 2D widgets.
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 10.03.21
 */
@Serializable
class PoseEntity : SubscriberLayerEntity() {
    init {
        this.topic = Topic("/amcl_pose", PoseWithCovarianceStamped._TYPE)
    }
}
