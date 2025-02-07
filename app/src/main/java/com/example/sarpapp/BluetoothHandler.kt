package com.example.sarpapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresPermission

class BluetoothHandler(
    private val btManager: BluetoothManager,
) {

    private var bluetoothAdapter: BluetoothAdapter
    private final val REQUEST_PERMISSION_PHONE_STATE = 1
    private var isScanning = false
    private var isAdvertising = false
    private val BROADCAST_TIME: Long = 5000
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val leAdvertiserCallback = object : AdvertiseCallback() {

    }

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            when (callbackType) {
                ScanSettings.CALLBACK_TYPE_ALL_MATCHES -> println("Type All Matches")

                ScanSettings.CALLBACK_TYPE_FIRST_MATCH -> println("Type First Match")
                ScanSettings.CALLBACK_TYPE_FIRST_MATCH -> println("Type First Match")
                else -> println("I was not expecting anything else...")
            }
        }
    }

    init {
        bluetoothAdapter = if (Build.VERSION.SDK_INT in 1..JELLY_BEAN_MR1)
            BluetoothAdapter.getDefaultAdapter()
        else
            btManager.adapter
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan(timeToScanMs: Long): Boolean {
        if (isScanning) return false
        isScanning = true
        bluetoothAdapter.bluetoothLeScanner.startScan(leScanCallback)
        handler.postDelayed(
            {
                isScanning = false
                bluetoothAdapter.bluetoothLeScanner.stopScan(leScanCallback)
            },
            timeToScanMs
        )
        return true
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADVERTISE)
    fun broadcastMsg(ssid: String, pwd: String): Boolean {
        if (isAdvertising) return false;
        var advertiseMsg: AdvertiseData.Builder = AdvertiseData.Builder()
        var advertiseSettings: AdvertiseSettings.Builder = AdvertiseSettings.Builder()
        //TODO keep building the message plus the settings.
        isAdvertising = true
        handler.postDelayed(
            {
                isAdvertising = false
                bluetoothAdapter.bluetoothLeAdvertiser.stopAdvertising(leAdvertiserCallback)
            },
            BROADCAST_TIME
        )
        bluetoothAdapter.bluetoothLeAdvertiser.startAdvertising(
            advertiseSettings.build(),
            advertiseMsg.build(),
            leAdvertiserCallback
        )
        return true
    }
}