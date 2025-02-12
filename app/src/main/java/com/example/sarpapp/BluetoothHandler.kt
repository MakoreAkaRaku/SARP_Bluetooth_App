package com.example.sarpapp

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresPermission

class BluetoothHandler(
    private val bluetoothAdapter: BluetoothAdapter,
) {

    private var isScanning = false
    private var isAdvertising = false
    private val BROADCAST_TIME: Long = 5000
    private val handler: Handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingPermission")
    private val shutdownScanCallback = {
        isScanning = false
        bluetoothAdapter.bluetoothLeScanner.stopScan(leScanCallback)
    }

    private val leAdvertiserCallback = object : AdvertiseCallback() {
    }

    private val leDeviceList = LeScanResultList()

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {

            if (callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES && result != null) {
                leDeviceList.addScanResult(result)
            }
        }
    }

    fun getDeviceListViewModel(): LeScanResultList {
        return leDeviceList
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan(timeToScanMs: Long = BROADCAST_TIME): Boolean {
        if (isScanning) return false
        isScanning = true
        leDeviceList.clear()
        bluetoothAdapter.bluetoothLeScanner.startScan(leScanCallback)
        handler.postDelayed(
            shutdownScanCallback,
            timeToScanMs
        )
        return true
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        handler.removeCallbacks(shutdownScanCallback)
        bluetoothAdapter.bluetoothLeScanner.stopScan(leScanCallback)
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