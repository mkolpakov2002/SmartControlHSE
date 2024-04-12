package ru.hse.smart_control.model.entities.universal.test

import kotlinx.serialization.json.Json
import ru.hse.smart_control.model.entities.universal.scheme.DeviceStateResponse

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

    val json = Json {
        prettyPrint = true
    }
    val deviceStateResponse = json.decodeFromString<DeviceStateResponse>(deviceStateJson)
    println(deviceStateResponse)
}