package ru.hse.smart_control.model.entities.universal.scheme.common

import kotlinx.serialization.Serializable

@Serializable
data class MacAddress(val address: String) {
    init {
        require(isValidMacAddress(address)) { "Invalid MAC address format" }
    }

    companion object {
        private val macAddressPattern = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$".toRegex()

        fun isValidMacAddress(address: String): Boolean {
            return macAddressPattern.matches(address)
        }
    }
}