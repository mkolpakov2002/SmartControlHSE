package ru.hse.smart_control.model.entities.universal.scheme.common.arduino

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.serializer.codifiedEnumSerializer

sealed interface ArduinoPossibility{
    val byteType: ByteType
    val value: Byte
}

@Serializable
data class CustomByteWrapper(
    val name : String,
    override val value: Byte
) : ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.CUSTOM
}

@Serializable
data class ClassFromDeviceWrapper(
    @Serializable(with = ClassFromDevice.CodifiedSerializer::class)
    val classFromDevice: CodifiedEnum<ClassFromDevice, String>,
    override val value: Byte
) : ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.CLASS_FROM_DEVICE
}

@Serializable
enum class TypeFromDevice(override val code: String) : Codified<String> {
    SPHERE("sphere"),
    ANTHROPOMORPHIC("anthropometric"),
    CUBBIE("cubby"),
    COMPUTER("computer"),
    NO_TYPE("no_type");
    object CodifiedSerializer : KSerializer<CodifiedEnum<TypeFromDevice, String>> by codifiedEnumSerializer()
}

@Serializable
data class TypeFromDeviceWrapper(
    @Serializable(with = TypeFromDevice.CodifiedSerializer::class)
    val typeFromDevice: CodifiedEnum<TypeFromDevice, String>,
    override val value: Byte
): ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.TYPE_FROM_DEVICE
}

@Serializable
enum class ClassToDevice(override val code: String) : Codified<String> {
    ANDROID("android"),
    COMPUTER("computer"),
    ARDUINO("arduino"),
    NO_CLASS("no_class");
    object CodifiedSerializer : KSerializer<CodifiedEnum<ClassToDevice, String>> by codifiedEnumSerializer()
}

@Serializable
data class ClassToDeviceWrapper(
    @Serializable(with = ClassToDevice.CodifiedSerializer::class)
    val classToDevice: CodifiedEnum<ClassToDevice, String>,
    override val value: Byte
): ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.CLASS_TO_DEVICE
}

@Serializable
enum class TypeToDevice(override val code: String) : Codified<String> {
    SPHERE("sphere"),
    ANTHROPOMORPHIC("anthropomorphic"),
    CUBBIE("cubbie"),
    COMPUTER("computer"),
    NO_TYPE("no_type");
    object CodifiedSerializer : KSerializer<CodifiedEnum<TypeToDevice, String>> by codifiedEnumSerializer()
}

@Serializable
data class TypeToDeviceWrapper(
    @Serializable(with = TypeToDevice.CodifiedSerializer::class)
    val typeToDevice: CodifiedEnum<TypeToDevice, String>,
    override val value: Byte
): ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.TYPE_TO_DEVICE
}

@Serializable
enum class TurnOfCommand(override val code: String) : Codified<String> {
    REDO_COMMAND("redo_command"),
    NEW_COMMAND("new_command");
    object CodifiedSerializer : KSerializer<CodifiedEnum<TurnOfCommand, String>> by codifiedEnumSerializer()
}

@Serializable
data class TurnOfCommandWrapper(
    @Serializable(with = TurnOfCommand.CodifiedSerializer::class)
    val turnOfCommand: CodifiedEnum<TurnOfCommand, String>,
    override val value: Byte
): ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.TURN_OF_COMMAND
}

@Serializable
enum class TypeOfCommand(override val code: String) : Codified<String> {
    TYPE_MOVE("type_move"),
    TYPE_TELE("type_tele"),
    TYPE_CALI("type_cali");
    object CodifiedSerializer : KSerializer<CodifiedEnum<TypeOfCommand, String>> by codifiedEnumSerializer()
}

@Serializable
data class TypeOfCommandWrapper(
    @Serializable(with = TypeOfCommand.CodifiedSerializer::class)
    val typeOfCommand: CodifiedEnum<TypeOfCommand, String>,
    override val value: Byte
): ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.TYPE_OF_COMMAND
}

@Serializable
enum class TypeOfMove(override val code: String) : Codified<String> {
    STOP("stop"),
    FORWARD("forward"),
    FORWARD_STOP("forward_stop"),
    BACK("back"),
    BACK_STOP("back_stop"),
    LEFT("left"),
    LEFT_STOP("left_stop"),
    RIGHT("right"),
    RIGHT_STOP("right_stop");
    object CodifiedSerializer : KSerializer<CodifiedEnum<TypeOfMove, String>> by codifiedEnumSerializer()
}

@Serializable
data class TypeOfMoveWrapper(
    @Serializable(with = TypeOfMove.CodifiedSerializer::class)
    val typeOfMove: CodifiedEnum<TypeOfMove, String>,
    override val value: Byte
): ArduinoPossibility{
    override val byteType: ByteType
        get() = ByteType.TYPE_OF_MOVE
}