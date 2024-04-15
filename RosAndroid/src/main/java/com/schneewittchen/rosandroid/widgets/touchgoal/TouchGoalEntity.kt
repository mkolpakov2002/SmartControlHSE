package com.schneewittchen.rosandroid.widgets.touchgoal

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherLayerEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import geometry_msgs.PoseStamped
import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 26.05.2021
 */
@Serializable
class TouchGoalEntity : PublisherLayerEntity() {
    init {
        this.topic = Topic("/goal", PoseStamped._TYPE)
        this.immediatePublish = true
    }
}
