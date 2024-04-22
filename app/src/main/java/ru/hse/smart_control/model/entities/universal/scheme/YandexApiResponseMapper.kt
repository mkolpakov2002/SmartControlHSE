package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.json.JsonObject
import ru.hse.miem.yandexsmarthomeapi.entity.YandexDeviceStateResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexUserInfoResponse

class YandexApiResponseMapper {

    fun mapUserInfoResponse(userInfoResponse: YandexUserInfoResponse): UserInfo {
        val roomObjects = mapRooms(userInfoResponse.rooms)
        val groupObjects = mapGroups(userInfoResponse.groups)
        val deviceObjects = mapDevices(userInfoResponse.devices)
        val scenarioObjects = mapScenarios(userInfoResponse.scenarios)
        val householdObjects = mapHouseholds(userInfoResponse.households)
        return UserInfo(roomObjects, groupObjects, deviceObjects, scenarioObjects, householdObjects)
    }

    fun mapDeviceStateResponse(deviceStateResponse: YandexDeviceStateResponse): DeviceObject {
        TODO("not implemented")
    }

    private fun mapRooms(rooms: List<JsonObject>): List<RoomObject> {
        // Преобразование JsonObject в RoomObject
        return rooms.map { roomJson ->
            // Логика парсинга
            TODO("not implemented")
        }
    }

    private fun mapGroups(groups: List<JsonObject>): List<GroupObject> {
        // Преобразование JsonObject в GroupObject
        return groups.map { groupJson ->
            // Логика парсинга
            TODO("not implemented")
        }
    }

    private fun mapDevices(devices: List<JsonObject>): List<DeviceObject> {
        // Преобразование JsonObject в DeviceObject
        return devices.map { deviceJson ->
            // Логика парсинга
            TODO("not implemented")
        }
    }

    private fun mapScenarios(scenarios: List<JsonObject>): List<ScenarioObject> {
        // Преобразование JsonObject в ScenarioObject
        return scenarios.map { scenarioJson ->
            // Логика парсинга
            TODO("not implemented")
        }
    }

    private fun mapHouseholds(households: List<JsonObject>): List<HouseholdObject> {
        // Преобразование JsonObject в HouseholdObject
        return households.map { householdJson ->
            // Логика парсинга
            TODO("not implemented")
        }
    }

}
