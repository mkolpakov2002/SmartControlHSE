package com.schneewittchen.rosandroid.widgets.button

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import std_msgs.Bool

/**
 * TODO: Description
 *
 * @author Dragos Circa
 * @version 1.0.0
 * @created on 02.11.2020
 * @updated on 18.11.2020
 * @modified by Nils Rottmann
 */
@Serializable
class ButtonEntity : PublisherWidgetEntity() {
    @JvmField
    var text: String
    @JvmField
    var rotation: Int


    init {
        this.width = 2
        this.height = 1
        this.topic = Topic("btn_press", Bool._TYPE)
        this.immediatePublish = true
        this.text = "A button"
        this.rotation = 0
    }
}
