package ru.hse.smart_control.model.entities.universal.test

import kotlinx.serialization.json.Json
import ru.hse.smart_control.model.entities.universal.scheme.UserInfoResponse

val universalSchemeJson = """
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

fun main() {

    val json = Json {
        prettyPrint = true
    }
    val universalSchemeJsonResponse = json.decodeFromString<UserInfoResponse>(universalSchemeJson)
    println(universalSchemeJsonResponse)
}