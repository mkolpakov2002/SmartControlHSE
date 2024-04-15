package com.schneewittchen.rosandroid.widgets.logger

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable

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
class LoggerEntity : SubscriberWidgetEntity() {
    var text: String
    @JvmField
    var rotation: Int


    init {
        this.width = 3
        this.height = 1
        this.topic = Topic("log", std_msgs.String._TYPE)
        this.text = "A logger"
        this.rotation = 0
    }
}
