package ru.hse.smart_control.model.entities.universal.scheme.common.arduino

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.serializer.codifiedEnumSerializer
import ru.hse.smart_control.model.entities.universal.scheme.common.InternetAddress
import ru.hse.smart_control.model.entities.universal.scheme.common.MacAddress
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalDeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalPayload

@Serializable
data class ArduinoInfo(
    @SerialName("devices") val devices: MutableList<ArduinoDeviceObject> = mutableListOf()
) : UniversalPayload

@Serializable
data class ArduinoDeviceObject(
    val possibilities: MutableList<ArduinoPossibility> = mutableListOf(),
    val packetConfiguration: ArduinoPacketConfiguration = ArduinoPacketConfiguration(),
    val macAddress: MacAddress? = null,
    val internetAddress: InternetAddress? = null
) : UniversalDeviceObject

@Serializable
data class ArduinoPacketConfiguration(
    val byteOrder: MutableList<ByteType> = mutableListOf(),
    val defaultBytes: MutableMap<ByteType, ArduinoPossibility> = mutableMapOf(),
    var packetLength: Int? = null
) {
    fun addByteType(byteType: ByteType) {
        if (!byteOrder.contains(byteType)) {
            byteOrder.add(byteType)
        }
    }

    fun removeByteType(byteType: ByteType) {
        byteOrder.remove(byteType)
        defaultBytes.remove(byteType)
    }

    fun setDefaultByte(byteType: ByteType, value: ArduinoPossibility) {
        defaultBytes[byteType] = value
    }
}

@Serializable
data class ArduinoPacket(
    val configuration: ArduinoPacketConfiguration,
    val customBytes: MutableMap<ByteType, ArduinoPossibility> = mutableMapOf()
) {

    fun setByte(byteType: ByteType, value: ArduinoPossibility) {
        customBytes[byteType] = value
    }

    fun removeByte(byteType: ByteType) {
        customBytes.remove(byteType)
    }

    fun getBytes(): ByteArray {
        val bytes = mutableListOf<Byte>()
        for (byteType in configuration.byteOrder) {
            val value = customBytes[byteType] ?: configuration.defaultBytes[byteType]
            value?.let {
                val byteValue = when (it) {
                    is CustomByteWrapper -> it.value
                    is ClassFromDeviceWrapper -> it.value
                    is TypeFromDeviceWrapper -> it.value
                    is ClassToDeviceWrapper -> it.value
                    is TypeToDeviceWrapper -> it.value
                    is TurnOfCommandWrapper -> it.value
                    is TypeOfCommandWrapper -> it.value
                    is TypeOfMoveWrapper -> it.value
                }
                bytes.add(byteValue)
            }
        }

        val packetLength = configuration.packetLength
        if (packetLength != null) {
            if (bytes.size > packetLength) {
                throw IllegalStateException("Packet length exceeds the specified length")
            }
            while (bytes.size < packetLength) {
                bytes.add(0x00)
            }
        }

        return bytes.toByteArray()
    }
}

@Serializable
enum class ByteType {
    CLASS_FROM_DEVICE,
    TYPE_FROM_DEVICE,
    CLASS_TO_DEVICE,
    TYPE_TO_DEVICE,
    TURN_OF_COMMAND,
    TYPE_OF_COMMAND,
    TYPE_OF_MOVE,
    CUSTOM
}

@Serializable
enum class ClassFromDevice(override val code: String) : Codified<String> {
    ANDROID("android"),
    COMPUTER("computer"),
    ARDUINO("arduino"),
    NO_CLASS("no_class");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ClassFromDevice, String>> by codifiedEnumSerializer()
}