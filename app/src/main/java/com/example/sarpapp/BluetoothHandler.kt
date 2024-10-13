package com.example.sarpapp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeAdvertiser
import android.bluetooth.le.BluetoothLeScanner
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService

class BluetoothHandler {
    private var bluetoothAdapter: BluetoothAdapter? = null
    constructor(context: AppCompatActivity) {

        if (Build.VERSION.SDK_INT in 1..JELLY_BEAN_MR1) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        }else {
            var bleManager = context.getSystemService<BluetoothManager>()
            if (bleManager != null) {
                bluetoothAdapter = bleManager.adapter
            }
        }
    }
}