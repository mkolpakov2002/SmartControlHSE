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
}