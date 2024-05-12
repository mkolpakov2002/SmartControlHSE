package ru.hse.smart_control.model.entities.universal.scheme.common.ros

import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic
import com.schneewittchen.rosandroid.widgets.battery.BatteryEntity
import com.schneewittchen.rosandroid.widgets.button.ButtonEntity
import com.schneewittchen.rosandroid.widgets.camera.CameraEntity
import com.schneewittchen.rosandroid.widgets.debug.DebugEntity
import com.schneewittchen.rosandroid.widgets.gps.GpsEntity
import com.schneewittchen.rosandroid.widgets.gridmap.GridMapEntity
import com.schneewittchen.rosandroid.widgets.joystick.JoystickEntity
import com.schneewittchen.rosandroid.widgets.label.LabelEntity
import com.schneewittchen.rosandroid.widgets.laserscan.LaserScanEntity
import com.schneewittchen.rosandroid.widgets.logger.LoggerEntity
import com.schneewittchen.rosandroid.widgets.path.PathEntity
import com.schneewittchen.rosandroid.widgets.pose.PoseEntity
import com.schneewittchen.rosandroid.widgets.rqtplot.RqtPlotEntity
import com.schneewittchen.rosandroid.widgets.touchgoal.TouchGoalEntity
import com.schneewittchen.rosandroid.widgets.viz2d.Viz2DEntity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.add
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.put

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    encodeDefaults = true
}

fun ROSInfo.toJson(): JsonObject {
    return buildJsonObject {
        put("devices", buildJsonArray {
            devices.forEach { device ->
                add(device.toJson())
            }
        })
    }
}

fun JsonObject.toROSInfo(): ROSInfo {
    return ROSInfo(
        devices = this["devices"]?.jsonArray?.map {
            it.jsonObject.toROSDeviceObject()
        }?.toMutableList() ?: mutableListOf()
    )
}

fun ROSDeviceObject.toJson(): JsonObject {
    return buildJsonObject {
        put("configEntity", configEntity.toJson())
        put("masterEntity", masterEntity.toJson())
        put("sshEntity", sshEntity.toJson())
        put("uiWidgets", buildJsonArray {
            uiWidgets.forEach { entity ->
                when (entity) {
                    is ButtonEntity -> add(entity.toJson())
                    is JoystickEntity -> add(entity.toJson())
                    is BatteryEntity -> add(entity.toJson())
                    is CameraEntity -> add(entity.toJson())
                    is DebugEntity -> add(entity.toJson())
                    is GpsEntity -> add(entity.toJson())
                    is LabelEntity -> add(entity.toJson())
                    is LoggerEntity -> add(entity.toJson())
                    is RqtPlotEntity -> add(entity.toJson())
                    is Viz2DEntity -> add(entity.toJson())
                    is GridMapEntity -> add(entity.toJson())
                    is LaserScanEntity -> add(entity.toJson())
                    is PathEntity -> add(entity.toJson())
                    is PoseEntity -> add(entity.toJson())
                    is TouchGoalEntity -> add(entity.toJson())
                    else -> throw IllegalArgumentException("Unsupported widget type: ${entity.javaClass.simpleName}")
                }
            }
        })
    }
}

fun JsonObject.toROSDeviceObject(): ROSDeviceObject {
    return try {
        val configEntity = this["configEntity"]?.jsonObject?.toConfigEntity()
            ?: throw IllegalArgumentException("Invalid or missing configEntity")
        val masterEntity = this["masterEntity"]?.jsonObject?.toMasterEntity()
            ?: throw IllegalArgumentException("Invalid or missing masterEntity")
        val sshEntity = this["sshEntity"]?.jsonObject?.toSSHEntity()
            ?: throw IllegalArgumentException("Invalid or missing sshEntity")
        val uiWidgets = this["uiWidgets"]?.jsonArray?.mapNotNull {
            it.jsonObject.toBaseEntity()
        }?.toMutableList()
            ?: throw IllegalArgumentException("Invalid or missing uiWidgets")

        ROSDeviceObject(configEntity, masterEntity, sshEntity, uiWidgets)
    } catch (e: Exception) {
        throw IllegalArgumentException("Invalid or missing ROSInfo: $this", e)
    }
}

// BaseEntity
fun BaseEntity.toJson(): JsonObject {
    return when (this) {
        is BatteryEntity -> toJson()
        is ButtonEntity -> toJson()
        is CameraEntity -> toJson()
        is DebugEntity -> toJson()
        is GpsEntity -> toJson()
        is GridMapEntity -> toJson()
        is JoystickEntity -> toJson()
        is LabelEntity -> toJson()
        is LaserScanEntity -> toJson()
        is LoggerEntity -> toJson()
        is PathEntity -> toJson()
        is PoseEntity -> toJson()
        is RqtPlotEntity -> toJson()
        is TouchGoalEntity -> toJson()
        is Viz2DEntity -> toJson()
        else -> throw IllegalArgumentException("Unknown entity type: ${this.javaClass.simpleName}")
    }
}

fun JsonObject.toBaseEntity(): BaseEntity {
    val entityType = jsonObject["type"]?.jsonPrimitive?.content
        ?: throw IllegalArgumentException("Missing entityType field in JSON: $this")

    return when (entityType) {
        "Battery" -> toBatteryEntity()
        "Button" -> toButtonEntity()
        "Camera" -> toCameraEntity()
        "Debug" -> toDebugEntity()
        "Gps" -> toGpsEntity()
        "GridMap" -> toGridMapEntity()
        "Joystick" -> toJoystickEntity()
        "Label" -> toLabelEntity()
        "LaserScan" -> toLaserScanEntity()
        "Logger" -> toLoggerEntity()
        "Path" -> toPathEntity()
        "Pose" -> toPoseEntity()
        "RqtPlot" -> toRqtPlotEntity()
        "TouchGoal" -> toTouchGoalEntity()
        "Viz2D" -> toViz2DEntity()
        else -> throw IllegalArgumentException("Unknown entity type: $entityType")
    }
}

// SSHEntity
fun SSHEntity.toJson(): JsonObject {
    return json.encodeToJsonElement(this).jsonObject
}

fun JsonObject.toSSHEntity(): SSHEntity {
    return json.decodeFromJsonElement(this)
}

// MasterEntity
fun MasterEntity.toJson(): JsonObject {
    return json.encodeToJsonElement(this).jsonObject
}

fun JsonObject.toMasterEntity(): MasterEntity {
    return json.decodeFromJsonElement(this)
}

// ConfigEntity
fun ConfigEntity.toJson(): JsonObject {
    return json.encodeToJsonElement(this).jsonObject
}

fun JsonObject.toConfigEntity(): ConfigEntity {
    return json.decodeFromJsonElement(this)
}

// BatteryEntity
fun BatteryEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("displayVoltage", displayVoltage)
    }
}

fun JsonObject.toBatteryEntity(): BatteryEntity {
    return BatteryEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities =
            ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() }?: emptyList())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        displayVoltage = jsonObject["displayVoltage"]?.jsonPrimitive?.boolean ?: false
    }
}

// ButtonEntity
fun ButtonEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("publishRate", publishRate)
        put("immediatePublish", immediatePublish)
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("text", text)
        put("rotation", rotation)
    }
}

fun JsonObject.toButtonEntity(): ButtonEntity {
    return ButtonEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        publishRate = jsonObject["publishRate"]?.jsonPrimitive?.float ?: 0f
        immediatePublish = jsonObject["immediatePublish"]?.jsonPrimitive?.boolean ?: false
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        text = jsonObject["text"]?.jsonPrimitive?.content ?: ""
        rotation = jsonObject["rotation"]?.jsonPrimitive?.int ?: 0
    }
}

// CameraEntity
fun CameraEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("colorScheme", colorScheme)
        put("drawBehind", drawBehind)
        put("useTimeStamp", useTimeStamp)
    }
}

fun JsonObject.toCameraEntity(): CameraEntity {
    return CameraEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        colorScheme = jsonObject["colorScheme"]?.jsonPrimitive?.int ?: 0
        drawBehind = jsonObject["drawBehind"]?.jsonPrimitive?.boolean ?: false
        useTimeStamp = jsonObject["useTimeStamp"]?.jsonPrimitive?.boolean ?: false
    }
}

// DebugEntity
fun DebugEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("numberMessages", numberMessages)
    }
}

fun JsonObject.toDebugEntity(): DebugEntity {
    return DebugEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        numberMessages = jsonObject["numberMessages"]?.jsonPrimitive?.int ?: 0
    }
}

// GpsEntity
fun GpsEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
    }
}

fun JsonObject.toGpsEntity(): GpsEntity {
    return GpsEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
    }
}

// GridMapEntity
fun GridMapEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
    }
}

fun JsonObject.toGridMapEntity(): GridMapEntity {
    return GridMapEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
    }
}

// JoystickEntity
fun JoystickEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("publishRate", publishRate)
        put("immediatePublish", immediatePublish)
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("xAxisMapping", xAxisMapping)
        put("yAxisMapping", yAxisMapping)
        put("xScaleLeft", xScaleLeft)
        put("xScaleRight", xScaleRight)
        put("yScaleLeft", yScaleLeft)
        put("yScaleRight", yScaleRight)
    }
}

fun JsonObject.toJoystickEntity(): JoystickEntity {
    return JoystickEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        publishRate = jsonObject["publishRate"]?.jsonPrimitive?.float ?: 0f
        immediatePublish = jsonObject["immediatePublish"]?.jsonPrimitive?.boolean ?: false
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        xAxisMapping = jsonObject["xAxisMapping"]?.jsonPrimitive?.content ?: ""
        yAxisMapping = jsonObject["yAxisMapping"]?.jsonPrimitive?.content ?: ""
        xScaleLeft = jsonObject["xScaleLeft"]?.jsonPrimitive?.float ?: 0f
        xScaleRight = jsonObject["xScaleRight"]?.jsonPrimitive?.float ?: 0f
        yScaleLeft = jsonObject["yScaleLeft"]?.jsonPrimitive?.float ?: 0f
        yScaleRight = jsonObject["yScaleRight"]?.jsonPrimitive?.float ?: 0f
    }
}

// LabelEntity
fun LabelEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("text", text)
        put("rotation", rotation)
    }
}

fun JsonObject.toLabelEntity(): LabelEntity {
    return LabelEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        text = jsonObject["text"]?.jsonPrimitive?.content ?: ""
        rotation = jsonObject["rotation"]?.jsonPrimitive?.int ?: 0
    }
}

// LaserScanEntity
fun LaserScanEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("pointsColor", pointsColor)
        put("areaColor", areaColor)
        put("pointSize", pointSize)
        put("showFreeSpace", showFreeSpace)
    }
}

fun JsonObject.toLaserScanEntity(): LaserScanEntity {
    return LaserScanEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        pointsColor = jsonObject["pointsColor"]?.jsonPrimitive?.int ?: 0
        areaColor = jsonObject["areaColor"]?.jsonPrimitive?.int ?: 0
        pointSize = jsonObject["pointSize"]?.jsonPrimitive?.int ?: 0
        showFreeSpace = jsonObject["showFreeSpace"]?.jsonPrimitive?.boolean ?: false
    }
}

// LoggerEntity
fun LoggerEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("text", text)
        put("rotation", rotation)
    }
}

fun JsonObject.toLoggerEntity(): LoggerEntity {
    return LoggerEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        text = jsonObject["text"]?.jsonPrimitive?.content ?: ""
        rotation = jsonObject["rotation"]?.jsonPrimitive?.int ?: 0
    }
}

// PathEntity
fun PathEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("lineWidth", lineWidth)
        put("lineColor", lineColor)
    }
}

fun JsonObject.toPathEntity(): PathEntity {
    return PathEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        lineWidth = jsonObject["lineWidth"]?.jsonPrimitive?.float ?: 0f
        lineColor = jsonObject["lineColor"]?.jsonPrimitive?.content ?: ""
    }
}

// PoseEntity
fun PoseEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
    }
}

fun JsonObject.toPoseEntity(): PoseEntity {
    return PoseEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
    }
}

// RqtPlotEntity
fun RqtPlotEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("fieldPath", fieldPath)
    }
}

fun JsonObject.toRqtPlotEntity(): RqtPlotEntity {
    return RqtPlotEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        fieldPath = jsonObject["fieldPath"]?.jsonPrimitive?.content ?: ""
    }
}

// TouchGoalEntity
fun TouchGoalEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        topic?.let {
            buildJsonObject {
                put("name", it.name)
                put("type", it.type)
            }
        }?.let { put("topic", it) }
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("publishRate", publishRate)
        put("immediatePublish", immediatePublish)
    }
}

fun JsonObject.toTouchGoalEntity(): TouchGoalEntity {
    return TouchGoalEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        topic = jsonObject["topic"]?.jsonObject?.let {
            Topic(
                name = it["name"]?.jsonPrimitive?.content ?: "",
                type = it["type"]?.jsonPrimitive?.content ?: ""
            )
        }
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        publishRate = jsonObject["publishRate"]?.jsonPrimitive?.float ?: 0f
        immediatePublish = jsonObject["immediatePublish"]?.jsonPrimitive?.boolean ?: false
    }
}

// Viz2DEntity
fun Viz2DEntity.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("name", name)
        put("type", type)
        put("configId", configId)
        put("creationTime", creationTime)
        put("validMessage", validMessage)
        put("childEntities", buildJsonArray {
            childEntities.forEach { add(it.toJson()) }
        })
        put("posX", posX)
        put("posY", posY)
        put("width", width)
        put("height", height)
        put("frame", frame)
    }
}

fun JsonObject.toViz2DEntity(): Viz2DEntity {
    return Viz2DEntity().apply {
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L
        name = jsonObject["name"]?.jsonPrimitive?.content ?: ""
        type = jsonObject["type"]?.jsonPrimitive?.content ?: ""
        configId = jsonObject["configId"]?.jsonPrimitive?.long ?: 0L
        creationTime = jsonObject["creationTime"]?.jsonPrimitive?.long ?: 0L
        validMessage = jsonObject["validMessage"]?.jsonPrimitive?.boolean ?: false
        childEntities = ArrayList(jsonObject["childEntities"]?.jsonArray?.map { it.jsonObject.toBaseEntity() } ?: listOf())
        posX = jsonObject["posX"]?.jsonPrimitive?.int ?: 0
        posY = jsonObject["posY"]?.jsonPrimitive?.int ?: 0
        width = jsonObject["width"]?.jsonPrimitive?.int ?: 0
        height = jsonObject["height"]?.jsonPrimitive?.int ?: 0
        frame = jsonObject["frame"]?.jsonPrimitive?.content ?: ""
    }
}