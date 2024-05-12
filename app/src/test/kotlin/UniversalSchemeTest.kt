import com.schneewittchen.rosandroid.model.entities.ConfigEntity
import com.schneewittchen.rosandroid.model.entities.MasterEntity
import com.schneewittchen.rosandroid.model.entities.SSHEntity
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.smart_control.model.entities.universal.scheme.common.ArduinoTypeConnectionObject
import ru.hse.smart_control.model.entities.universal.scheme.common.RosTypeConnectionObject
import ru.hse.smart_control.model.entities.universal.scheme.common.SmartHomeTypeConnectionObject
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalScheme
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ArduinoDeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ArduinoInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ArduinoPacketConfiguration
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ByteType
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassFromDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassFromDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassToDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.ClassToDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeFromDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeFromDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeOfCommand
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeOfCommandWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeToDevice
import ru.hse.smart_control.model.entities.universal.scheme.common.arduino.TypeToDeviceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.ROSDeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.ros.ROSInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceTypeWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.GroupObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.HouseholdObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.RoomObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.SmartHomeInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.toJson
import ru.hse.smart_control.model.entities.universal.scheme.common.toUniversalScheme
import kotlin.reflect.full.memberProperties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UniversalSchemeTest {
    @Test
    fun `test SmartHome configuration`() {
        val smartHomeInfo = SmartHomeInfo(
            rooms = mutableListOf(
                RoomObject(id = "room1", name = "Living Room", devices = mutableListOf("device1"), householdId = "household1")
            ),
            groups = mutableListOf(
                GroupObject(id = "group1", name = "Lights", aliases = mutableListOf(), type = DeviceTypeWrapper(
                    DeviceType.LIGHT.codifiedEnum()), capabilities = mutableListOf(),
                    devices = mutableListOf("device1"), householdId = "household1")
            ),
            devices = mutableListOf(
                DeviceObject(id = "device1", name = "Smart Bulb", type = DeviceTypeWrapper(
                    DeviceType.LIGHT.codifiedEnum()), externalId = "bulb1",
                    skillId = "skill1", householdId = "household1", room = null,
                    groups = mutableListOf(), capabilities = mutableListOf(),
                    properties = mutableListOf(), aliases = mutableListOf())
            ),
            scenarios = mutableListOf(),
            households = mutableListOf(
                HouseholdObject(id = "household1", name = "My Home", type = "apartment")
            )
        )
        val smartHomeTypeConnectionObject = SmartHomeTypeConnectionObject(
            payload = smartHomeInfo, smartHomeApiUrl = "https://example.com/api")
        val universalScheme = UniversalScheme(id = 1, objects = mutableListOf(smartHomeTypeConnectionObject))

        val json = universalScheme.toJson()
        val deserializedUniversalScheme = json.toUniversalScheme()

        val isEqual = compareObjects(universalScheme, deserializedUniversalScheme)
        assertTrue(isEqual)

        assertEquals(universalScheme, deserializedUniversalScheme)
    }

    @Test
    fun `test ROS configuration`() {
        val rosInfo = ROSDeviceObject(
            configEntity = ConfigEntity().apply {
                name = "ROS Config"
                creationTime = System.currentTimeMillis()
                lastUsed = System.currentTimeMillis()
                isFavourite = true
            },
            masterEntity = MasterEntity().apply {
                configId = 1
                ip = "192.168.1.100"
                port = 11311
            },
            sshEntity = SSHEntity().apply {
                configId = 1
                ip = "192.168.1.100"
                port = 22
                username = "user"
                password = "password"
            },
            uiWidgets = mutableListOf()
        )
        val rosTypeConnectionObject = RosTypeConnectionObject(payload = ROSInfo(devices = mutableListOf(rosInfo)))
        val universalScheme = UniversalScheme(id = 2, objects = mutableListOf(rosTypeConnectionObject))

        val json = universalScheme.toJson()
        val deserializedUniversalScheme = json.toUniversalScheme()

        assertEquals(universalScheme, deserializedUniversalScheme)
    }

    @Test
    fun `test Arduino configuration`() {
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
            packetLength = 32
        }
        val arduinoInfo = ArduinoInfo(mutableListOf(ArduinoDeviceObject(packetConfiguration = packetConfiguration)))
        val arduinoTypeConnectionObject = ArduinoTypeConnectionObject(payload = arduinoInfo)
        val universalScheme = UniversalScheme(id = 3, objects = mutableListOf(arduinoTypeConnectionObject))

        val json = universalScheme.toJson()
        val deserializedUniversalScheme = json.toUniversalScheme()

        assertEquals(universalScheme, deserializedUniversalScheme)
    }

    fun <T : Any> compareObjects(obj1: T?, obj2: T?, path: String = ""): Boolean {
        if (obj1 == null && obj2 == null) return true
        if (obj1 == null || obj2 == null) return false

        val kClass = obj1::class
        val properties = kClass.memberProperties

        for (prop in properties) {
            val value1 = prop.getter.call(obj1)
            val value2 = prop.getter.call(obj2)

            val currentPath = if (path.isEmpty()) prop.name else "$path.${prop.name}"

            if (value1 is List<*> && value2 is List<*>) {
                if (!compareLists(value1 as List<Any>, value2 as List<Any>, currentPath)) {
                    return false
                }
            } else if (value1 is Map<*, *> && value2 is Map<*, *>) {
                if (!compareMaps(value1 as Map<Any, Any>, value2 as Map<Any, Any>, currentPath)) {
                    return false
                }
            } else if (value1 != null && value1::class.isData) {
                if (!compareObjects(value1, value2, currentPath)) {
                    return false
                }
            } else if (value1 != value2) {
                println("Property $currentPath is different:")
                println("Original: $value1, class: ${value1?.javaClass}")
                println("Deserialized: $value2, class: ${value2?.javaClass}")
                return false
            }
        }

        return true
    }

    fun <T : Any> compareLists(list1: List<T>, list2: List<T>, path: String): Boolean {
        if (list1.size != list2.size) {
            println("List $path has different size:")
            println("Original: ${list1.size}")
            println("Deserialized: ${list2.size}")
            return false
        }

        for (i in list1.indices) {
            if (!compareObjects(list1[i], list2[i], "$path[$i]")) {
                return false
            }
        }

        return true
    }

    fun <K : Any, V : Any> compareMaps(map1: Map<K, V>, map2: Map<K, V>, path: String): Boolean {
        if (map1.size != map2.size) {
            println("Map $path has different size:")
            println("Original: ${map1.size}")
            println("Deserialized: ${map2.size}")
            return false
        }

        for ((key, value1) in map1) {
            val value2 = map2[key]
            if (!compareObjects(value1, value2, "$path[$key]")) {
                return false
            }
        }

        return true
    }
}