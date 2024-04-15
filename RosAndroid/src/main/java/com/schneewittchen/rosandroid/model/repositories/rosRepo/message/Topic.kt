package com.schneewittchen.rosandroid.model.repositories.rosRepo.message

import kotlinx.serialization.Serializable


/**
 * ROS topic class for subscriber/publisher nodes.
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 15.09.2020
 */
@Serializable
class Topic {
    /**
     * Topic name e.g. '/map'
     */
    var name: String = ""

    /**
     * Type of the topic e.g. 'nav_msgs.OccupancyGrid'
     */
    var type: String = ""


    constructor(name: String, type: String) {
        this.name = name
        this.type = type
    }

    constructor(other: Topic?) {
        if (other == null) {
            return
        }

        this.name = other.name
        this.type = other.type
    }


    override fun equals(`object`: Any?): Boolean {
        if (`object` === this) {
            return true
        } else if (`object`!!.javaClass != this.javaClass) {
            return false
        }

        val other = `object` as Topic?

        return other!!.name == name && other.type == type
    }

    override fun hashCode(): Int {
        return name.hashCode() + 31 * type.hashCode()
    }
}
