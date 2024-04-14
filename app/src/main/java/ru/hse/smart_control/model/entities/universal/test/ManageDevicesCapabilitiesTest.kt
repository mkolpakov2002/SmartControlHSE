package ru.hse.smart_control.model.entities.universal.test

import kotlinx.serialization.json.Json
import ru.hse.smart_control.model.entities.universal.scheme.DeviceStateResponse
import ru.hse.smart_control.model.entities.universal.scheme.ManageDeviceCapabilitiesStateRequest

val postManageDeviceCapabilitiesJson = """
    {
        "devices": [
            {
                "id": "lamp-id-1",
                "actions": [
                    {
                        "type": "devices.capabilities.on_off",
                        "state": {
                            "instance": "ON",
                            "value": true
                        }
                    },
                    {
                        "type": "devices.capabilities.range",
                        "state": {
                            "instance": "BRIGHTNESS",
                            "value": 50.0
                        }
                    },
                    {
                        "type": "devices.capabilities.color_setting",
                        "state": {
                            "instance": "TEMPERATURE_K",
                            "value": 4000
                        }
                    }
                ]
            }
        ]
    }
""".trimIndent()

val getManageDeviceCapabilitiesJson = """
    {
   "status": "ok",
   "request_id": "fd9299b8-f927-44f1-8728-8a0b35f1c72a",
   "devices": [{
      "id": "lamp-id-1",
      "capabilities": [{
         "type": "devices.capabilities.on_off",
         "state": {
            "instance": "ON",
            "action_result": {
               "status": "DONE"
            }
         }
      },
      {
         "type": "devices.capabilities.range",
         "state": {
            "instance": "BRIGHTNESS",
            "action_result": {
               "status": "DONE"
            }
         }
      },
      {
         "type": "devices.capabilities.color_setting",
         "state": {
            "instance": "TEMPERATURE_K",
            "action_result": {
               "status": "DONE"
            }
         }
      }]
   }]
}
""".trimIndent()

fun main() {

    val json = Json {
        prettyPrint = true
    }
    val deviceStateResponse = json.decodeFromString<ManageDeviceCapabilitiesStateRequest>(postManageDeviceCapabilitiesJson)


    println(deviceStateResponse)
}