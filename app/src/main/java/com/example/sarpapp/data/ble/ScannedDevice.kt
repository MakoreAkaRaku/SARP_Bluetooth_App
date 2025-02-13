package com.example.sarpapp.data.ble

data class ScannedDevice(
    val name: String,
    val macAddress: String = "00:00:00:00:00:00",
    val rssi: Int = 999)
{
    constructor(nameTmp:String?,address: String,rssi: Int) :
            this(nameTmp ?: "N/A",address,rssi)

}
