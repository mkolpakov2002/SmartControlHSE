package com.schneewittchen.rosandroid.widgets.laserscan

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberLayerEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import com.schneewittchen.rosandroid.ui.opengl.visualisation.ROSColor
import kotlinx.serialization.Serializable
import sensor_msgs.LaserScan

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 14.05.21
 */
@Serializable
class LaserScanEntity : SubscriberLayerEntity() {
    @JvmField
    var pointsColor: Int
    @JvmField
    var areaColor: Int
    @JvmField
    var pointSize: Int
    @JvmField
    var showFreeSpace: Boolean


    init {
        this.topic = Topic("/scan", LaserScan._TYPE)
        this.pointsColor = ROSColor.fromHexAndAlpha("377dfa", 0.6f).toInt()
        this.areaColor = ROSColor.fromHexAndAlpha("377dfa", 0.2f).toInt()
        this.pointSize = 10
        this.showFreeSpace = true
    }
}
