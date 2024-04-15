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
}
