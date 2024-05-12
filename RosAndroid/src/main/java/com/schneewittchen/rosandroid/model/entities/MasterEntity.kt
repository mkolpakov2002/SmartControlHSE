package com.schneewittchen.rosandroid.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nico Studt
 * @version 1.0.1
 * @created on 30.01.20
 * @updated on 31.01.20
 * @modified by
 */
@Entity(tableName = "master_table")
@Serializable
class MasterEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    var configId: Long = 0
    @JvmField
    var ip: String = "192.168.0.0"
    @JvmField
    var port: Int = 11311

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MasterEntity

        if (id != other.id) return false
        if (configId != other.configId) return false
        if (ip != other.ip) return false
        if (port != other.port) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + configId.hashCode()
        result = 31 * result + ip.hashCode()
        result = 31 * result + port
        return result
    }
}
