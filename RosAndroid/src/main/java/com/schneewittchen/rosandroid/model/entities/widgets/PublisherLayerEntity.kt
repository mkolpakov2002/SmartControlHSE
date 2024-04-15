package com.schneewittchen.rosandroid.model.entities.widgets

import kotlinx.serialization.Serializable


/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 24.09.20
 */
@Serializable
abstract class PublisherLayerEntity : BaseEntity(), I2DLayerEntity, IPublisherEntity {
    @JvmField
    var publishRate: Float = 1f
    @JvmField
    var immediatePublish: Boolean = false


    override fun equalRosState(other: BaseEntity): Boolean {
        if (!super.equalRosState(other)) {
            return false
        }

        if (other !is PublisherLayerEntity) {
            return false
        }

        val otherPub = other

        return (this.publishRate == otherPub.publishRate
                && this.immediatePublish == otherPub.immediatePublish)
    }
}
