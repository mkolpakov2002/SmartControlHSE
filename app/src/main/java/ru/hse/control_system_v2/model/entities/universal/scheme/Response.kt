package ru.hse.control_system_v2.model.entities.universal.scheme

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Код ответа.
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/response-codes.html
 * https://raw.githubusercontent.com/dext0r/yandex_smart_home/master/custom_components/yandex_smart_home/schema/response.py
 */

/**
 * Базовый класс для полезной нагрузки ответа API.
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/response-codes.html
 * https://raw.githubusercontent.com/dext0r/yandex_smart_home/master/custom_components/yandex_smart_home/schema/response.py
 */
@Serializable
open class ResponsePayload: APIModel

/**
 * Полезная нагрузка ошибки.
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/response-codes.html
 * https://raw.githubusercontent.com/dext0r/yandex_smart_home/master/custom_components/yandex_smart_home/schema/response.py
 */
@Serializable
data class Error(
    @SerialName("error_code") val errorCode: ErrorCode,
    @SerialName("error_message") val errorMessage: String? = null
): ResponsePayload()

/**
 * Базовый ответ API.
 * https://yandex.ru/dev/dialogs/smart-home/doc/concepts/response-codes.html
 * https://raw.githubusercontent.com/dext0r/yandex_smart_home/master/custom_components/yandex_smart_home/schema/response.py
 */
