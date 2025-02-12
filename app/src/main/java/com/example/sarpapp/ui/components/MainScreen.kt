package com.example.sarpapp.ui.components

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.sarpapp.BluetoothHandler

@Composable
fun MainScreen(btAdapter: BluetoothAdapter) {
    var btHandler = BluetoothHandler(btAdapter)
    Column(

    ) {
        WiFiLEAdvertiseFormView(btHandler)
        HorizontalDivider(
            thickness = 2.dp
        )
        LeScannerView(btHandler)
    }
}