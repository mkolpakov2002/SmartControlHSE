package com.schneewittchen.rosandroid.widgets.debug

import com.schneewittchen.rosandroid.model.entities.widgets.SubscriberWidgetEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import org.ros.node.topic.Subscriber

/**
 * TODO: Description
 *
 * @author Nils Rottmann
 * @version 1.0.0
 * @created on 17.08.20
 * @updated on 17.09.20
 * @modified by Nils Rottmann
 */
@Serializable
class DebugEntity : SubscriberWidgetEntity() {
    @JvmField
    var numberMessages: Int


    init {
        this.width = 4
        this.height = 3
        this.topic = Topic("MessageToDebug", Subscriber.TOPIC_MESSAGE_TYPE_WILDCARD)
        this.numberMessages = 10
    }
}
