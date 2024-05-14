import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pl.brightinventions.codified.enums.codifiedEnum
import ru.hse.smart_control.domain.connection.http.yandex.YandexSmartHomeClient
import ru.hse.miem.yandexsmarthomeapi.entity.YandexApiResponse
import ru.hse.miem.yandexsmarthomeapi.entity.YandexManageDeviceCapabilitiesStateRequest
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceActionsObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.DeviceType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.CapabilityType
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.ColorSettingCapabilityStateObjectValueInteger
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectData
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectInstance
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectInstanceWrapper
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.OnOffCapabilityStateObjectValue
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.capability.toCapabilityObject
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.toJson
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.toSmartHomeInfo
import ru.hse.smart_control.model.entities.universal.scheme.common.smart_home.toYandexManageGroupCapabilitiesStateRequest
import kotlin.time.Duration.Companion.seconds

/**
 * Класс для тестирования взаимодействия с API Яндекс Умного Дома.
 */
class YandexSmartHomeClientOnlineTest {

    private val client = YandexSmartHomeClient(
        endpoint = "https://api.iot.yandex.net",
        bearerToken = "y0_AgAEA7qkFjULAAunigAAAAECgh4nAACLJd5CGYRHgKrglKkdzWDwL123WQ"
    )

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `test individual lamp capabilities modifications`() = runTest(timeout = 30.seconds) {
        launch(Dispatchers.Unconfined) {
            // Получение полной информации об умном доме пользователя
            val userInfoResult = client.getUserInfo()
            assertTrue(userInfoResult is YandexApiResponse.SuccessUserInfo, "Не удалось получить информацию о пользователе.")

            val smartHomeInfo = (userInfoResult as YandexApiResponse.SuccessUserInfo).data.toSmartHomeInfo()

            // Индивидуальное управление лампами
            smartHomeInfo.devices.filter {
                it.type.type == DeviceType.LIGHT.codifiedEnum()
            }.forEach { device ->
                // Находим возможность управления включением/выключением
                val onOffCapability = device.capabilities.firstOrNull {
                    it.type.type == CapabilityType.ON_OFF.codifiedEnum()
                }

                if (onOffCapability != null) {
                    val onOffCapabilityStateObjectData = onOffCapability.state as? OnOffCapabilityStateObjectData

                    val currentState = onOffCapabilityStateObjectData?.value?.value

                    println("Текущее состояние лампы ${device.name}: ${if (currentState == true) "включено" else if (currentState == false) "выключено" else "неизвестно"}")

                    var newCurrentState = currentState != true

                    println("Меняю состояние лампы ${device.name} на: ${if (newCurrentState) "включено" else "выключено"}")

                    // Создаем новое состояние для включения/выключения лампы
                    val newOnOffState = onOffCapabilityStateObjectData
                        ?.copy(value = OnOffCapabilityStateObjectValue(newCurrentState))
                        ?: OnOffCapabilityStateObjectData(
                            instance = OnOffCapabilityStateObjectInstanceWrapper(
                                OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                            value = OnOffCapabilityStateObjectValue(newCurrentState)
                        )

                    onOffCapability.state = newOnOffState

                    // Отправляем запрос на изменение состояния лампы
                    val manageDeviceRequest = YandexManageDeviceCapabilitiesStateRequest(listOf(
                        DeviceActionsObject(
                            device.id, device.capabilities.map { it.toCapabilityObject() }.toMutableList()
                        )
                    ).map { it.toJson() })

                    val manageResult = client.manageDeviceCapabilitiesState(manageDeviceRequest)
                    assertTrue(manageResult is YandexApiResponse.SuccessManageDeviceCapabilitiesState,
                        "Не удалось изменить состояние лампы: ${device.name}")

                    delay(4.seconds)  // Задержка для проверки изменения состояния

                    // Возвращаем предыдущее состояние лампы
                    newCurrentState = !newCurrentState

                    println("Меняю состояние лампы ${device.name} на: ${if (newCurrentState) "включено" else "выключено"}")

                    val revertOnOffState = onOffCapabilityStateObjectData
                        ?.copy(value = OnOffCapabilityStateObjectValue(newCurrentState))
                        ?: OnOffCapabilityStateObjectData(
                            instance = OnOffCapabilityStateObjectInstanceWrapper(
                                OnOffCapabilityStateObjectInstance.ON.codifiedEnum()),
                            value = OnOffCapabilityStateObjectValue(newCurrentState)
                        )

                    onOffCapability.state = revertOnOffState

                    // Отправляем запрос на изменение состояния лампы
                    val revertDeviceRequest = YandexManageDeviceCapabilitiesStateRequest(listOf(
                        DeviceActionsObject(
                            device.id, device.capabilities.map { it.toCapabilityObject() }.toMutableList()
                        )
                    ).map { it.toJson() })

                    val revertResult = client.manageDeviceCapabilitiesState(revertDeviceRequest)
                    assertTrue(revertResult is YandexApiResponse.SuccessManageDeviceCapabilitiesState,
                        "Не удалось изменить состояние лампы: ${device.name}")
                }
            }
        }
    }

    @Test
    fun `test group lamp capabilities modifications`() = runTest(timeout = 30.seconds) {
        launch(Dispatchers.Unconfined) {
            // Получение полной информации об умном доме пользователя
            val userInfoResult = client.getUserInfo()
            assertTrue(userInfoResult is YandexApiResponse.SuccessUserInfo, "Не удалось получить информацию о пользователе.")

            val smartHomeInfo = (userInfoResult as YandexApiResponse.SuccessUserInfo).data.toSmartHomeInfo()

            // Групповое управление
            val groupWithLamps = smartHomeInfo.groups.find { group ->
                group.devices.any { deviceId -> smartHomeInfo.devices.any { device -> device.id == deviceId &&
                        device.type.type == DeviceType.LIGHT.codifiedEnum() } }
            }

            if (groupWithLamps != null) {
                println("Выполнение группового управления над лампами в группе: ${groupWithLamps.name}, id: ${groupWithLamps.id}")

                // Меняем состояния на основе текущих значений
                val modifiedGroupActions = groupWithLamps.capabilities.map { capability ->
                    when (capability.state) {
                        is OnOffCapabilityStateObjectData -> {
                            (capability.state as OnOffCapabilityStateObjectData).copy(
                                value = OnOffCapabilityStateObjectValue(!(capability.state as OnOffCapabilityStateObjectData).value.value)
                            )
                        }
                        is ColorSettingCapabilityStateObjectData -> {
                            (capability.state as ColorSettingCapabilityStateObjectData).copy(
                                instance = ColorSettingCapabilityStateObjectInstanceWrapper(
                                    ColorSettingCapabilityStateObjectInstance.TEMPERATURE_K.codifiedEnum()),
                                value = ColorSettingCapabilityStateObjectValueInteger(4500)
                            )
                        }
                        else -> capability.state
                    }
                }

                groupWithLamps.capabilities.forEachIndexed { index, capability ->
                    groupWithLamps.capabilities[index] = capability.copy(state = modifiedGroupActions[index])
                }

                // Отправляем запрос на изменение состояния группы устройств
                val manageGroupRequest = groupWithLamps.capabilities.toYandexManageGroupCapabilitiesStateRequest()

                println("Групповое управление лампами: $manageGroupRequest")

                val manageGroupResult = client.manageGroupCapabilitiesState(groupWithLamps.id, manageGroupRequest)
                assertTrue(manageGroupResult is YandexApiResponse.SuccessManageGroupCapabilitiesState,
                    "Не удалось выполнить групповое управление лампами.")
            } else {
                println("Группа с лампами не найдена.")
            }
        }
    }

}