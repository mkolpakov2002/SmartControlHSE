package ru.hse.smart_control.domain.connection.http.yandex

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class YandexSmartHomeAPIClient(private val accessToken: String,
                               val baseUrl: String = "https://api.iot.yandex.net/v1.0") {

    val httpClient = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    logger.log(message)
                }
            }
            level = LogLevel.ALL
        }
        defaultRequest {
            header("Authorization", "Bearer $accessToken")
        }
    }

}