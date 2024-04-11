package ru.hse.smart_control.model.entities.universal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityState
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.CapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.ColorModel
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValueObjectHSV
import ru.hse.smart_control.model.entities.universal.scheme.ColorSettingCapabilityStateObjectValueObjectScene
import ru.hse.smart_control.model.entities.universal.scheme.DeviceState
import ru.hse.smart_control.model.entities.universal.scheme.DeviceStateResponse
import ru.hse.smart_control.model.entities.universal.scheme.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.ErrorCode
import ru.hse.smart_control.model.entities.universal.scheme.EventFunctions
import ru.hse.smart_control.model.entities.universal.scheme.EventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.EventPropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.FloatFunctions
import ru.hse.smart_control.model.entities.universal.scheme.FloatObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.FloatPropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.MeasurementUnit
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityInstance
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityMode
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.PropertyFunction
import ru.hse.smart_control.model.entities.universal.scheme.PropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.PropertyType
import ru.hse.smart_control.model.entities.universal.scheme.PropertyValue
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObjectFunction
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.SceneObject
import ru.hse.smart_control.model.entities.universal.scheme.Status
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityParameterObjectFunction
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.UserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityParameterObjectStreamProtocol
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectActionResultValue
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectInstance

object CapabilitySerializer : KSerializer<Capability> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Capability") {
        // Описываем структуру класса для сериализации
    }

    override fun serialize(encoder: Encoder, value: Capability) {
        // Сериализация если потребуется
    }

    override fun deserialize(decoder: Decoder): Capability {
        // Десериализация
        val input = decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val jsonObject = input.decodeJsonElement().jsonObject

        val reportable = jsonObject["reportable"]?.jsonPrimitive?.booleanOrNull ?: false
        val retrievable = jsonObject["retrievable"]?.jsonPrimitive?.booleanOrNull ?: false
        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: ""
        val state = jsonObject["state"]?.jsonPrimitive?.contentOrNull
        val lastUpdated = jsonObject["lastUpdated"]?.jsonPrimitive?.longOrNull ?: 0L

        val paramsJson = jsonObject["parameters"]?.jsonObject ?: throw SerializationException("Parameters are missing")

        val params = when (type) {
            "devices.capabilities.color_setting" -> input.json.decodeFromJsonElement<ColorSettingParameters>(paramsJson)
            "devices.capabilities.on_off" -> input.json.decodeFromJsonElement<OnOffParameters>(paramsJson)
            "devices.capabilities.range" -> input.json.decodeFromJsonElement<RangeParameters>(paramsJson)
            else -> throw SerializationException("Unknown Capability type")
        }

        return Capability(reportable, retrievable, type, params, state, lastUpdated)
    }
}

@Serializable(with = CapabilitySerializer::class)
data class Capability(
    val reportable: Boolean,
    val retrievable: Boolean,
    val type: String,
    val parameters: CapabilityParameters,
    val state: String? = null,
    val lastUpdated: Long
)

@Serializable
@Polymorphic
sealed class CapabilityParameters

@Serializable
data class ColorSettingParameters(
    @SerialName("color_model") val colorModel: String,
    @SerialName("temperature_k") val temperatureK: TemperatureRange
) : CapabilityParameters()

@Serializable
data class TemperatureRange(
    val min: Int,
    val max: Int
)

@Serializable
data class OnOffParameters(
    val split: Boolean
) : CapabilityParameters()

@Serializable
data class RangeParameters(
    val instance: String,
    val unit: String,
    @SerialName("random_access") val randomAccess: Boolean,
    val looped: Boolean,
    val range: Range
) : CapabilityParameters()

@Serializable
data class Range(
    val min: Int,
    val max: Int,
    val precision: Int
)

@Serializable
data class CapabilitiesResponse(
    val capabilities: List<Capability>
)

//val module = SerializersModule {
//    polymorphic(CapabilityParameters::class) {
//        subclass(ColorSettingParameters::class, ColorSettingParameters.serializer())
//        subclass(OnOffParameters::class, OnOffParameters.serializer())
//        subclass(RangeParameters::class, RangeParameters.serializer())
//    }
//}
//
//val format = Json { serializersModule = module }

val userInfoJson = """
        {
          "status": "ok",
          "request_id": "9f055dc8-5407-499a-87d7-b31af8ec289f",
          "rooms": [
            {
              "id": "ca82a680-0317-4bec-b92e-5c3dd27c61eb",
              "name": "Балкон",
              "household_id": "c9a8269c-9939-429b-bb56-05f5abae2937",
              "devices": [
                "51e797a4-93cf-4bc4-832e-698b6703467c"
              ]
            },
            {
              "id": "d490747d-862a-4c06-9182-c56641dc00e5",
              "name": "Ванная",
              "household_id": "f80b6641-8880-49d5-be31-1b35745c321a",
              "devices": [
                "d7e57431-7953-49aa-b46e-589495b71986"
              ]
            },
            {
              "id": "fde19064-d853-4c67-be3b-982b7056ebda",
              "name": "Детская",
              "household_id": "c9a8269c-9939-429b-bb56-05f5abae2937",
              "devices": []
            },
            {
              "id": "b7eacc42-fe63-4efe-85a4-57a6d6e7ac61",
              "name": "Гардеробная",
              "household_id": "f80b6641-8880-49d5-be31-1b35745c321a",
              "devices": []
            }
          ],
          "groups": [
            {
              "id": "d7eded8d-bdb4-4541-beba-7bbf88fea853",
              "name": "Освещение",
              "aliases": [],
              "household_id": "f80b6641-8880-49d5-be31-1b35745c321a",
              "type": "devices.types.light",
              "devices": [
                "d7e57431-7953-49aa-b46e-589495b71986"
              ],
              "capabilities": [
                {
                  "retrievable": true,
                  "type": "devices.capabilities.color_setting",
                  "parameters": {
                    "color_model": "hsv",
                    "temperature_k": {
                      "min": 2700,
                      "max": 6500
                    }
                  },
                  "state": {
                    "instance": "temperature_k",
                    "value": 6500
                  }
                },
                {
                  "retrievable": true,
                  "type": "devices.capabilities.on_off",
                  "parameters": {
                    "split": false
                  },
                  "state": {
                    "instance": "on",
                    "value": true
                  }
                }
              ]
            }
          ],
          "devices": [
            {
              "id": "4a7a2b29-3788-4e09-b5ef-387447185c96",
              "name": "Яндекс Лайт",
              "aliases": [],
              "type": "devices.types.smart_speaker.yandex.station.micro",
              "external_id": "L00BN1200M42ZY.yandexmicro",
              "skill_id": "Q",
              "household_id": "f80b6641-8880-49d5-be31-1b35745c321a",
              "room": null,
              "groups": [],
              "capabilities": [],
              "properties": [
                {
                  "type": "devices.properties.event",
                  "reportable": true,
                  "retrievable": false,
                  "parameters": {
                    "instance": "voice_activity",
                    "events": [
                      {
                        "value": "speech_finished"
                      }
                    ]
                  },
                  "state": null,
                  "last_updated": 0
                }
              ],
              "quasar_info": {
                "device_id": "L00BN1200M42ZY",
                "platform": "yandexmicro"
              }
            },
            {
              "id": "51e797a4-93cf-4bc4-832e-698b6703467c",
              "name": "Лампа",
              "aliases": [],
              "type": "devices.types.light",
              "external_id": "bf9159632e4fb1987bi7am",
              "skill_id": "35e2897a-c583-495a-9e33-f5d6f0f4cb49",
              "household_id": "c9a8269c-9939-429b-bb56-05f5abae2937",
              "room": "ca82a680-0317-4bec-b92e-5c3dd27c61eb",
              "groups": [],
              "capabilities": [
                {
                  "reportable": true,
                  "retrievable": true,
                  "type": "devices.capabilities.color_setting",
                  "parameters": {
                    "color_model": "hsv",
                    "temperature_k": {
                      "min": 2700,
                      "max": 6500
                    }
                  },
                  "state": null,
                  "last_updated": 0
                },
                {
                  "reportable": true,
                  "retrievable": true,
                  "type": "devices.capabilities.on_off",
                  "parameters": {
                    "split": false
                  },
                  "state": null,
                  "last_updated": 0
                },
                {
                  "reportable": true,
                  "retrievable": true,
                  "type": "devices.capabilities.range",
                  "parameters": {
                    "instance": "brightness",
                    "unit": "unit.percent",
                    "random_access": true,
                    "looped": false,
                    "range": {
                      "min": 1,
                      "max": 100,
                      "precision": 11
                    }
                  },
                  "state": null,
                  "last_updated": 0
                }
              ],
              "properties": []
            },
            {
              "id": "d7e57431-7953-49aa-b46e-589495b71986",
              "name": "Лампочка",
              "aliases": [],
              "type": "devices.types.light",
              "external_id": "bf6aa28ee7199c5068ab3l",
              "skill_id": "35e2897a-c583-495a-9e33-f5d6f0f4cb49",
              "household_id": "f80b6641-8880-49d5-be31-1b35745c321a",
              "room": "d490747d-862a-4c06-9182-c56641dc00e5",
              "groups": [
                "d7eded8d-bdb4-4541-beba-7bbf88fea853"
              ],
              "capabilities": [
                {
                  "reportable": true,
                  "retrievable": true,
                  "type": "devices.capabilities.color_setting",
                  "parameters": {
                    "color_model": "hsv",
                    "temperature_k": {
                      "min": 2700,
                      "max": 6500
                    }
                  },
                  "state": {
                    "instance": "temperature_k",
                    "value": 6500
                  },
                  "last_updated": 1711625675.9642246
                },
                {
                  "reportable": true,
                  "retrievable": true,
                  "type": "devices.capabilities.on_off",
                  "parameters": {
                    "split": false
                  },
                  "state": {
                    "instance": "on",
                    "value": true
                  },
                  "last_updated": 1711625675.9642246
                }
              ],
              "properties": []
            }
          ],
          "scenarios": [],
          "households": [
            {
              "id": "c9a8269c-9939-429b-bb56-05f5abae2937",
              "name": "Мой дом",
              "type": "households.types.personal"
            },
            {
              "id": "f80b6641-8880-49d5-be31-1b35745c321a",
              "name": "Мой дом Волгоград ",
              "type": "households.types.personal"
            }
          ]
        }
    """.trimIndent()

val deviceStateJson = """
    {
    	"status": "ok",
    	"request_id": "fd9299b8-f927-44f1-8728-8a0b35f1c72a",
    	"id": "lamp-id-1",
    	"name": "Лампочка 1",
    	"aliases": [],
    	"type": "devices.types.light",
    	"state": "online",
    	"groups": [
    		"light-group-id-1"
    	],
    	"room": "room-id-1",
    	"external_id": "external-lamp-id-1",
    	"skill_id": "skill-id-1",
    	"capabilities": [{
    			"retrievable": true,
    			"type": "devices.capabilities.on_off",
    			"parameters": {
    				"split": false
    			},
    			"state": {
    				"instance": "on",
    				"value": false
    			},
    			"last_updated": 1626702522.614036
    		},
    		{
    			"retrievable": true,
    			"type": "devices.capabilities.range",
    			"parameters": {
    				"instance": "brightness",
    				"unit": "unit.percent",
    				"random_access": true,
    				"range": {
    					"min": 1,
    					"max": 100,
    					"precision": 1
    				}
    			},
    			"state": {
    				"instance": "brightness",
    				"value": 100
    			},
    			"last_updated": 1626702522.614036
    		},
    		{
    			"retrievable": true,
    			"type": "devices.capabilities.color_setting",
    			"parameters": {
    				"color_model": "rgb",
    				"temperature_k": {
    					"min": 1700,
    					"max": 6500
    				}
    			},
    			"state": {
    				"instance": "temperature_k",
    				"value": 3400
    			},
    			"last_updated": 1626702522.614036
    		}
    	],
    	"properties": []
    }
""".trimIndent()

fun main() {
    val module = SerializersModule {
        polymorphic(DeviceType::class) {
            defaultDeserializer { DeviceType.serializer() }
            subclass(DeviceType.Light::class)
            subclass(DeviceType.Socket::class)
            subclass(DeviceType.Switch::class)
            subclass(DeviceType.Thermostat::class)
            subclass(DeviceType.ThermostatAC::class)
            subclass(DeviceType.MediaDevice::class)
            subclass(DeviceType.MediaDeviceTV::class)
            subclass(DeviceType.MediaDeviceTVBox::class)
            subclass(DeviceType.MediaDeviceReceiver::class)
            subclass(DeviceType.Cooking::class)
            subclass(DeviceType.CoffeeMaker::class)
            subclass(DeviceType.Kettle::class)
            subclass(DeviceType.Multicooker::class)
            subclass(DeviceType.Openable::class)
            subclass(DeviceType.OpenableCurtain::class)
            subclass(DeviceType.Humidifier::class)
            subclass(DeviceType.Purifier::class)
            subclass(DeviceType.VacuumCleaner::class)
            subclass(DeviceType.WashingMachine::class)
            subclass(DeviceType.Dishwasher::class)
            subclass(DeviceType.Iron::class)
            subclass(DeviceType.Sensor::class)
            subclass(DeviceType.SensorMotion::class)
            subclass(DeviceType.SensorDoor::class)
            subclass(DeviceType.SensorWindow::class)
            subclass(DeviceType.SensorWaterLeak::class)
            subclass(DeviceType.SensorSmoke::class)
            subclass(DeviceType.SensorGas::class)
            subclass(DeviceType.SensorVibration::class)
            subclass(DeviceType.SensorButton::class)
            subclass(DeviceType.SensorIllumination::class)
            subclass(DeviceType.Other::class)
            subclass(DeviceType.CustomDeviceType::class)
        }
        polymorphic(CapabilityParameterObject::class) {
            defaultDeserializer { CapabilityParameterObject.serializer() }
            subclass(ColorSettingCapabilityParameterObject::class)
            subclass(OnOffCapabilityParameterObject::class)
            subclass(ModeCapabilityParameterObject::class)
            subclass(RangeCapabilityParameterObject::class)
            subclass(ToggleCapabilityParameterObject::class)
            subclass(VideoStreamCapabilityParameterObject::class)
        }
        polymorphic(PropertyParameterObject::class) {
            defaultDeserializer { PropertyParameterObject.serializer() }
            subclass(FloatPropertyParameterObject::class)
            subclass(EventPropertyParameterObject::class)
        }
        polymorphic(CapabilityStateObjectData::class) {
            defaultDeserializer { CapabilityStateObjectData.serializer() }
            subclass(ColorSettingCapabilityStateObjectData::class)
            subclass(OnOffCapabilityStateObjectData::class)
            subclass(ModeCapabilityStateObjectData::class)
            subclass(RangeCapabilityStateObjectData::class)
            subclass(ToggleCapabilityStateObjectData::class)
            subclass(VideoStreamCapabilityStateObjectData::class)
        }
        polymorphic(CapabilityStateObjectInstance::class) {
            defaultDeserializer { CapabilityStateObjectInstance.serializer() }
            subclass(ColorSettingCapabilityStateObjectInstance::class)
            subclass(OnOffCapabilityStateObjectInstance::class)
            subclass(ModeCapabilityInstance::class)
            subclass(RangeCapabilityParameterObjectFunction::class)
            subclass(ToggleCapabilityParameterObjectFunction::class)
            subclass(VideoStreamCapabilityStateObjectInstance::class)
        }
        polymorphic(ColorSettingCapabilityStateObjectInstance::class) {
            defaultDeserializer { ColorSettingCapabilityStateObjectInstance.serializer() }
            subclass(ColorSettingCapabilityStateObjectInstance.Base::class, ColorSettingCapabilityStateObjectInstance.Base.serializer())
            subclass(ColorSettingCapabilityStateObjectInstance.RGB::class, ColorSettingCapabilityStateObjectInstance.RGB.serializer())
            subclass(ColorSettingCapabilityStateObjectInstance.HSV::class, ColorSettingCapabilityStateObjectInstance.HSV.serializer())
            subclass(ColorSettingCapabilityStateObjectInstance.TemperatureK::class, ColorSettingCapabilityStateObjectInstance.TemperatureK.serializer())
            subclass(ColorSettingCapabilityStateObjectInstance.Scene::class, ColorSettingCapabilityStateObjectInstance.Scene.serializer())
        }
        polymorphic(VideoStreamCapabilityStateObjectInstance::class) {
            defaultDeserializer { VideoStreamCapabilityStateObjectInstance.serializer() }
            subclass(VideoStreamCapabilityStateObjectInstance.GetStream::class, VideoStreamCapabilityStateObjectInstance.GetStream.serializer())
        }
        polymorphic(OnOffCapabilityStateObjectInstance::class) {
            defaultDeserializer { OnOffCapabilityStateObjectInstance.serializer() }
            subclass(OnOffCapabilityStateObjectInstance.On::class, OnOffCapabilityStateObjectInstance.On.serializer())
        }
        polymorphic(Status::class) {
            defaultDeserializer { Status.serializer() }
            subclass(Status.Done::class, Status.Done.serializer())
            subclass(Status.Error::class, Status.Error.serializer())
            subclass(Status.CustomStatus::class, Status.CustomStatus.serializer())
        }
        polymorphic(ErrorCode::class) {
            defaultDeserializer { ErrorCode.serializer() }
            subclass(ErrorCode.DoorOpen::class, ErrorCode.DoorOpen.serializer())
            subclass(ErrorCode.LidOpen::class, ErrorCode.LidOpen.serializer())
            subclass(ErrorCode.RemoteControlDisabled::class, ErrorCode.RemoteControlDisabled.serializer())
            subclass(ErrorCode.NotEnoughWater::class, ErrorCode.NotEnoughWater.serializer())
            subclass(ErrorCode.LowChargeLevel::class, ErrorCode.LowChargeLevel.serializer())
            subclass(ErrorCode.ContainerFull::class, ErrorCode.ContainerFull.serializer())
            subclass(ErrorCode.ContainerEmpty::class, ErrorCode.ContainerEmpty.serializer())
            subclass(ErrorCode.DripTrayFull::class, ErrorCode.DripTrayFull.serializer())
            subclass(ErrorCode.DeviceStuck::class, ErrorCode.DeviceStuck.serializer())
            subclass(ErrorCode.DeviceOff::class, ErrorCode.DeviceOff.serializer())
            subclass(ErrorCode.FirmwareOutOfDate::class, ErrorCode.FirmwareOutOfDate.serializer())
            subclass(ErrorCode.NotEnoughDetergent::class, ErrorCode.NotEnoughDetergent.serializer())
            subclass(ErrorCode.HumanInvolvementNeeded::class, ErrorCode.HumanInvolvementNeeded.serializer())
            subclass(ErrorCode.DeviceUnreachable::class, ErrorCode.DeviceUnreachable.serializer())
            subclass(ErrorCode.DeviceBusy::class, ErrorCode.DeviceBusy.serializer())
            subclass(ErrorCode.InternalError::class, ErrorCode.InternalError.serializer())
            subclass(ErrorCode.InvalidAction::class, ErrorCode.InvalidAction.serializer())
            subclass(ErrorCode.InvalidValue::class, ErrorCode.InvalidValue.serializer())
            subclass(ErrorCode.NotSupportedInCurrentMode::class, ErrorCode.NotSupportedInCurrentMode.serializer())
            subclass(ErrorCode.AccountLinkingError::class, ErrorCode.AccountLinkingError.serializer())
            subclass(ErrorCode.DeviceNotFound::class, ErrorCode.DeviceNotFound.serializer())
        }
        polymorphic(VideoStreamCapabilityParameterObjectStreamProtocol::class) {
            defaultDeserializer { VideoStreamCapabilityParameterObjectStreamProtocol.serializer() }
            subclass(VideoStreamCapabilityParameterObjectStreamProtocol.HLS::class, VideoStreamCapabilityParameterObjectStreamProtocol.HLS.serializer())
            subclass(VideoStreamCapabilityParameterObjectStreamProtocol.RTMP::class, VideoStreamCapabilityParameterObjectStreamProtocol.RTMP.serializer())
        }
//        polymorphic(CapabilityState::class) {
//            defaultDeserializer { CapabilityStateObjectData.serializer() }
//            subclass(CapabilityStateObjectData::class)
//        }
        polymorphic(ToggleCapabilityParameterObjectFunction::class) {
            defaultDeserializer { ToggleCapabilityParameterObjectFunction.serializer() }
            subclass(ToggleCapabilityParameterObjectFunction.Backlight::class, ToggleCapabilityParameterObjectFunction.Backlight.serializer())
            subclass(ToggleCapabilityParameterObjectFunction.ControlsLocked::class, ToggleCapabilityParameterObjectFunction.ControlsLocked.serializer())
            subclass(ToggleCapabilityParameterObjectFunction.Ionization::class, ToggleCapabilityParameterObjectFunction.Ionization.serializer())
            subclass(ToggleCapabilityParameterObjectFunction.KeepWarm::class, ToggleCapabilityParameterObjectFunction.KeepWarm.serializer())
            subclass(ToggleCapabilityParameterObjectFunction.Mute::class, ToggleCapabilityParameterObjectFunction.Mute.serializer())
            subclass(ToggleCapabilityParameterObjectFunction.Oscillation::class, ToggleCapabilityParameterObjectFunction.Oscillation.serializer())
            subclass(ToggleCapabilityParameterObjectFunction.Pause::class, ToggleCapabilityParameterObjectFunction.Pause.serializer())
        }
        polymorphic(RangeCapabilityParameterObjectFunction::class) {
            defaultDeserializer { RangeCapabilityParameterObjectFunction.serializer() }
            subclass(RangeCapabilityParameterObjectFunction.Brightness::class, RangeCapabilityParameterObjectFunction.Brightness.serializer())
            subclass(RangeCapabilityParameterObjectFunction.Channel::class, RangeCapabilityParameterObjectFunction.Channel.serializer())
            subclass(RangeCapabilityParameterObjectFunction.Humidity::class, RangeCapabilityParameterObjectFunction.Humidity.serializer())
            subclass(RangeCapabilityParameterObjectFunction.Open::class, RangeCapabilityParameterObjectFunction.Open.serializer())
            subclass(RangeCapabilityParameterObjectFunction.Temperature::class, RangeCapabilityParameterObjectFunction.Temperature.serializer())
            subclass(RangeCapabilityParameterObjectFunction.Volume::class, RangeCapabilityParameterObjectFunction.Volume.serializer())
        }
        polymorphic(ModeCapabilityInstance::class) {
            defaultDeserializer { ModeCapabilityInstance.serializer() }
            subclass(ModeCapabilityInstance.CleanupMode::class, ModeCapabilityInstance.CleanupMode.serializer())
            subclass(ModeCapabilityInstance.CoffeeMode::class, ModeCapabilityInstance.CoffeeMode.serializer())
            subclass(ModeCapabilityInstance.Dishwashing::class, ModeCapabilityInstance.Dishwashing.serializer())
            subclass(ModeCapabilityInstance.FanSpeed::class, ModeCapabilityInstance.FanSpeed.serializer())
            subclass(ModeCapabilityInstance.Heat::class, ModeCapabilityInstance.Heat.serializer())
            subclass(ModeCapabilityInstance.InputSource::class, ModeCapabilityInstance.InputSource.serializer())
            subclass(ModeCapabilityInstance.Program::class, ModeCapabilityInstance.Program.serializer())
            subclass(ModeCapabilityInstance.Swing::class, ModeCapabilityInstance.Swing.serializer())
            subclass(ModeCapabilityInstance.TeaMode::class, ModeCapabilityInstance.TeaMode.serializer())
            subclass(ModeCapabilityInstance.Thermostat::class, ModeCapabilityInstance.Thermostat.serializer())
            subclass(ModeCapabilityInstance.WorkSpeed::class, ModeCapabilityInstance.WorkSpeed.serializer())
        }
        polymorphic(ModeCapabilityMode::class) {
            defaultDeserializer { ModeCapabilityMode.serializer() }
            subclass(ModeCapabilityMode.Auto::class, ModeCapabilityMode.Auto.serializer())
            subclass(ModeCapabilityMode.Eco::class, ModeCapabilityMode.Eco.serializer())
            subclass(ModeCapabilityMode.Smart::class, ModeCapabilityMode.Smart.serializer())
            subclass(ModeCapabilityMode.Turbo::class, ModeCapabilityMode.Turbo.serializer())
            subclass(ModeCapabilityMode.Cool::class, ModeCapabilityMode.Cool.serializer())
            subclass(ModeCapabilityMode.Dry::class, ModeCapabilityMode.Dry.serializer())
            subclass(ModeCapabilityMode.FanOnly::class, ModeCapabilityMode.FanOnly.serializer())
            subclass(ModeCapabilityMode.Heat::class, ModeCapabilityMode.Heat.serializer())
            subclass(ModeCapabilityMode.Preheat::class, ModeCapabilityMode.Preheat.serializer())
            subclass(ModeCapabilityMode.High::class, ModeCapabilityMode.High.serializer())
            subclass(ModeCapabilityMode.Low::class, ModeCapabilityMode.Low.serializer())
            subclass(ModeCapabilityMode.Medium::class, ModeCapabilityMode.Medium.serializer())
            subclass(ModeCapabilityMode.Max::class, ModeCapabilityMode.Max.serializer())
            subclass(ModeCapabilityMode.Min::class, ModeCapabilityMode.Min.serializer())
            subclass(ModeCapabilityMode.Fast::class, ModeCapabilityMode.Fast.serializer())
            subclass(ModeCapabilityMode.Slow::class, ModeCapabilityMode.Slow.serializer())
            subclass(ModeCapabilityMode.Express::class, ModeCapabilityMode.Express.serializer())
            subclass(ModeCapabilityMode.Normal::class, ModeCapabilityMode.Normal.serializer())
            subclass(ModeCapabilityMode.Quiet::class, ModeCapabilityMode.Quiet.serializer())
            subclass(ModeCapabilityMode.Horizontal::class, ModeCapabilityMode.Horizontal.serializer())
            subclass(ModeCapabilityMode.Stationary::class, ModeCapabilityMode.Stationary.serializer())
            subclass(ModeCapabilityMode.Vertical::class, ModeCapabilityMode.Vertical.serializer())
            subclass(ModeCapabilityMode.One::class, ModeCapabilityMode.One.serializer())
            subclass(ModeCapabilityMode.Two::class, ModeCapabilityMode.Two.serializer())
            subclass(ModeCapabilityMode.Three::class, ModeCapabilityMode.Three.serializer())
            subclass(ModeCapabilityMode.Four::class, ModeCapabilityMode.Four.serializer())
            subclass(ModeCapabilityMode.Five::class, ModeCapabilityMode.Five.serializer())
            subclass(ModeCapabilityMode.Six::class, ModeCapabilityMode.Six.serializer())
            subclass(ModeCapabilityMode.Seven::class, ModeCapabilityMode.Seven.serializer())
            subclass(ModeCapabilityMode.Eight::class, ModeCapabilityMode.Eight.serializer())
            subclass(ModeCapabilityMode.Nine::class, ModeCapabilityMode.Nine.serializer())
            subclass(ModeCapabilityMode.Ten::class, ModeCapabilityMode.Ten.serializer())
            subclass(ModeCapabilityMode.Americano::class, ModeCapabilityMode.Americano.serializer())
            subclass(ModeCapabilityMode.Cappuccino::class, ModeCapabilityMode.Cappuccino.serializer())
            subclass(ModeCapabilityMode.Double::class, ModeCapabilityMode.Double.serializer())
            subclass(ModeCapabilityMode.Espresso::class, ModeCapabilityMode.Espresso.serializer())
            subclass(ModeCapabilityMode.DoubleEspresso::class, ModeCapabilityMode.DoubleEspresso.serializer())
            subclass(ModeCapabilityMode.Latte::class, ModeCapabilityMode.Latte.serializer())
            subclass(ModeCapabilityMode.BlackTea::class, ModeCapabilityMode.BlackTea.serializer())
            subclass(ModeCapabilityMode.FlowerTea::class, ModeCapabilityMode.FlowerTea.serializer())
            subclass(ModeCapabilityMode.GreenTea::class, ModeCapabilityMode.GreenTea.serializer())
            subclass(ModeCapabilityMode.HerbalTea::class, ModeCapabilityMode.HerbalTea.serializer())
            subclass(ModeCapabilityMode.OolongTea::class, ModeCapabilityMode.OolongTea.serializer())
            subclass(ModeCapabilityMode.PuerhTea::class, ModeCapabilityMode.PuerhTea.serializer())
            subclass(ModeCapabilityMode.RedTea::class, ModeCapabilityMode.RedTea.serializer())
            subclass(ModeCapabilityMode.WhiteTea::class, ModeCapabilityMode.WhiteTea.serializer())
            subclass(ModeCapabilityMode.Glass::class, ModeCapabilityMode.Glass.serializer())
            subclass(ModeCapabilityMode.Intensive::class, ModeCapabilityMode.Intensive.serializer())
            subclass(ModeCapabilityMode.PreRinse::class, ModeCapabilityMode.PreRinse.serializer())
            subclass(ModeCapabilityMode.Aspic::class, ModeCapabilityMode.Aspic.serializer())
            subclass(ModeCapabilityMode.BabyFood::class, ModeCapabilityMode.BabyFood.serializer())
            subclass(ModeCapabilityMode.Baking::class, ModeCapabilityMode.Baking.serializer())
            subclass(ModeCapabilityMode.Bread::class, ModeCapabilityMode.Bread.serializer())
            subclass(ModeCapabilityMode.Boiling::class, ModeCapabilityMode.Boiling.serializer())
            subclass(ModeCapabilityMode.Cereals::class, ModeCapabilityMode.Cereals.serializer())
            subclass(ModeCapabilityMode.Cheesecake::class, ModeCapabilityMode.Cheesecake.serializer())
            subclass(ModeCapabilityMode.DeepFryer::class, ModeCapabilityMode.DeepFryer.serializer())
            subclass(ModeCapabilityMode.Dessert::class, ModeCapabilityMode.Dessert.serializer())
            subclass(ModeCapabilityMode.Fowl::class, ModeCapabilityMode.Fowl.serializer())
            subclass(ModeCapabilityMode.Frying::class, ModeCapabilityMode.Frying.serializer())
            subclass(ModeCapabilityMode.Macaroni::class, ModeCapabilityMode.Macaroni.serializer())
            subclass(ModeCapabilityMode.MilkPorridge::class, ModeCapabilityMode.MilkPorridge.serializer())
            subclass(ModeCapabilityMode.Multicooker::class, ModeCapabilityMode.Multicooker.serializer())
            subclass(ModeCapabilityMode.Pasta::class, ModeCapabilityMode.Pasta.serializer())
            subclass(ModeCapabilityMode.Pilaf::class, ModeCapabilityMode.Pilaf.serializer())
            subclass(ModeCapabilityMode.Pizza::class, ModeCapabilityMode.Pizza.serializer())
            subclass(ModeCapabilityMode.Sauce::class, ModeCapabilityMode.Sauce.serializer())
            subclass(ModeCapabilityMode.SlowCook::class, ModeCapabilityMode.SlowCook.serializer())
            subclass(ModeCapabilityMode.Soup::class, ModeCapabilityMode.Soup.serializer())
            subclass(ModeCapabilityMode.Steam::class, ModeCapabilityMode.Steam.serializer())
            subclass(ModeCapabilityMode.Stewing::class, ModeCapabilityMode.Stewing.serializer())
            subclass(ModeCapabilityMode.Vacuum::class, ModeCapabilityMode.Vacuum.serializer())
            subclass(ModeCapabilityMode.Yogurt::class, ModeCapabilityMode.Yogurt.serializer())
        }
        polymorphic(SceneObject::class) {
            defaultDeserializer { SceneObject.serializer() }
            subclass(SceneObject.Alarm::class, SceneObject.Alarm.serializer())
            subclass(SceneObject.Alice::class, SceneObject.Alice.serializer())
            subclass(SceneObject.Candle::class, SceneObject.Candle.serializer())
            subclass(SceneObject.Dinner::class, SceneObject.Dinner.serializer())
            subclass(SceneObject.Fantasy::class, SceneObject.Fantasy.serializer())
            subclass(SceneObject.Garland::class, SceneObject.Garland.serializer())
            subclass(SceneObject.Jungle::class, SceneObject.Jungle.serializer())
            subclass(SceneObject.Movie::class, SceneObject.Movie.serializer())
            subclass(SceneObject.Neon::class, SceneObject.Neon.serializer())
            subclass(SceneObject.Night::class, SceneObject.Night.serializer())
            subclass(SceneObject.Ocean::class, SceneObject.Ocean.serializer())
            subclass(SceneObject.Party::class, SceneObject.Party.serializer())
            subclass(SceneObject.Reading::class, SceneObject.Reading.serializer())
            subclass(SceneObject.Rest::class, SceneObject.Rest.serializer())
            subclass(SceneObject.Romance::class, SceneObject.Romance.serializer())
            subclass(SceneObject.Siren::class, SceneObject.Siren.serializer())
            subclass(SceneObject.Sunrise::class, SceneObject.Sunrise.serializer())
            subclass(SceneObject.Sunset::class, SceneObject.Sunset.serializer())
        }
        polymorphic(ColorModel::class) {
            defaultDeserializer { ColorModel.serializer() }
            subclass(ColorModel.RGB::class, ColorModel.RGB.serializer())
            subclass(ColorModel.HSV::class, ColorModel.HSV.serializer())
        }
        polymorphic(DeviceState::class) {
            defaultDeserializer { DeviceState.serializer() }
            subclass(DeviceState.Online::class, DeviceState.Online.serializer())
            subclass(DeviceState.Offline::class, DeviceState.Offline.serializer())
        }
        polymorphic(MeasurementUnit::class) {
            defaultDeserializer { MeasurementUnit.serializer() }
            subclass(MeasurementUnit.Ampere::class, MeasurementUnit.Ampere.serializer())
            subclass(MeasurementUnit.CubicMeter::class, MeasurementUnit.CubicMeter.serializer())
            subclass(MeasurementUnit.Gigacalorie::class, MeasurementUnit.Gigacalorie.serializer())
            subclass(MeasurementUnit.KilowattHour::class, MeasurementUnit.KilowattHour.serializer())
            subclass(MeasurementUnit.Lux::class, MeasurementUnit.Lux.serializer())
            subclass(MeasurementUnit.McgM3::class, MeasurementUnit.McgM3.serializer())
            subclass(MeasurementUnit.Percent::class, MeasurementUnit.Percent.serializer())
            subclass(MeasurementUnit.Ppm::class, MeasurementUnit.Ppm.serializer())
            subclass(MeasurementUnit.Volt::class, MeasurementUnit.Volt.serializer())
            subclass(MeasurementUnit.Watt::class, MeasurementUnit.Watt.serializer())
            subclass(MeasurementUnit.TemperatureCelsius::class, MeasurementUnit.TemperatureCelsius.serializer())
        }
        polymorphic(EventObjectValue::class) {
            defaultDeserializer { EventObjectValue.serializer() }
            subclass(EventObjectValue.Tilt::class, EventObjectValue.Tilt.serializer())
            subclass(EventObjectValue.Fall::class, EventObjectValue.Fall.serializer())
            subclass(EventObjectValue.Vibration::class, EventObjectValue.Vibration.serializer())
            subclass(EventObjectValue.Opened::class, EventObjectValue.Opened.serializer())
            subclass(EventObjectValue.Closed::class, EventObjectValue.Closed.serializer())
            subclass(EventObjectValue.Click::class, EventObjectValue.Click.serializer())
            subclass(EventObjectValue.DoubleClick::class, EventObjectValue.DoubleClick.serializer())
            subclass(EventObjectValue.LongPress::class, EventObjectValue.LongPress.serializer())
            subclass(EventObjectValue.Detected::class, EventObjectValue.Detected.serializer())
            subclass(EventObjectValue.NotDetected::class, EventObjectValue.NotDetected.serializer())
            subclass(EventObjectValue.High::class, EventObjectValue.High.serializer())
            subclass(EventObjectValue.Low::class, EventObjectValue.Low.serializer())
            subclass(EventObjectValue.Normal::class, EventObjectValue.Normal.serializer())
            subclass(EventObjectValue.Empty::class, EventObjectValue.Empty.serializer())
            subclass(EventObjectValue.Dry::class, EventObjectValue.Dry.serializer())
            subclass(EventObjectValue.Leak::class, EventObjectValue.Leak.serializer())
        }
        polymorphic(PropertyType::class) {
            defaultDeserializer { PropertyType.serializer() }
            subclass(PropertyType.Float::class, PropertyType.Float.serializer())
            subclass(PropertyType.Event::class, PropertyType.Event.serializer())
        }
        polymorphic(FloatFunctions::class) {
            defaultDeserializer { FloatFunctions.serializer() }
            subclass(FloatFunctions.Amperage::class, FloatFunctions.Amperage.serializer())
            subclass(FloatFunctions.BatteryLevel::class, FloatFunctions.BatteryLevel.serializer())
            subclass(FloatFunctions.Co2Level::class, FloatFunctions.Co2Level.serializer())
            subclass(FloatFunctions.ElectricityMeter::class, FloatFunctions.ElectricityMeter.serializer())
            subclass(FloatFunctions.FoodLevel::class, FloatFunctions.FoodLevel.serializer())
            subclass(FloatFunctions.GasMeter::class, FloatFunctions.GasMeter.serializer())
            subclass(FloatFunctions.HeatMeter::class, FloatFunctions.HeatMeter.serializer())
            subclass(FloatFunctions.Humidity::class, FloatFunctions.Humidity.serializer())
            subclass(FloatFunctions.Illumination::class, FloatFunctions.Illumination.serializer())
            subclass(FloatFunctions.Meter::class, FloatFunctions.Meter.serializer())
            subclass(FloatFunctions.Pm10Density::class, FloatFunctions.Pm10Density.serializer())
            subclass(FloatFunctions.Pm1Density::class, FloatFunctions.Pm1Density.serializer())
            subclass(FloatFunctions.Pm2_5Density::class, FloatFunctions.Pm2_5Density.serializer())
            subclass(FloatFunctions.Power::class, FloatFunctions.Power.serializer())
            subclass(FloatFunctions.Pressure::class, FloatFunctions.Pressure.serializer())
            subclass(FloatFunctions.Temperature::class, FloatFunctions.Temperature.serializer())
            subclass(FloatFunctions.Tvoc::class, FloatFunctions.Tvoc.serializer())
            subclass(FloatFunctions.Voltage::class, FloatFunctions.Voltage.serializer())
            subclass(FloatFunctions.WaterLevel::class, FloatFunctions.WaterLevel.serializer())
            subclass(FloatFunctions.WaterMeter::class, FloatFunctions.WaterMeter.serializer())
        }
        polymorphic(PropertyValue::class) {
            defaultDeserializer { PropertyValue.serializer() }
            subclass(EventObjectValue::class)
            subclass(FloatObjectValue::class)
        }
        polymorphic(PropertyFunction::class) {
            defaultDeserializer { PropertyFunction.serializer() }
            subclass(FloatFunctions::class, FloatFunctions.serializer())
            subclass(EventFunctions::class, EventFunctions.serializer())
        }
        polymorphic(CapabilityStateObjectValue::class) {
            defaultDeserializer { CapabilityStateObjectValue.serializer() }
            subclass(ColorSettingCapabilityStateObjectValue::class, ColorSettingCapabilityStateObjectValue.serializer())
            subclass(OnOffCapabilityStateObjectValue::class, OnOffCapabilityStateObjectValue.serializer())
            subclass(ModeCapabilityMode::class, ModeCapabilityMode.serializer())
            subclass(RangeCapabilityStateObjectDataValue::class, RangeCapabilityStateObjectDataValue.serializer())
            subclass(ToggleCapabilityStateObjectDataValue::class, ToggleCapabilityStateObjectDataValue.serializer())
            subclass(VideoStreamCapabilityStateObjectDataValue::class, VideoStreamCapabilityStateObjectDataValue.serializer())
            subclass(VideoStreamCapabilityStateObjectActionResultValue::class, VideoStreamCapabilityStateObjectActionResultValue.serializer())
        }
        polymorphic(ColorSettingCapabilityStateObjectValue::class) {
            defaultDeserializer { ColorSettingCapabilityStateObjectValue.serializer() }
            subclass(ColorSettingCapabilityStateObjectValueInteger::class, ColorSettingCapabilityStateObjectValueInteger.serializer())
            subclass(ColorSettingCapabilityStateObjectValueObjectScene::class, ColorSettingCapabilityStateObjectValueObjectScene.serializer())
            subclass(ColorSettingCapabilityStateObjectValueObjectHSV::class, ColorSettingCapabilityStateObjectValueObjectHSV.serializer())
        }
        polymorphic(EventFunctions::class) {
            defaultDeserializer { EventFunctions.serializer() }
            subclass(EventFunctions.Vibration::class, EventFunctions.Vibration.serializer())
            subclass(EventFunctions.Open::class, EventFunctions.Open.serializer())
            subclass(EventFunctions.Button::class, EventFunctions.Button.serializer())
            subclass(EventFunctions.Motion::class, EventFunctions.Motion.serializer())
            subclass(EventFunctions.Smoke::class, EventFunctions.Smoke.serializer())
            subclass(EventFunctions.Gas::class, EventFunctions.Gas.serializer())
            subclass(EventFunctions.BatteryLevel::class, EventFunctions.BatteryLevel.serializer())
            subclass(EventFunctions.FoodLevel::class, EventFunctions.FoodLevel.serializer())
            subclass(EventFunctions.WaterLevel::class, EventFunctions.WaterLevel.serializer())
            subclass(EventFunctions.WaterLeak::class, EventFunctions.WaterLeak.serializer())
            subclass(EventFunctions.Custom::class, EventFunctions.Custom.serializer())
        }
        polymorphic(CapabilityStateObjectActionResult::class) {
            defaultDeserializer { CapabilityStateObjectActionResult.serializer() }
            subclass(ToggleCapabilityStateObjectActionResult::class, ToggleCapabilityStateObjectActionResult.serializer())
            subclass(RangeCapabilityStateObjectActionResult::class, RangeCapabilityStateObjectActionResult.serializer())
            subclass(ModeCapabilityStateObjectActionResult::class, ModeCapabilityStateObjectActionResult.serializer())
            subclass(VideoStreamCapabilityStateObjectActionResult::class, VideoStreamCapabilityStateObjectActionResult.serializer())
        }
    }

    val json = Json {
        prettyPrint = true
        serializersModule = module
    }

    val userInfoResponse = json.decodeFromString<UserInfoResponse>(userInfoJson)
    println(userInfoResponse)
    val deviceStateResponse = json.decodeFromString<DeviceStateResponse>(deviceStateJson)
    println(deviceStateResponse)
}