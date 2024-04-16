package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object CapabilityObjectSerializer : KSerializer<CapabilityObject> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CapabilityObject") {
    }

    override fun serialize(encoder: Encoder, value: CapabilityObject) {
    }

    override fun deserialize(decoder: Decoder): CapabilityObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonObject = input.decodeJsonElement().jsonObject
        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.booleanOrNull
        val type = jsonObject["type"]?.jsonPrimitive?.content?: throw SerializationException("type not found")
        val typeObject = CapabilityType.fromString(type)
        val stateJson = jsonObject["state"]
        val state: CapabilityStateObjectData?
        when (stateJson) {
            null -> state = null
            is JsonNull -> state = null
            else -> {
                state = when (typeObject) {
                    CapabilityType.ON_OFF -> {
                        val instance = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
                        OnOffCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.COLOR_SETTING -> {
                        val instance = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
                        ColorSettingCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.VIDEO_STREAM -> {
                        val instance = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
                        VideoStreamCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.MODE -> {
                        val instance = input.json.decodeFromJsonElement<ModeCapabilityInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<ModeCapabilityMode>(stateJson.jsonObject["value"]!!)
                        ModeCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.RANGE -> {
                        val instance = input.json.decodeFromJsonElement<RangeCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
                        val valueObject = input.json.decodeFromJsonElement<Float>(stateJson.jsonObject["value"]!!)
                        val value = RangeCapabilityStateObjectDataValue(valueObject)
                        RangeCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.TOGGLE -> {
                        val instance = input.json.decodeFromJsonElement<ToggleCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<ToggleCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
                        ToggleCapabilityStateObjectData(instance, value)
                    }
                }
            }
        }
        val lastUpdated = jsonObject["last_updated"]?.jsonPrimitive?.float

        val params: CapabilityParameterObject? = when(val paramsJson = jsonObject["parameters"]) {
            null -> null
            is JsonNull -> null
            else -> when (typeObject) {
                CapabilityType.COLOR_SETTING -> input.json.decodeFromJsonElement<ColorSettingCapabilityParameterObject>(paramsJson)
                CapabilityType.ON_OFF -> input.json.decodeFromJsonElement<OnOffCapabilityParameterObject>(paramsJson)
                CapabilityType.RANGE -> input.json.decodeFromJsonElement<RangeCapabilityParameterObject>(paramsJson)
                CapabilityType.MODE -> input.json.decodeFromJsonElement<ModeCapabilityParameterObject>(paramsJson)
                CapabilityType.TOGGLE -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObject>(paramsJson)
                CapabilityType.VIDEO_STREAM -> input.json.decodeFromJsonElement<VideoStreamCapabilityParameterObject>(paramsJson)
            }
        }

        return CapabilityObject(retrievable = retrievable, type = typeObject, parameters = params, state = state, lastUpdated = lastUpdated)
    }
}

object GroupCapabilityObjectSerializer : KSerializer<GroupCapabilityObject> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GroupCapabilityObject") {
        element("type", String.serializer().descriptor)
        element("retrievable", Boolean.serializer().descriptor)
        element("parameters", CapabilityParameterObject.serializer().descriptor)
        element("state", CapabilityStateObjectData.serializer().descriptor)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: GroupCapabilityObject) {
        val compositeOutput = encoder.beginStructure(descriptor)
        compositeOutput.encodeStringElement(descriptor, 0, value.type.toString())
        compositeOutput.encodeBooleanElement(descriptor, 1, value.retrievable)
        compositeOutput.encodeSerializableElement(descriptor, 2, CapabilityParameterObject.serializer(), value.parameters)
        compositeOutput.encodeNullableSerializableElement(descriptor, 3, CapabilityStateObjectData.serializer(), value.state)
        compositeOutput.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): GroupCapabilityObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonObject = input.decodeJsonElement().jsonObject

        val type = jsonObject["type"]?.jsonPrimitive?.content ?: throw SerializationException("Missing type")
        val typeObject = CapabilityType.fromString(type)
        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing retrievable")

        val paramsJson = jsonObject["parameters"]?.jsonObject ?: throw SerializationException("Missing parameters")
        val params = when (typeObject) {
            CapabilityType.COLOR_SETTING -> input.json.decodeFromJsonElement<ColorSettingCapabilityParameterObject>(paramsJson)
            CapabilityType.ON_OFF -> input.json.decodeFromJsonElement<OnOffCapabilityParameterObject>(paramsJson)
            CapabilityType.RANGE -> input.json.decodeFromJsonElement<RangeCapabilityParameterObject>(paramsJson)
            CapabilityType.MODE -> input.json.decodeFromJsonElement<ModeCapabilityParameterObject>(paramsJson)
            CapabilityType.TOGGLE -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObject>(paramsJson)
            CapabilityType.VIDEO_STREAM -> input.json.decodeFromJsonElement<VideoStreamCapabilityParameterObject>(paramsJson)
        }

        val stateJson = jsonObject["state"]?.jsonObject ?: throw SerializationException("Missing state")
        val state = when (typeObject) {
            CapabilityType.COLOR_SETTING -> input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectData>(stateJson)
            CapabilityType.ON_OFF -> input.json.decodeFromJsonElement<OnOffCapabilityStateObjectData>(stateJson)
            CapabilityType.RANGE -> input.json.decodeFromJsonElement<RangeCapabilityStateObjectData>(stateJson)
            CapabilityType.MODE -> input.json.decodeFromJsonElement<ModeCapabilityStateObjectData>(stateJson)
            CapabilityType.TOGGLE -> input.json.decodeFromJsonElement<ToggleCapabilityStateObjectData>(stateJson)
            CapabilityType.VIDEO_STREAM -> input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectData>(stateJson)
        }

        return GroupCapabilityObject(type = typeObject, retrievable = retrievable, parameters = params, state = state)
    }
}
//
object DeviceCapabilityObjectSerializer : KSerializer<DeviceCapabilityObject> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DeviceCapabilityObject") {
    }

    override fun serialize(encoder: Encoder, value: DeviceCapabilityObject) {
    }

    override fun deserialize(decoder: Decoder): DeviceCapabilityObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonObject = input.decodeJsonElement().jsonObject

        val reportable = jsonObject["reportable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing reportable")
        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.boolean ?: throw SerializationException("Missing retrievable")
        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Missing type")
        val typeObject = CapabilityType.fromString(type)
        val stateJson = jsonObject["state"]
        val state: CapabilityStateObjectData?
        when (stateJson) {
            null -> state = null
            is JsonNull -> state = null
            else -> {
                state = when (typeObject) {
                    CapabilityType.ON_OFF -> {
                        val instance = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<OnOffCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
                        OnOffCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.COLOR_SETTING -> {
                        val instance = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectValue>(stateJson.jsonObject["value"]!!)
                        ColorSettingCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.VIDEO_STREAM -> {
                        val instance = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
                        VideoStreamCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.MODE -> {
                        val instance = input.json.decodeFromJsonElement<ModeCapabilityInstance>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<ModeCapabilityMode>(stateJson.jsonObject["value"]!!)
                        ModeCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.RANGE -> {
                        val instance = input.json.decodeFromJsonElement<RangeCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<RangeCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
                        RangeCapabilityStateObjectData(instance, value)
                    }
                    CapabilityType.TOGGLE -> {
                        val instance = input.json.decodeFromJsonElement<ToggleCapabilityParameterObjectFunction>(stateJson.jsonObject["instance"]!!)
                        val value = input.json.decodeFromJsonElement<ToggleCapabilityStateObjectDataValue>(stateJson.jsonObject["value"]!!)
                        ToggleCapabilityStateObjectData(instance, value)
                    }
                }
            }
        }
        val lastUpdated = jsonObject["last_updated"]?.jsonPrimitive?.float ?: throw SerializationException("Missing lastUpdated")

        val paramsJson = jsonObject["parameters"]?.jsonObject ?: throw SerializationException("Missing parameters")
        val params = when (typeObject) {
            CapabilityType.COLOR_SETTING -> input.json.decodeFromJsonElement<ColorSettingCapabilityParameterObject>(paramsJson)
            CapabilityType.ON_OFF -> input.json.decodeFromJsonElement<OnOffCapabilityParameterObject>(paramsJson)
            CapabilityType.RANGE -> input.json.decodeFromJsonElement<RangeCapabilityParameterObject>(paramsJson)
            CapabilityType.MODE -> input.json.decodeFromJsonElement<ModeCapabilityParameterObject>(paramsJson)
            CapabilityType.TOGGLE -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObject>(paramsJson)
            CapabilityType.VIDEO_STREAM -> input.json.decodeFromJsonElement<VideoStreamCapabilityParameterObject>(paramsJson)
        }

        return DeviceCapabilityObject(reportable = reportable, retrievable = retrievable, type = typeObject, parameters = params, state = state, lastUpdated = lastUpdated)
    }
}

object CapabilityParameterObjectSerializer : JsonContentPolymorphicSerializer<CapabilityParameterObject>(CapabilityParameterObject::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<CapabilityParameterObject> = when {
        "color_model" in element.jsonObject || "temperature_k" in element.jsonObject || "color_scene" in element.jsonObject -> ColorSettingCapabilityParameterObject.serializer()
        "split" in element.jsonObject -> OnOffCapabilityParameterObject.serializer()
        "instance" in element.jsonObject && element.jsonObject["instance"]?.jsonPrimitive?.content == "modes" -> ModeCapabilityParameterObject.serializer()
        "instance" in element.jsonObject && (element.jsonObject["instance"]?.jsonPrimitive?.content == "brightness" || element.jsonObject["instance"]?.jsonPrimitive?.content == "temperature") -> RangeCapabilityParameterObject.serializer()
        "instance" in element.jsonObject && (element.jsonObject["instance"]?.jsonPrimitive?.content == "ionization" || element.jsonObject["instance"]?.jsonPrimitive?.content == "backlight") -> ToggleCapabilityParameterObject.serializer()
        "protocols" in element.jsonObject -> VideoStreamCapabilityParameterObject.serializer()
        else -> throw SerializationException("Unknown CapabilityParameterObject type")
    }
}

@Serializable(with = OnOffCapabilityStateObjectValueSerializer::class)
data class OnOffCapabilityStateObjectValue(val value: Boolean) : CapabilityStateObjectValue

object OnOffCapabilityStateObjectValueSerializer : KSerializer<OnOffCapabilityStateObjectValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OnOffCapabilityStateObjectValue", PrimitiveKind.BOOLEAN)

    override fun serialize(encoder: Encoder, value: OnOffCapabilityStateObjectValue) {
        encoder.encodeBoolean(value.value)
    }

    override fun deserialize(decoder: Decoder): OnOffCapabilityStateObjectValue {
        return when (decoder) {
            is JsonDecoder -> {
                when (val element = decoder.decodeJsonElement()) {
                    is JsonPrimitive -> OnOffCapabilityStateObjectValue(element.boolean)
                    is JsonObject -> OnOffCapabilityStateObjectValue(element["value"]?.jsonPrimitive?.booleanOrNull ?: throw SerializationException("Expected 'value' field"))
                    else -> throw SerializationException("Expected JsonObject or JsonPrimitive")
                }
            }
            else -> OnOffCapabilityStateObjectValue(decoder.decodeBoolean())
        }
    }
}

class ColorSettingCapabilityStateObjectValueSerializer : KSerializer<ColorSettingCapabilityStateObjectValue> {
    private val objectSceneSerializer = ColorSettingCapabilityStateObjectValueObjectScene.serializer()
    private val objectHSVSerializer = ColorSettingCapabilityStateObjectValueObjectHSV.serializer()
    private val integerSerializer = ColorSettingCapabilityStateObjectValueInteger.serializer()

    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("ColorSettingCapabilityStateObjectValue", PolymorphicKind.SEALED) {
        element("ObjectScene", objectSceneSerializer.descriptor)
        element("ObjectHSV", objectHSVSerializer.descriptor)
        element("Integer", integerSerializer.descriptor)
    }

    override fun deserialize(decoder: Decoder): ColorSettingCapabilityStateObjectValue {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        return when (val jsonElement = jsonDecoder.decodeJsonElement()) {
            is JsonObject -> when {
                "scene" in jsonElement -> jsonDecoder.json.decodeFromJsonElement(objectSceneSerializer, jsonElement)
                "h" in jsonElement && "s" in jsonElement && "v" in jsonElement -> jsonDecoder.json.decodeFromJsonElement(objectHSVSerializer, jsonElement)
                else -> throw SerializationException("Unknown ColorSettingCapabilityStateObjectValue type")
            }
            is JsonPrimitive -> {
                if (jsonElement.content.toIntOrNull() != null) {
                    val intValue = jsonElement.int
                    ColorSettingCapabilityStateObjectValueInteger(intValue)
                } else {
                    throw SerializationException("Expected integer value for ColorSettingCapabilityStateObjectValueInteger")
                }
            }
            else -> throw SerializationException("Unknown ColorSettingCapabilityStateObjectValue type")
        }
    }

    override fun serialize(encoder: Encoder, value: ColorSettingCapabilityStateObjectValue) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("Expected JsonEncoder")

        when (value) {
            is ColorSettingCapabilityStateObjectValueObjectScene -> jsonEncoder.encodeSerializableValue(objectSceneSerializer, value)
            is ColorSettingCapabilityStateObjectValueObjectHSV -> jsonEncoder.encodeSerializableValue(objectHSVSerializer, value)
            is ColorSettingCapabilityStateObjectValueInteger -> jsonEncoder.encodeJsonElement(JsonPrimitive(value.value))
        }
    }
}

object PropertyParameterObjectSerializer : KSerializer<PropertyParameterObject> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PropertyParameterObject")

    override fun deserialize(decoder: Decoder): PropertyParameterObject {
        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonElement = jsonDecoder.decodeJsonElement()
        val instance = jsonElement.jsonObject["instance"]?.jsonPrimitive?.content
        return when (instance) {
            "float" -> jsonDecoder.json.decodeFromJsonElement(FloatPropertyParameterObject.serializer(), jsonElement)
            else -> jsonDecoder.json.decodeFromJsonElement(EventPropertyParameterObject.serializer(), jsonElement)
        }
    }

    override fun serialize(encoder: Encoder, value: PropertyParameterObject) {
        val jsonEncoder = encoder as? JsonEncoder ?: throw SerializationException("Expected JsonEncoder")
        when (value) {
            is FloatPropertyParameterObject -> jsonEncoder.encodeSerializableValue(FloatPropertyParameterObject.serializer(), value)
            is EventPropertyParameterObject -> jsonEncoder.encodeSerializableValue(EventPropertyParameterObject.serializer(), value)
        }
    }
}

object CapabilityTypeSerializer : KSerializer<CapabilityType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CapabilityType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): CapabilityType {
        val value = decoder.decodeString()
        return CapabilityType.fromString(value)
    }

    override fun serialize(encoder: Encoder, value: CapabilityType) {
        encoder.encodeString(value.name)
    }
}

object CapabilityActionResultObjectSerializer : KSerializer<CapabilityActionResultObject> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CapabilityActionResultObject") {
        element("type", String.serializer().descriptor)
        element("state", StateResultObject.serializer().descriptor)
    }

    override fun serialize(encoder: Encoder, value: CapabilityActionResultObject) {
        // Serialization logic, if needed
    }

    override fun deserialize(decoder: Decoder): CapabilityActionResultObject {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonObject = input.decodeJsonElement().jsonObject

        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: throw SerializationException("Missing type")
        val typeObject: CapabilityType = CapabilityType.fromString(type)

        val stateJson = jsonObject["state"] ?: throw SerializationException("Missing state")
        val instanceJson = stateJson.jsonObject["instance"] ?: throw SerializationException("Missing instance")

        val instanceObject = when (typeObject) {
            CapabilityType.COLOR_SETTING -> input.json.decodeFromJsonElement<ColorSettingCapabilityStateObjectInstance>(instanceJson)
            CapabilityType.ON_OFF -> input.json.decodeFromJsonElement<OnOffCapabilityStateObjectInstance>(instanceJson)
            CapabilityType.RANGE -> input.json.decodeFromJsonElement<RangeCapabilityParameterObjectFunction>(instanceJson)
            CapabilityType.MODE -> input.json.decodeFromJsonElement<ModeCapabilityInstance>(instanceJson)
            CapabilityType.TOGGLE -> input.json.decodeFromJsonElement<ToggleCapabilityParameterObjectFunction>(instanceJson)
            CapabilityType.VIDEO_STREAM -> input.json.decodeFromJsonElement<VideoStreamCapabilityStateObjectInstance>(instanceJson)
        }
        val actionResult = input.json.decodeFromJsonElement<ActionResult>(stateJson.jsonObject["action_result"]!!)
        val state = StateResultObject(instanceObject, actionResult)

        return CapabilityActionResultObject(typeObject, state)
    }
}