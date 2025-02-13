package com.example.sarpapp.data.ble

data class ScannedDevice(
    val name: String,
    val macAddress: String,
    val rssi: Int,
    val scannedAt: Long
)

