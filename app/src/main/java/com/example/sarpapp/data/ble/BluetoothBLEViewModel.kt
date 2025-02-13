@file:OptIn(ExperimentalStdlibApi::class)

package com.example.sarpapp.data.ble

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BluetoothBLEViewModel(
    application: Application
) : AndroidViewModel(application) {
    var isAdvertising by mutableStateOf(false)
        private set

    var scanHasCooledDown by mutableStateOf(true)
        private set

    private val bluetoothAdapter by lazy {
        val app = getApplication<Application>()
        when {
            Build.VERSION.SDK_INT in 1..JELLY_BEAN_MR1 -> BluetoothAdapter.getDefaultAdapter()
            else -> (app.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        }
    }


    private val leScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val leAdvertiser by lazy {
        bluetoothAdapter.bluetoothLeAdvertiser
    }

    companion object DataConstants {
        //TODO: Short UUID must be defined by the whole system service.
        const val SHORT_UUID: Int = 0xDEAD
        const val SSID_MAX_LENGTH: Int = 8
        const val PWD_MAX_LENGTH: Int = 8

        /**Default time in ms to scan devices.**/
        const val SCAN_CD_TIME: Long = 5000

        /**Default time in ms to broadcast messages.**/
        const val BROADCAST_TIME: Long = 30000
    }

    val scanResults = mutableStateListOf<ScannedDevice>()

    /**
     * Object containing functions to be called when Scanning receives a result.
     */
    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {

            if (callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES && result != null) {
                val device =ScannedDevice(
                    name = result.device.name ?: "N/A",
                    macAddress = result.device.address,
                    rssi = result.rssi
                    )
                scanResults.add(device)
            }
        }
    }

    /**
     * Object containing functions to be called when Advertising tries to start.
     */
    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)
            isAdvertising = false

        }

        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            super.onStartSuccess(settingsInEffect)
            isAdvertising = true
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan() {
        scanHasCooledDown = false
        leScanner.startScan(scanCallback)
        viewModelScope.launch {
            delay(SCAN_CD_TIME)
            scanHasCooledDown = true
        }
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        leScanner.stopScan(scanCallback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    fun broadcastForm(ssid: String, pwd: String, duration: Long = BROADCAST_TIME): Boolean {

        var msg = (ssid + pwd).toByteArray(Charsets.UTF_8)
        //TODO: must encrypt the message before broadcasting

        Log.i(this::class.java.simpleName, "Message is 0x${msg.toHexString(HexFormat.UpperCase)}")

        val data = AdvertiseData
            .Builder()
            .addManufacturerData(SHORT_UUID, msg)
            .setIncludeDeviceName(false)
            .build()

        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            .setConnectable(false)
            .setTimeout(0)
            .build()

        leAdvertiser.startAdvertising(
            settings,
            data,
            advertiseCallback
        )
        viewModelScope.launch {
            delay(duration)
            isAdvertising = false
            leAdvertiser.stopAdvertising(advertiseCallback)
        }
        return true
    }


    @SuppressLint("MissingPermission")
    override fun onCleared() {
        super.onCleared()
        isAdvertising = false
        scanHasCooledDown = true
        leAdvertiser.stopAdvertising(advertiseCallback)
        leScanner.stopScan(scanCallback)
    }
}
