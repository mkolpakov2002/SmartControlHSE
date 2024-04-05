package ru.hse.smart_control.model.entities.universal.scheme

import kotlinx.serialization.Serializable

/**
 * Базовый класс для моделей ответов API.
 * https://raw.githubusercontent.com/dext0r/yandex_smart_home/master/custom_components/yandex_smart_home/schema/base.py
 */
@Serializable
sealed interface APIModel

@Serializable
open class GenericAPIModel<T>: APIModel