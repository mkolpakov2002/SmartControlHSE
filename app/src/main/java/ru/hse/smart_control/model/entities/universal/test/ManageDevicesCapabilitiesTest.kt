package ru.hse.smart_control.model.entities.universal.test

import kotlinx.serialization.json.Json
import ru.hse.smart_control.model.entities.universal.scheme.DeviceActionResponse
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
                            "instance": "on",
                            "value": true
                        }
                    },
                    {
                        "type": "devices.capabilities.range",
                        "state": {
                            "instance": "brightness",
                            "value": 50.0
                        }
                    },
                    {
                        "type": "devices.capabilities.color_setting",
                        "state": {
                            "instance": "temperature_k",
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
            "instance": "on",
            "action_result": {
               "status": "DONE"
            }
         }
      },
      {
         "type": "devices.capabilities.range",
         "state": {
            "instance": "brightness",
            "action_result": {
               "status": "DONE"
            }
         }
      },
      {
         "type": "devices.capabilities.color_setting",
         "state": {
            "instance": "temperature_k",
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

    val getManageDeviceCapabilitiesResponse = json.decodeFromString<DeviceActionResponse>(getManageDeviceCapabilitiesJson)
    println(deviceStateResponse)
    println(getManageDeviceCapabilitiesResponse)
}