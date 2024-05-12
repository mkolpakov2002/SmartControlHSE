package com.schneewittchen.rosandroid.widgets.viz2d

import com.schneewittchen.rosandroid.model.entities.widgets.GroupEntity
import kotlinx.serialization.Serializable


/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 08.03.21
 */
@Serializable
class Viz2DEntity : GroupEntity() {

    @JvmField
    var frame: String

    init {
        this.width = 8
        this.height = 8
        this.frame = "map"
    }
}
