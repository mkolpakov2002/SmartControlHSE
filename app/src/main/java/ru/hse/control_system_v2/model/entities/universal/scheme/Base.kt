package ru.hse.control_system_v2.model.entities.universal.scheme

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Базовый класс для моделей ответов API.
 * https://raw.githubusercontent.com/dext0r/yandex_smart_home/master/custom_components/yandex_smart_home/schema/base.py
 */
@Serializable
sealed interface APIModel

@Serializable
open class GenericAPIModel<T>: APIModel