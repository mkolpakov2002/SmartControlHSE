package ru.hse.smart_control.model.entities

import android.bluetooth.BluetoothAdapter

/**
 * логика для управления по Bluetooth
 */
interface DeviceBluetoothConnectable {
    var bluetoothAddress: String
    val isBluetoothSupported: Boolean
        get() = BluetoothAdapter.checkBluetoothAddress(bluetoothAddress)
}