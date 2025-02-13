package com.example.sarpapp.data.ble

data class ScannedDevice(
    val name : String = "N/A",
    val macAddress: String = "00:00:00:00:00:00",
    val rssi: Int = 999)
{

}
