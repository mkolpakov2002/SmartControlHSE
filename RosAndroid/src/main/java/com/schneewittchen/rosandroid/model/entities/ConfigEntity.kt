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
}