package com.example.sarpapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass.Device
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresPermission
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService


class BluetoothHandler(
    private val btManager: BluetoothManager,
) : ScanCallback() {

    private var bluetoothAdapter: BluetoothAdapter
    private final val REQUEST_PERMISSION_PHONE_STATE = 1
    private var isScanning = false
    private val handler: Handler = Handler(Looper.getMainLooper())

    init {
        bluetoothAdapter = if (Build.VERSION.SDK_INT in 1..JELLY_BEAN_MR1)
            BluetoothAdapter.getDefaultAdapter()
        else
            btManager.adapter
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan(timeToScanMs: Long) : Boolean {
        if (!isScanning)
        {
            isScanning = true
            bluetoothAdapter.bluetoothLeScanner.startScan(this)
            handler.postDelayed(
                {
                    isScanning = false
                    bluetoothAdapter.bluetoothLeScanner.stopScan(this)
                },
                timeToScanMs
            )
            return true
        }
        return false
    }

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        when(callbackType) {
            ScanSettings.CALLBACK_TYPE_ALL_MATCHES -> println("Type All Matches")

            ScanSettings.CALLBACK_TYPE_FIRST_MATCH -> println("Type First Match")
            ScanSettings.CALLBACK_TYPE_FIRST_MATCH -> println("Type First Match")
            else -> println("I was not expecting anything else...")
        }
    }

    override fun onBatchScanResults(results: List<ScanResult?>?) {
    }

    override fun onScanFailed(errorCode: Int) {
    }
}