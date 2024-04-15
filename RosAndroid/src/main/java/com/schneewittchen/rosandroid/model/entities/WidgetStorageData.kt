package com.schneewittchen.rosandroid.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * TODO: Description
 *
 * Replaced version of Base Entity.
 *
 * @author Nico Studt
 * @version 1.0.0
 * @created on 23.09.20
 * @updated on
 * @modified by
 */
@Entity(tableName = "widget_table")
@Serializable
class WidgetStorageData {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @JvmField
    @ColumnInfo(name = "type_name")
    var typeName: String? = null

    @JvmField
    @ColumnInfo(name = "widget_config_id")
    var configId: Long = 0

    @JvmField
    @ColumnInfo(name = "data")
    var data: String? = null

    @JvmField
    @ColumnInfo(name = "name")
    var name: String? = null
}
