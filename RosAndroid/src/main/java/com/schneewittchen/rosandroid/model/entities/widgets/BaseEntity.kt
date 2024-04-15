package com.schneewittchen.rosandroid.model.entities.widgets

import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import kotlinx.serialization.Serializable
import java.lang.reflect.Constructor

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.1
 * @created on 23.09.20
 * @updated on 10.03.21
 * @modified by Nico Studt
 */
@Serializable
abstract class BaseEntity {
    @JvmField
    var id: Long = 0
    @JvmField
    var name: String? = null
    @JvmField
    var type: String? = null
    @JvmField
    var configId: Long = 0
    @JvmField
    var creationTime: Long = 0
    @JvmField
    var topic: Topic? = null
    @JvmField
    var validMessage: Boolean = false
    @JvmField
    var childEntities: ArrayList<BaseEntity> = ArrayList()


    open fun equalRosState(other: BaseEntity): Boolean {
        return this.topic == other.topic
    }

    fun addEntity(entity: BaseEntity) {
        childEntities.add(entity)
    }

    fun getChildById(id: Long): BaseEntity? {
        for (child in childEntities) {
            if (child.id == id) return child
        }

        return null
    }

    fun removeChild(entity: BaseEntity) {
        val iter = childEntities.listIterator()
        while (iter.hasNext()) {
            if (iter.next().id == entity.id) {
                iter.remove()
            }
        }
    }

    fun replaceChild(entity: BaseEntity) {
        for (i in childEntities.indices) {
            if (childEntities[i].id == entity.id) {
                childEntities[i] = entity
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        else if (other === this) return true
        else if (other.javaClass != this.javaClass) return false

        // Check if other object has equal field values
        for (f in this.javaClass.fields) {
            try {
                val equalValues = f[this] == f[other]

                if (!equalValues) {
                    return false
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                return false
            }
        }

        return true
    }

    fun copy(): BaseEntity? {
        try {
            val constructor: Constructor<*> = this.javaClass.getConstructor()
            val newObj = constructor.newInstance()

            for (f in this.javaClass.fields) {
                val value = f[this]

                // Check if children available and if object type is matching
                if (value is List<*>) {
                    val children = ArrayList<BaseEntity?>()
                    for (child in this.childEntities) {
                        children.add(child.copy())
                    }
                    f[newObj] = children
                } else if (f.type == Topic::class.java) {
                    val topicObj = f[this] as Topic
                    f[newObj] = Topic(topicObj)
                } else {
                    f[newObj] = f[this]
                }
            }

            return newObj as BaseEntity
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    override fun toString(): String {
        var output = "[Widget: "

        for ((i, f) in this.javaClass.fields.withIndex()) {
            if (i > 0) {
                output += ", "
            }

            try {
                output += f.name + ": "

                output += if (f[this] == null) {
                    "null"
                } else {
                    f[this]?.toString()
                }
            } catch (e: IllegalAccessException) {
            }

        }

        output += "]"
        return output
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + configId.hashCode()
        result = 31 * result + creationTime.hashCode()
        result = 31 * result + (topic?.hashCode() ?: 0)
        result = 31 * result + validMessage.hashCode()
        result = 31 * result + childEntities.hashCode()
        return result
    }
}
