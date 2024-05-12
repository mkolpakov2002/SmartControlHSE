package com.schneewittchen.rosandroid.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * @author Nils Rottmann
 * @version 1.0.0
 * @created on 04.06.20
 */
@Entity(tableName = "ssh_table")
@Serializable
class SSHEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    var configId: Long = 0
    @JvmField
    var ip: String = "192.168.1.1"
    @JvmField
    var port: Int = 22
    @JvmField
    var username: String = "pi"
    @JvmField
    var password: String = "raspberry"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SSHEntity

        if (id != other.id) return false
        if (configId != other.configId) return false
        if (ip != other.ip) return false
        if (port != other.port) return false
        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + configId.hashCode()
        result = 31 * result + ip.hashCode()
        result = 31 * result + port
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}