package ru.hse.smart_control.model.entities.universal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.hse.smart_control.model.entities.universal.scheme.BatteryLevelEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.ButtonEventObjectValue
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
import ru.hse.smart_control.model.entities.universal.scheme.DeviceCapabilityObject
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
import ru.hse.smart_control.model.entities.universal.scheme.FloatPropertyParameterObjectUnit
import ru.hse.smart_control.model.entities.universal.scheme.FoodLevelEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.GasEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.MeasurementUnit
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityInstance
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityMode
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.ModeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.MotionEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.OnOffCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.OpenInstanceObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.PropertyFunction
import ru.hse.smart_control.model.entities.universal.scheme.PropertyParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.PropertyStateObject
import ru.hse.smart_control.model.entities.universal.scheme.PropertyType
import ru.hse.smart_control.model.entities.universal.scheme.PropertyValue
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObjectFunction
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityParameterObjectUnit
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.RangeCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.SceneObject
import ru.hse.smart_control.model.entities.universal.scheme.SmokeEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.Status
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityParameterObjectFunction
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.ToggleCapabilityStateObjectDataValue
import ru.hse.smart_control.model.entities.universal.scheme.UserInfoResponse
import ru.hse.smart_control.model.entities.universal.scheme.VibrationEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityParameterObject
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityParameterObjectStreamProtocol
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectActionResult
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectData

import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.VideoStreamCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.WaterLeakEventObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.WaterLevelEventObjectValue

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
            subclass(ColorSettingCapabilityParameterObject::class)
            subclass(OnOffCapabilityParameterObject::class)
            subclass(ModeCapabilityParameterObject::class)
            subclass(RangeCapabilityParameterObject::class)
            subclass(ToggleCapabilityParameterObject::class)
            subclass(VideoStreamCapabilityParameterObject::class)
        }
        polymorphic(PropertyParameterObject::class) {
            subclass(FloatPropertyParameterObject::class)
            subclass(EventPropertyParameterObject::class)
        }
        polymorphic(CapabilityStateObjectData::class) {
            subclass(ColorSettingCapabilityStateObjectData::class)
            subclass(OnOffCapabilityStateObjectData::class)
            subclass(ModeCapabilityStateObjectData::class)
            subclass(RangeCapabilityStateObjectData::class)
            subclass(ToggleCapabilityStateObjectData::class)
            subclass(VideoStreamCapabilityStateObjectData::class)
        }
        polymorphic(CapabilityStateObjectInstance::class) {
            subclass(ColorSettingCapabilityStateObjectInstance::class)
            subclass(OnOffCapabilityStateObjectInstance::class)
            subclass(ModeCapabilityInstance::class)
            subclass(RangeCapabilityParameterObjectFunction::class)
            subclass(ToggleCapabilityParameterObjectFunction::class)
            subclass(VideoStreamCapabilityStateObjectInstance::class)
        }
        polymorphic(ColorSettingCapabilityStateObjectInstance::class) {
            subclass(ColorSettingCapabilityStateObjectInstance.Base::class)
            subclass(ColorSettingCapabilityStateObjectInstance.RGB::class)
            subclass(ColorSettingCapabilityStateObjectInstance.HSV::class)
            subclass(ColorSettingCapabilityStateObjectInstance.TemperatureK::class)
            subclass(ColorSettingCapabilityStateObjectInstance.Scene::class)
        }
        polymorphic(VideoStreamCapabilityStateObjectInstance::class) {
            subclass(VideoStreamCapabilityStateObjectInstance.GetStream::class)
        }
        polymorphic(OnOffCapabilityStateObjectInstance::class) {
            subclass(OnOffCapabilityStateObjectInstance.On::class)
        }
        polymorphic(Status::class) {
            subclass(Status.Done::class)
            subclass(Status.Error::class)
            subclass(Status.CustomStatus::class)
        }
        polymorphic(ErrorCode::class) {
            subclass(ErrorCode.DoorOpen::class)
            subclass(ErrorCode.LidOpen::class)
            subclass(ErrorCode.RemoteControlDisabled::class)
            subclass(ErrorCode.NotEnoughWater::class)
            subclass(ErrorCode.LowChargeLevel::class)
            subclass(ErrorCode.ContainerFull::class)
            subclass(ErrorCode.ContainerEmpty::class)
            subclass(ErrorCode.DripTrayFull::class)
            subclass(ErrorCode.DeviceStuck::class)
            subclass(ErrorCode.DeviceOff::class)
            subclass(ErrorCode.FirmwareOutOfDate::class)
            subclass(ErrorCode.NotEnoughDetergent::class)
            subclass(ErrorCode.HumanInvolvementNeeded::class)
            subclass(ErrorCode.DeviceUnreachable::class)
            subclass(ErrorCode.DeviceBusy::class)
            subclass(ErrorCode.InternalError::class)
            subclass(ErrorCode.InvalidAction::class)
            subclass(ErrorCode.InvalidValue::class)
            subclass(ErrorCode.NotSupportedInCurrentMode::class)
            subclass(ErrorCode.AccountLinkingError::class)
            subclass(ErrorCode.DeviceNotFound::class)
        }
        polymorphic(VideoStreamCapabilityParameterObjectStreamProtocol::class) {
            subclass(VideoStreamCapabilityParameterObjectStreamProtocol.HLS::class)
            subclass(VideoStreamCapabilityParameterObjectStreamProtocol.RTMP::class)
        }
        polymorphic(CapabilityState::class) {
            subclass(CapabilityStateObjectData::class)
        }
        polymorphic(ToggleCapabilityParameterObjectFunction::class) {
            subclass(ToggleCapabilityParameterObjectFunction.Backlight::class)
            subclass(ToggleCapabilityParameterObjectFunction.ControlsLocked::class)
            subclass(ToggleCapabilityParameterObjectFunction.Ionization::class)
            subclass(ToggleCapabilityParameterObjectFunction.KeepWarm::class)
            subclass(ToggleCapabilityParameterObjectFunction.Mute::class)
            subclass(ToggleCapabilityParameterObjectFunction.Oscillation::class)
            subclass(ToggleCapabilityParameterObjectFunction.Pause::class)
        }
        polymorphic(RangeCapabilityParameterObjectFunction::class) {
            subclass(RangeCapabilityParameterObjectFunction.Brightness::class)
            subclass(RangeCapabilityParameterObjectFunction.Channel::class)
            subclass(RangeCapabilityParameterObjectFunction.Humidity::class)
            subclass(RangeCapabilityParameterObjectFunction.Open::class)
            subclass(RangeCapabilityParameterObjectFunction.Temperature::class)
            subclass(RangeCapabilityParameterObjectFunction.Volume::class)
        }
        polymorphic(ModeCapabilityInstance::class) {
            subclass(ModeCapabilityInstance.CleanupMode::class)
            subclass(ModeCapabilityInstance.CoffeeMode::class)
            subclass(ModeCapabilityInstance.Dishwashing::class)
            subclass(ModeCapabilityInstance.FanSpeed::class)
            subclass(ModeCapabilityInstance.Heat::class)
            subclass(ModeCapabilityInstance.InputSource::class)
            subclass(ModeCapabilityInstance.Program::class)
            subclass(ModeCapabilityInstance.Swing::class)
            subclass(ModeCapabilityInstance.TeaMode::class)
            subclass(ModeCapabilityInstance.Thermostat::class)
            subclass(ModeCapabilityInstance.WorkSpeed::class)
        }
        polymorphic(ModeCapabilityMode::class) {
            subclass(ModeCapabilityMode.Auto::class)
            subclass(ModeCapabilityMode.Eco::class)
            subclass(ModeCapabilityMode.Smart::class)
            subclass(ModeCapabilityMode.Turbo::class)
            subclass(ModeCapabilityMode.Cool::class)
            subclass(ModeCapabilityMode.Dry::class)
            subclass(ModeCapabilityMode.FanOnly::class)
            subclass(ModeCapabilityMode.Heat::class)
            subclass(ModeCapabilityMode.Preheat::class)
            subclass(ModeCapabilityMode.High::class)
            subclass(ModeCapabilityMode.Low::class)
            subclass(ModeCapabilityMode.Medium::class)
            subclass(ModeCapabilityMode.Max::class)
            subclass(ModeCapabilityMode.Min::class)
            subclass(ModeCapabilityMode.Fast::class)
            subclass(ModeCapabilityMode.Slow::class)
            subclass(ModeCapabilityMode.Express::class)
            subclass(ModeCapabilityMode.Normal::class)
            subclass(ModeCapabilityMode.Quiet::class)
            subclass(ModeCapabilityMode.Horizontal::class)
            subclass(ModeCapabilityMode.Stationary::class)
            subclass(ModeCapabilityMode.Vertical::class)
            subclass(ModeCapabilityMode.One::class)
            subclass(ModeCapabilityMode.Two::class)
            subclass(ModeCapabilityMode.Three::class)
            subclass(ModeCapabilityMode.Four::class)
            subclass(ModeCapabilityMode.Five::class)
            subclass(ModeCapabilityMode.Six::class)
            subclass(ModeCapabilityMode.Seven::class)
            subclass(ModeCapabilityMode.Eight::class)
            subclass(ModeCapabilityMode.Nine::class)
            subclass(ModeCapabilityMode.Ten::class)
            subclass(ModeCapabilityMode.Americano::class)
            subclass(ModeCapabilityMode.Cappuccino::class)
            subclass(ModeCapabilityMode.Double::class)
            subclass(ModeCapabilityMode.Espresso::class)
            subclass(ModeCapabilityMode.DoubleEspresso::class)
            subclass(ModeCapabilityMode.Latte::class)
            subclass(ModeCapabilityMode.BlackTea::class)
            subclass(ModeCapabilityMode.FlowerTea::class)
            subclass(ModeCapabilityMode.GreenTea::class)
            subclass(ModeCapabilityMode.HerbalTea::class)
            subclass(ModeCapabilityMode.OolongTea::class)
            subclass(ModeCapabilityMode.PuerhTea::class)
            subclass(ModeCapabilityMode.RedTea::class)
            subclass(ModeCapabilityMode.WhiteTea::class)
            subclass(ModeCapabilityMode.Glass::class)
            subclass(ModeCapabilityMode.Intensive::class)
            subclass(ModeCapabilityMode.PreRinse::class)
            subclass(ModeCapabilityMode.Aspic::class)
            subclass(ModeCapabilityMode.BabyFood::class)
            subclass(ModeCapabilityMode.Baking::class)
            subclass(ModeCapabilityMode.Bread::class)
            subclass(ModeCapabilityMode.Boiling::class)
            subclass(ModeCapabilityMode.Cereals::class)
            subclass(ModeCapabilityMode.Cheesecake::class)
            subclass(ModeCapabilityMode.DeepFryer::class)
            subclass(ModeCapabilityMode.Dessert::class)
            subclass(ModeCapabilityMode.Fowl::class)
            subclass(ModeCapabilityMode.Frying::class)
            subclass(ModeCapabilityMode.Macaroni::class)
            subclass(ModeCapabilityMode.MilkPorridge::class)
            subclass(ModeCapabilityMode.Multicooker::class)
            subclass(ModeCapabilityMode.Pasta::class)
            subclass(ModeCapabilityMode.Pilaf::class)
            subclass(ModeCapabilityMode.Pizza::class)
            subclass(ModeCapabilityMode.Sauce::class)
            subclass(ModeCapabilityMode.SlowCook::class)
            subclass(ModeCapabilityMode.Soup::class)
            subclass(ModeCapabilityMode.Steam::class)
            subclass(ModeCapabilityMode.Stewing::class)
            subclass(ModeCapabilityMode.Vacuum::class)
            subclass(ModeCapabilityMode.Yogurt::class)
        }
        polymorphic(SceneObject::class) {
            subclass(SceneObject.Alarm::class)
            subclass(SceneObject.Alice::class)
            subclass(SceneObject.Candle::class)
            subclass(SceneObject.Dinner::class)
            subclass(SceneObject.Fantasy::class)
            subclass(SceneObject.Garland::class)
            subclass(SceneObject.Jungle::class)
            subclass(SceneObject.Movie::class)
            subclass(SceneObject.Neon::class)
            subclass(SceneObject.Night::class)
            subclass(SceneObject.Ocean::class)
            subclass(SceneObject.Party::class)
            subclass(SceneObject.Reading::class)
            subclass(SceneObject.Rest::class)
            subclass(SceneObject.Romance::class)
            subclass(SceneObject.Siren::class)
            subclass(SceneObject.Sunrise::class)
            subclass(SceneObject.Sunset::class)
        }
        polymorphic(ColorModel::class) {
            subclass(ColorModel.RGB::class)
            subclass(ColorModel.HSV::class)
        }
        polymorphic(DeviceState::class) {
            subclass(DeviceState.Online::class)
            subclass(DeviceState.Offline::class)
        }
        polymorphic(RangeCapabilityParameterObjectUnit::class) {
            subclass(RangeCapabilityParameterObjectUnit.Percent::class, RangeCapabilityParameterObjectUnit.Percent.serializer())
            subclass(RangeCapabilityParameterObjectUnit.TemperatureCelsius::class, RangeCapabilityParameterObjectUnit.TemperatureCelsius.serializer())
        }
        polymorphic(FloatPropertyParameterObjectUnit::class) {
            subclass(FloatPropertyParameterObjectUnit.Ampere::class)
            subclass(FloatPropertyParameterObjectUnit.CubicMeter::class)
            subclass(FloatPropertyParameterObjectUnit.Gigacalorie::class)
            subclass(FloatPropertyParameterObjectUnit.KilowattHour::class)
            subclass(FloatPropertyParameterObjectUnit.Lux::class)
            subclass(FloatPropertyParameterObjectUnit.McgM3::class)
            subclass(FloatPropertyParameterObjectUnit.Percent::class)
            subclass(FloatPropertyParameterObjectUnit.Ppm::class)
            subclass(FloatPropertyParameterObjectUnit.Volt::class)
            subclass(FloatPropertyParameterObjectUnit.Watt::class)
        }
        polymorphic(EventObjectValue::class) {
            subclass(EventObjectValue.Tilt::class)
            subclass(EventObjectValue.Fall::class)
            subclass(EventObjectValue.Vibration::class)
            subclass(EventObjectValue.Opened::class)
            subclass(EventObjectValue.Closed::class)
            subclass(EventObjectValue.Click::class)
            subclass(EventObjectValue.DoubleClick::class)
            subclass(EventObjectValue.LongPress::class)
            subclass(EventObjectValue.Detected::class)
            subclass(EventObjectValue.NotDetected::class)
            subclass(EventObjectValue.High::class)
            subclass(EventObjectValue.Low::class)
            subclass(EventObjectValue.Normal::class)
            subclass(EventObjectValue.Empty::class)
            subclass(EventObjectValue.Dry::class)
            subclass(EventObjectValue.Leak::class)
        }
        polymorphic(PropertyType::class) {
            subclass(PropertyType.Float::class)
            subclass(PropertyType.Event::class)
        }
        polymorphic(FloatFunctions::class) {
            subclass(FloatFunctions.Amperage::class)
            subclass(FloatFunctions.BatteryLevel::class)
            subclass(FloatFunctions.Co2Level::class)
            subclass(FloatFunctions.ElectricityMeter::class)
            subclass(FloatFunctions.FoodLevel::class)
            subclass(FloatFunctions.GasMeter::class)
            subclass(FloatFunctions.HeatMeter::class)
            subclass(FloatFunctions.Humidity::class)
            subclass(FloatFunctions.Illumination::class)
            subclass(FloatFunctions.Meter::class)
            subclass(FloatFunctions.Pm10Density::class)
            subclass(FloatFunctions.Pm1Density::class)
            subclass(FloatFunctions.Pm2_5Density::class)
            subclass(FloatFunctions.Power::class)
            subclass(FloatFunctions.Pressure::class)
            subclass(FloatFunctions.Temperature::class)
            subclass(FloatFunctions.Tvoc::class)
            subclass(FloatFunctions.Voltage::class)
            subclass(FloatFunctions.WaterLevel::class)
            subclass(FloatFunctions.WaterMeter::class)
        }
        polymorphic(MeasurementUnit::class) {
            subclass(FloatPropertyParameterObjectUnit::class)
            subclass(RangeCapabilityParameterObjectUnit::class)
        }
        polymorphic(PropertyValue::class) {
            subclass(EventObjectValue::class)
            subclass(FloatObjectValue::class)
        }
        polymorphic(PropertyFunction::class) {
            subclass(FloatFunctions::class)
            subclass(EventFunctions::class)
        }
        polymorphic(CapabilityStateObjectValue::class) {
            subclass(ColorSettingCapabilityStateObjectValue::class)
            subclass(OnOffCapabilityStateObjectValue::class)
            subclass(ModeCapabilityMode::class)
            subclass(RangeCapabilityStateObjectDataValue::class)
            subclass(ToggleCapabilityStateObjectDataValue::class)
            subclass(VideoStreamCapabilityStateObjectValue::class)
        }
        polymorphic(ColorSettingCapabilityStateObjectValue::class) {
            subclass(ColorSettingCapabilityStateObjectValueInteger::class, ColorSettingCapabilityStateObjectValueInteger.serializer())
            subclass(ColorSettingCapabilityStateObjectValueObjectScene::class, ColorSettingCapabilityStateObjectValueObjectScene.serializer())
            subclass(ColorSettingCapabilityStateObjectValueObjectHSV::class, ColorSettingCapabilityStateObjectValueObjectHSV.serializer())
        }
        polymorphic(VideoStreamCapabilityStateObjectValue::class) {
            subclass(VideoStreamCapabilityStateObjectValue.VideoStreamCapabilityStateObjectDataValue::class, VideoStreamCapabilityStateObjectValue.VideoStreamCapabilityStateObjectDataValue.serializer())
            subclass(VideoStreamCapabilityStateObjectValue.VideoStreamCapabilityStateObjectActionResultValue::class, VideoStreamCapabilityStateObjectValue.VideoStreamCapabilityStateObjectActionResultValue.serializer())
        }
        polymorphic(EventFunctions::class) {
            subclass(EventFunctions.Vibration::class)
            subclass(EventFunctions.Open::class)
            subclass(EventFunctions.Button::class)
            subclass(EventFunctions.Motion::class)
            subclass(EventFunctions.Smoke::class)
            subclass(EventFunctions.Gas::class)
            subclass(EventFunctions.BatteryLevel::class)
            subclass(EventFunctions.FoodLevel::class)
            subclass(EventFunctions.WaterLevel::class)
            subclass(EventFunctions.WaterLeak::class)
            subclass(EventFunctions.Custom::class)
        }
        polymorphic(CapabilityStateObjectActionResult::class) {
            subclass(ToggleCapabilityStateObjectActionResult::class)
            subclass(RangeCapabilityStateObjectActionResult::class)
            subclass(ModeCapabilityStateObjectActionResult::class)
            subclass(VideoStreamCapabilityStateObjectActionResult::class)
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