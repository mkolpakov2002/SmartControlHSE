import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.junit.Assert.assertArrayEquals
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ArduinoPacket
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ArduinoPacketConfiguration
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ByteType
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassFromDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassFromDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassToDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassToDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TurnOfCommand
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeFromDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeFromDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeOfCommand
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeOfCommandWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeOfMove
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeOfMoveWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeToDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeToDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.toArduinoPacket
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.toArduinoPacketConfiguration
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.toJson
import kotlin.test.Test
import kotlin.test.assertEquals

class ArduinoTest{
    @Test
    fun `test packet creation`() {
        // Создаем конфигурацию пакета
        val packetConfiguration = ArduinoPacketConfiguration().apply {
            addByteType(ByteType.CLASS_FROM_DEVICE)
            addByteType(ByteType.TYPE_FROM_DEVICE)
            addByteType(ByteType.CLASS_TO_DEVICE)
            addByteType(ByteType.TYPE_TO_DEVICE)
            addByteType(ByteType.TYPE_OF_COMMAND)
            addByteType(ByteType.TYPE_OF_MOVE)
            setDefaultByte(ByteType.CLASS_FROM_DEVICE,
                ClassFromDeviceWrapper(ClassFromDevice.ARDUINO.codifiedEnum(), 0x65))
            setDefaultByte(ByteType.TYPE_FROM_DEVICE,
                TypeFromDeviceWrapper(TypeFromDevice.ANTHROPOMORPHIC.codifiedEnum(), 0x7D))
            setDefaultByte(ByteType.CLASS_TO_DEVICE,
                ClassToDeviceWrapper(ClassToDevice.COMPUTER.codifiedEnum(), 0x7E))
            setDefaultByte(ByteType.TYPE_TO_DEVICE,
                TypeToDeviceWrapper(TypeToDevice.COMPUTER.codifiedEnum(), 0x01))
            setDefaultByte(ByteType.TYPE_OF_COMMAND,
                TypeOfCommandWrapper(TypeOfCommand.TYPE_MOVE.codifiedEnum(), 0x0A))
        }

        // Создаем пакет с пользовательскими данными
        val packet = ArduinoPacket(packetConfiguration).apply {
            setByte(ByteType.TYPE_OF_MOVE, TypeOfMoveWrapper(TypeOfMove.FORWARD.codifiedEnum(), 0x01))
        }
        // Задаем длину пакета
        packetConfiguration.packetLength = 32

        // Ожидаемый массив байтов
        val expectedBytes = byteArrayOf(
            0x65, 0x7D, 0x7E, 0x01, 0x0A, 0x01, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00
        )

        val packetBytes = packet.getBytes()

        // Проверяем, что сформированный пакет соответствует ожидаемому
        assertArrayEquals(expectedBytes, packetBytes)
    }

    @Test
    fun `test packet creation with custom data`() {
        // Создаем конфигурацию пакета
        val packetConfiguration = ArduinoPacketConfiguration().apply {
            addByteType(ByteType.CLASS_FROM_DEVICE)
            addByteType(ByteType.TYPE_FROM_DEVICE)
            addByteType(ByteType.CLASS_TO_DEVICE)
            addByteType(ByteType.TYPE_TO_DEVICE)
            addByteType(ByteType.TYPE_OF_COMMAND)
            addByteType(ByteType.TYPE_OF_MOVE)
            setDefaultByte(ByteType.CLASS_FROM_DEVICE,
                ClassFromDeviceWrapper(ClassFromDevice.ARDUINO.codifiedEnum(), 0x65))
            setDefaultByte(ByteType.TYPE_FROM_DEVICE,
                TypeFromDeviceWrapper(TypeFromDevice.ANTHROPOMORPHIC.codifiedEnum(), 0x7D))
            setDefaultByte(ByteType.CLASS_TO_DEVICE,
                ClassToDeviceWrapper(ClassToDevice.COMPUTER.codifiedEnum(), 0x7E))
            setDefaultByte(ByteType.TYPE_TO_DEVICE,
                TypeToDeviceWrapper(TypeToDevice.COMPUTER.codifiedEnum(), 0x01))
            setDefaultByte(ByteType.TYPE_OF_COMMAND,
                TypeOfCommandWrapper(TypeOfCommand.TYPE_MOVE.codifiedEnum(), 0x0A))
        }

        // Создаем пакет с пользовательскими данными
        val packet = ArduinoPacket(packetConfiguration).apply {
            setByte(ByteType.TYPE_OF_MOVE, TypeOfMoveWrapper(TypeOfMove.FORWARD.codifiedEnum(), 0x41))
        }
        // Задаем длину пакета
        packetConfiguration.packetLength = 32

        // Ожидаемый массив байтов
        val expectedBytes = byteArrayOf(
            0x65, 0x7D, 0x7E, 0x01, 0x0A, 0x41, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00
        )

        // Проверяем, что сформированный пакет соответствует ожидаемому
        assertArrayEquals(expectedBytes, packet.getBytes())
    }

    @Test
    fun `test packet configuration serialization`() {
        // Создаем конфигурацию пакета
        val packetConfiguration = ArduinoPacketConfiguration().apply {
            addByteType(ByteType.CLASS_FROM_DEVICE)
            addByteType(ByteType.TYPE_FROM_DEVICE)
            addByteType(ByteType.CLASS_TO_DEVICE)
            addByteType(ByteType.TYPE_TO_DEVICE)
            addByteType(ByteType.TYPE_OF_COMMAND)
            addByteType(ByteType.TYPE_OF_MOVE)
            setDefaultByte(ByteType.CLASS_FROM_DEVICE,
                ClassFromDeviceWrapper(ClassFromDevice.ARDUINO.codifiedEnum(), 0x65))
            setDefaultByte(ByteType.TYPE_FROM_DEVICE,
                TypeFromDeviceWrapper(TypeFromDevice.ANTHROPOMORPHIC.codifiedEnum(), 0x7D))
            setDefaultByte(ByteType.CLASS_TO_DEVICE,
                ClassToDeviceWrapper(ClassToDevice.COMPUTER.codifiedEnum(), 0x7E))
            setDefaultByte(ByteType.TYPE_TO_DEVICE,
                TypeToDeviceWrapper(TypeToDevice.COMPUTER.codifiedEnum(), 0x01))
            setDefaultByte(ByteType.TYPE_OF_COMMAND,
                TypeOfCommandWrapper(TypeOfCommand.TYPE_MOVE.codifiedEnum(), 0x0A))
        }

        // Сериализуем конфигурацию пакета в JSON
        val json = packetConfiguration.toJson()

        // Десериализуем JSON в конфигурацию пакета
        val deserializedPacketConfiguration = json.toArduinoPacketConfiguration()

        // Ожидаемый JSON
        val expectedJson = deserializedPacketConfiguration.toJson()

        // Проверяем, что сериализованный JSON соответствует ожидаемому
        assertJsonEquals(expectedJson, json)

        assertEquals(packetConfiguration, deserializedPacketConfiguration)
    }

    @Test
    fun `test packet serialization`() {
        // Создаем конфигурацию пакета
        val packetConfiguration = ArduinoPacketConfiguration().apply {
            addByteType(ByteType.CLASS_FROM_DEVICE)
            addByteType(ByteType.TYPE_FROM_DEVICE)
            addByteType(ByteType.CLASS_TO_DEVICE)
            addByteType(ByteType.TYPE_TO_DEVICE)
            addByteType(ByteType.TYPE_OF_COMMAND)
            addByteType(ByteType.TYPE_OF_MOVE)
            setDefaultByte(ByteType.CLASS_FROM_DEVICE,
                ClassFromDeviceWrapper(ClassFromDevice.ARDUINO.codifiedEnum(), 0x65))
            setDefaultByte(ByteType.TYPE_FROM_DEVICE,
                TypeFromDeviceWrapper(TypeFromDevice.ANTHROPOMORPHIC.codifiedEnum(), 0x7D))
            setDefaultByte(ByteType.CLASS_TO_DEVICE,
                ClassToDeviceWrapper(ClassToDevice.COMPUTER.codifiedEnum(), 0x7E))
            setDefaultByte(ByteType.TYPE_TO_DEVICE,
                TypeToDeviceWrapper(TypeToDevice.COMPUTER.codifiedEnum(), 0x01))
            setDefaultByte(ByteType.TYPE_OF_COMMAND,
                TypeOfCommandWrapper(TypeOfCommand.TYPE_MOVE.codifiedEnum(), 0x0A))
        }

        // Создаем пакет с пользовательскими данными
        val packet = ArduinoPacket(packetConfiguration).apply {
            setByte(ByteType.TYPE_OF_MOVE, TypeOfMoveWrapper(TypeOfMove.FORWARD.codifiedEnum(), 0x41))
        }

        // Сериализуем пакет в JSON
        val json = packet.toJson()

        // Десериализуем JSON в пакет
        val deserializedPacket = json.toArduinoPacket()

        // Ожидаемый JSON
        val expectedJson = deserializedPacket.toJson()

        // Проверяем, что сериализованный JSON соответствует ожидаемому
        assertJsonEquals(expectedJson, json)

        assertEquals(packet, deserializedPacket)
    }
}