package com.schneewittchen.rosandroid.model.entities.widgets

import com.schneewittchen.rosandroid.ui.general.Position
import kotlinx.serialization.Serializable


/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 24.09.20
 */
@Serializable
abstract class PublisherWidgetEntity

    : BaseEntity(), IPositionEntity, IPublisherEntity {
    @JvmField
    var publishRate: Float = 1f
    @JvmField
    var immediatePublish: Boolean = false
    var posX: Int = 0
    var posY: Int = 0
    @JvmField
    var width: Int = 0
    @JvmField
    var height: Int = 0


    override fun equalRosState(other: BaseEntity): Boolean {
        if (!super.equalRosState(other)) {
            return false
        }

        if (other !is PublisherWidgetEntity) {
            return false
        }

        val otherPub = other

        return (this.publishRate == otherPub.publishRate
                && this.immediatePublish == otherPub.immediatePublish)
    }

    override fun getPosition(): Position {
        return Position(posX, posY, width, height)
    }

    override fun setPosition(position: Position) {
        this.posX = position.x
        this.posY = position.y
        this.width = position.width
        this.height = position.height
    }
}
