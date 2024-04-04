package ru.hse.control_system_v2.model.entities.universal.scheme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PropertyType {
    @SerialName("devices.properties.float")
    FLOAT,
    @SerialName("devices.properties.event")
    EVENT;
}

@Serializable
@SerialName("devices.properties.float")
data class FloatPropertyDescription(
    override val type: PropertyType = PropertyType.FLOAT,
    override val retrievable: Boolean,
    override val reportable: Boolean,
    val parameters: FloatPropertyParameters<FloatUnit>,
    override val lastUpdated: Float? = null,
    override val state: PropertyInstanceStateValue? = null
) : PropertyDescription

@Serializable
@SerialName("devices.properties.event")
data class EventPropertyDescription(
    override val type: PropertyType = PropertyType.EVENT,
    override val retrievable: Boolean,
    override val reportable: Boolean,
    val parameters: EventPropertyParameters<EventInstanceEvent>,
    override val lastUpdated: Float? = null,
    override val state: PropertyInstanceStateValue? = null
) : PropertyDescription

@Serializable
sealed interface PropertyDescription : APIModel{
    val type: PropertyType
    val retrievable: Boolean
    val reportable: Boolean
    @SerialName("last_updated")
    val lastUpdated: Float?
    val state: PropertyInstanceStateValue?
}

@Serializable
sealed interface PropertyParameters : APIModel

@Serializable
sealed class PropertyInstance : APIModel

//TODO: add more properties?
@Serializable
sealed interface PropertyValue : APIModel

@Serializable
@SerialName("string")
data class StringPropertyValue(val value: String) : PropertyValue

@Serializable
@SerialName("number")
data class NumberPropertyValue(val value: Int) : PropertyValue

/**
 * Property instance value.
 */
@Serializable
data class PropertyInstanceStateValue(
    val instance: PropertyInstance,
    val value: PropertyValue
) : APIModel

/**
 * Property state for state query and callback requests.
 */
@Serializable
data class PropertyInstanceState(
    val type: PropertyType,
    val state: PropertyInstanceStateValue
) : APIModel