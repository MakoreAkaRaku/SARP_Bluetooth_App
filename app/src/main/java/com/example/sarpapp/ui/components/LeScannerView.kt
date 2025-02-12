package com.example.sarpapp.ui.components

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.sarpapp.BLUETOOTH_PERMISSIONS
import com.example.sarpapp.BluetoothHandler

@SuppressLint("MissingPermission")
@Composable
fun LeScannerView(btHandler: BluetoothHandler) {
    val scannedDevicesViewModel = btHandler.getDeviceListViewModel()
    var switchVal by rememberSaveable { mutableStateOf(false) }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results: Map<String, Boolean> ->
            for (result in results) {
                if (!result.value) {
                    switchVal = false
                    return@rememberLauncherForActivityResult
                }
            }
            btHandler.startScan(1000)
        }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        )
        {
            Text(
                "Enable scanning",
                fontSize = 5.em
            )
            Switch(
                switchVal,
                onCheckedChange = {
                    switchVal = it
                    if (switchVal) {
                        permissionLauncher.launch(BLUETOOTH_PERMISSIONS)
                    }
                    else
                        scannedDevicesViewModel.clear()
                }
            )
        }
        DevicesListView(scannedDevicesViewModel)
    }
}