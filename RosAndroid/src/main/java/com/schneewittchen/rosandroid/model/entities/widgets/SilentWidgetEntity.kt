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
abstract class SilentWidgetEntity : BaseEntity(), ISilentEntity, IPositionEntity {
    var posX: Int = 0
    var posY: Int = 0
    @JvmField
    var width: Int = 0
    @JvmField
    var height: Int = 0


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
