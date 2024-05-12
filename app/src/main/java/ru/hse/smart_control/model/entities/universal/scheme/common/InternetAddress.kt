package ru.hse.smart_control.model.entities.universal.scheme.common

import kotlinx.serialization.Serializable

@Serializable
data class InternetAddress(val address: String, val port: Int) {
    init {
        require(isValidIpAddressWithPort(address, port)) { "Invalid IP address or port format" }
    }

    companion object {
        private val ipAddressPattern = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$".toRegex()
        private const val MIN_PORT = 1
        private const val MAX_PORT = 65535

        fun isValidIpAddressWithPort(address: String, port: Int): Boolean {
            return ipAddressPattern.matches(address) && port in MIN_PORT..MAX_PORT
        }
    }
}