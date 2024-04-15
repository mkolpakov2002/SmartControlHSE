package com.schneewittchen.rosandroid.widgets.gridmap

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberLayerEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import nav_msgs.OccupancyGrid

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 08.03.21
 */
@Serializable
class GridMapEntity : SubscriberLayerEntity() {
    init {
        this.topic = Topic("/move_base/local_costmap/costmap", OccupancyGrid._TYPE)
    }
}
