package ru.hse.smart_control.model.entities.universal.scheme.common.arduino

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.smart_control.model.entities.universal.scheme.common.InternetAddress
import ru.hse.smart_control.model.entities.universal.scheme.common.MacAddress

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    encodeDefaults = true
}

fun ArduinoInfo.toJson(): JsonObject {
    return buildJsonObject {
        put("devices", buildJsonArray {
            devices.forEach { device ->
                add(device.toJson())
            }
        })
    }
}

fun JsonObject.toArduinoInfo(): ArduinoInfo {
    return ArduinoInfo(
        devices = this["devices"]?.jsonArray?.map {
            it.jsonObject.toArduinoDeviceObject()
        }?.toMutableList() ?: mutableListOf()
    )
}

fun ArduinoDeviceObject.toJson(): JsonObject {
    return buildJsonObject {
        put("possibilities", buildJsonArray {
            possibilities.forEach { possibility ->
                add(possibility.toJson())
            }
        })
        put("packetConfiguration", packetConfiguration.toJson())
        put("macAddress", macAddress.toJson())
        put("internetAddress", internetAddress.toJson())
    }
}

fun JsonObject.toArduinoDeviceObject(): ArduinoDeviceObject {
    return ArduinoDeviceObject(
        possibilities = this["possibilities"]?.jsonArray?.map { it.toArduinoPossibility() }?.toMutableList() ?: mutableListOf(),
        packetConfiguration = this["packetConfiguration"]?.jsonObject?.toArduinoPacketConfiguration()
            ?: ArduinoPacketConfiguration(),
        macAddress = this["macAddress"]?.jsonObject?.toMacAddress(),
        internetAddress = this["internetAddress"]?.jsonObject?.toInternetAddress()
    )
}

fun JsonObject?.toMacAddress(): MacAddress? {
    if (this == null) { return null }
    return MacAddress(
        address = this["address"]?.jsonPrimitive?.content ?: "00:11:22:33:44:55",
    )
}

fun MacAddress?.toJson(): JsonElement {
    if (this == null) { return JsonNull }
    return buildJsonObject {
        put("address", address)
    }
}

fun JsonObject?.toInternetAddress(): InternetAddress? {
    if (this == null) { return null }
    return InternetAddress(
        address = this["address"]?.jsonPrimitive?.content ?: "192.168.0.10",
        port = this["port"]?.jsonPrimitive?.int ?: 8080
    )
}

fun InternetAddress?.toJson(): JsonElement {
    if (this == null) { return JsonNull }
    return buildJsonObject {
        put("address", address)
        put("port", port)
    }
}

fun ArduinoPacketConfiguration.toJson(): JsonObject {
    return buildJsonObject {
        put("byteOrder", buildJsonArray {
            byteOrder.forEach { byteType ->
                add(JsonPrimitive(byteType.name))
            }
        })
        put("defaultBytes", buildJsonObject {
            defaultBytes.forEach { (byteType, possibility) ->
                put(byteType.name, possibility.value.toInt())
            }
        })
        put("packetLength", packetLength)
    }
}

fun JsonObject.toArduinoPacketConfiguration(): ArduinoPacketConfiguration {
    return ArduinoPacketConfiguration(
        byteOrder = this["byteOrder"]?.jsonArray?.map { it.jsonPrimitive.content.let { ByteType.valueOf(it) } }?.toMutableList()
            ?: mutableListOf(),
        defaultBytes = this["defaultBytes"]?.jsonObject?.map { (key, value) ->
            ByteType.valueOf(key) to when (ByteType.valueOf(key)) {
                ByteType.CLASS_FROM_DEVICE -> ClassFromDeviceWrapper(
                    classFromDevice = ClassFromDevice.ARDUINO.codifiedEnum(),
                    value = value.jsonPrimitive.int.toByte()
                )
                ByteType.TYPE_FROM_DEVICE -> TypeFromDeviceWrapper(
                    typeFromDevice = TypeFromDevice.ANTHROPOMORPHIC.codifiedEnum(),
                    value = value.jsonPrimitive.int.toByte()
                )
                ByteType.CLASS_TO_DEVICE -> ClassToDeviceWrapper(
                    classToDevice = ClassToDevice.COMPUTER.codifiedEnum(),
                    value = value.jsonPrimitive.int.toByte()
                )
                ByteType.TYPE_TO_DEVICE -> TypeToDeviceWrapper(
                    typeToDevice = TypeToDevice.COMPUTER.codifiedEnum(),
                    value = value.jsonPrimitive.int.toByte()
                )
                ByteType.TYPE_OF_COMMAND -> TypeOfCommandWrapper(
                    typeOfCommand = TypeOfCommand.TYPE_MOVE.codifiedEnum(),
                    value = value.jsonPrimitive.int.toByte()
                )
                else -> error("Unsupported ByteType")
            }
        }?.toMap()?.toMutableMap() ?: mutableMapOf(),
        packetLength = this["packetLength"]?.jsonPrimitive?.let {
        if (it == JsonNull) {
            null
        } else {
            it.int
        }
    }
    )
}

fun ArduinoPacket.toJson(): JsonObject {
    return buildJsonObject {
        put("configuration", configuration.toJson())
        put("customBytes", buildJsonObject {
            customBytes.forEach { (byteType, possibility) ->
                put(byteType.name, possibility.toJson())
            }
        })
    }
}

fun JsonObject.toArduinoPacket(): ArduinoPacket {
    return ArduinoPacket(
        configuration = this["configuration"]?.jsonObject?.toArduinoPacketConfiguration()
            ?: ArduinoPacketConfiguration(),
        customBytes = this["customBytes"]?.jsonObject?.map { (key, value) ->
            ByteType.valueOf(key) to value.toArduinoPossibility()
        }?.toMap()?.toMutableMap() ?: mutableMapOf()
    )
}

fun ArduinoPossibility.toJson(): JsonElement {
    return when (this) {
        is CustomByteWrapper -> buildJsonObject {
            put("name", name)
            put("value", value.toInt())
        }
        is ClassFromDeviceWrapper -> buildJsonObject {
            put("classFromDevice", classFromDevice.code())
            put("value", value.toInt())
        }
        is TypeFromDeviceWrapper -> buildJsonObject {
            put("typeFromDevice", typeFromDevice.code())
            put("value", value.toInt())
        }
        is ClassToDeviceWrapper -> buildJsonObject {
            put("classToDevice", classToDevice.code())
            put("value", value.toInt())
        }
        is TypeToDeviceWrapper -> buildJsonObject {
            put("typeToDevice", typeToDevice.code())
            put("value", value.toInt())
        }
        is TurnOfCommandWrapper -> buildJsonObject {
            put("turnOfCommand", turnOfCommand.code())
            put("value", value.toInt())
        }
        is TypeOfCommandWrapper -> buildJsonObject {
            put("typeOfCommand", typeOfCommand.code())
            put("value", value.toInt())
        }
        is TypeOfMoveWrapper -> buildJsonObject {
            put("typeOfMove", typeOfMove.code())
            put("value", value.toInt())
        }
    }
}

fun JsonElement.toArduinoPossibility(): ArduinoPossibility {
    return when (this) {
        is JsonObject -> {
            when {
                containsKey("name") -> CustomByteWrapper(
                    name = this["name"]?.jsonPrimitive?.content ?: "",
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("classFromDevice") -> ClassFromDeviceWrapper(
                    classFromDevice = this["classFromDevice"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: ClassFromDevice.NO_CLASS.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("typeFromDevice") -> TypeFromDeviceWrapper(
                    typeFromDevice = this["typeFromDevice"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: TypeFromDevice.NO_TYPE.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("classToDevice") -> ClassToDeviceWrapper(
                    classToDevice = this["classToDevice"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: ClassToDevice.NO_CLASS.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("typeToDevice") -> TypeToDeviceWrapper(
                    typeToDevice = this["typeToDevice"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: TypeToDevice.NO_TYPE.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("turnOfCommand") -> TurnOfCommandWrapper(
                    turnOfCommand = this["turnOfCommand"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: TurnOfCommand.NEW_COMMAND.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("typeOfCommand") -> TypeOfCommandWrapper(
                    typeOfCommand = this["typeOfCommand"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: TypeOfCommand.TYPE_MOVE.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                containsKey("typeOfMove") -> TypeOfMoveWrapper(
                    typeOfMove = this["typeOfMove"]?.jsonPrimitive?.content?.codifiedEnum()
                        ?: TypeOfMove.STOP.codifiedEnum(),
                    value = this["value"]?.jsonPrimitive?.int?.toByte() ?: 0
                )
                else -> error("Unknown ArduinoPossibility type")
            }
        }
        else -> error("Invalid JSON type for ArduinoPossibility")
    }
}