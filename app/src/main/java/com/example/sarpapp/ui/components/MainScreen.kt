package com.example.sarpapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sarpapp.data.ble.BluetoothBLEViewModel

@Composable
fun MainScreen(btHandler: BluetoothBLEViewModel = viewModel()) {
    Column {
        WiFiLEAdvertiseFormView(btHandler)
        HorizontalDivider(
            thickness = 2.dp
        )
        LeScannerView(btHandler)
    }
}