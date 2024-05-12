package com.schneewittchen.rosandroid.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.2
 * @created on 30.01.20
 * @updated on 04.06.20
 * @modified by Nils Rottmann
 * @updated on 27.07.20
 * @modified by Nils Rottmann
 * @updated on 01.10.20
 * @modified by Nico Studt
 */
@Entity(tableName = "config_table")
@Serializable
class ConfigEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    var creationTime: Long = 0
    @JvmField
    var lastUsed: Long = 0
    @JvmField
    var name: String = "DefaultName"
    @JvmField
    var isFavourite: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfigEntity

        if (id != other.id) return false
        if (creationTime != other.creationTime) return false
        if (lastUsed != other.lastUsed) return false
        if (name != other.name) return false
        if (isFavourite != other.isFavourite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + creationTime.hashCode()
        result = 31 * result + lastUsed.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + isFavourite.hashCode()
        return result
    }
}