package ru.hse.smart_control.model.entities.universal.scheme.common

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.put
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.toArduinoInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.toJson
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.toJson
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.toROSInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.toJson
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.toSmartHomeInfo

fun UniversalScheme.toJson(): JsonObject {
    return buildJsonObject {
        put("id", id)
        put("objects", buildJsonArray {
            objects.forEach {
                when (it) {
                    is SmartHomeTypeConnectionObject -> add(it.toJson())
                    is RosTypeConnectionObject -> add(it.toJson())
                    is ArduinoTypeConnectionObject -> add(it.toJson())
                }
            }
        })
    }
}

fun JsonObject.toUniversalScheme(): UniversalScheme {
    return UniversalScheme(
        id = jsonObject["id"]?.jsonPrimitive?.long ?: 0L,
        objects = jsonObject["objects"]?.jsonArray?.mapNotNull { objectElement ->
            val jsonObject = objectElement.jsonObject
            when (jsonObject["type"]?.jsonPrimitive?.content) {
                TypeDeviceConnection.SMART_HOME.name -> jsonObject.toSmartHomeTypeConnectionObject()
                TypeDeviceConnection.ROS.name -> jsonObject.toRosTypeConnectionObject()
                TypeDeviceConnection.ARDUINO_BT.name -> jsonObject.toArduinoTypeConnectionObject()
                else -> {
                    throw Exception("Unknown type: ${jsonObject["type"]?.jsonPrimitive?.content}")
                }
            }
        } ?: emptyList()
    )
}

fun SmartHomeTypeConnectionObject.toJson(): JsonObject {
    return buildJsonObject {
        put("type", type.name)
        put("payload", payload?.toJson() ?: JsonNull)
        put("smartHomeApiUrl", smartHomeApiUrl)
    }
}

fun JsonObject.toSmartHomeTypeConnectionObject(): SmartHomeTypeConnectionObject {
    return SmartHomeTypeConnectionObject(
        type = TypeDeviceConnection.valueOf(this["type"]?.jsonPrimitive?.content ?: ""),
        payload = this["payload"]?.jsonObject?.toSmartHomeInfo(),
        smartHomeApiUrl = this["smartHomeApiUrl"]?.jsonPrimitive?.content ?: ""
    )
}

fun RosTypeConnectionObject.toJson(): JsonObject {
    return buildJsonObject {
        put("type", type.name)
        put("payload", payload?.toJson() ?: JsonNull)
    }
}

fun JsonObject.toRosTypeConnectionObject(): RosTypeConnectionObject {
    return RosTypeConnectionObject(
        type = TypeDeviceConnection.valueOf(this["type"]?.jsonPrimitive?.content ?: ""),
        payload = this["payload"]?.jsonObject?.toROSInfo()
    )
}

fun ArduinoTypeConnectionObject.toJson(): JsonObject {
    return buildJsonObject {
        put("type", type.name)
        put("payload", payload?.toJson() ?: JsonNull)
    }
}

fun JsonObject.toArduinoTypeConnectionObject(): ArduinoTypeConnectionObject {
    return ArduinoTypeConnectionObject(
        type = TypeDeviceConnection.valueOf(this["type"]?.jsonPrimitive?.content ?: ""),
        payload = this["payload"]?.jsonObject?.toArduinoInfo()
    )
}
