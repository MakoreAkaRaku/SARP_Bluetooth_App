package com.example.sarpapp.ui.components

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.sarpapp.BLUETOOTH_PERMISSIONS
import com.example.sarpapp.data.ble.BluetoothBLEViewModel

@SuppressLint("MissingPermission")
@Composable
fun LeScannerView(btViewModel: BluetoothBLEViewModel) {
    val context = LocalContext.current
    var switchVal by rememberSaveable { mutableStateOf(false) }
    val hasCooledDown = btViewModel.scanHasCooledDown
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results: Map<String, Boolean> ->
            for (result in results) {
                if (!result.value) {
                    switchVal = false
                    return@rememberLauncherForActivityResult
                }
            }
            btViewModel.scanResults.clear()
            btViewModel.startScan()
            Toast.makeText(
                context, "Scan Started!", Toast.LENGTH_SHORT
            ).show()
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
        ) {
            Text(
                "Enable scanning", fontSize = 5.em
            )
            Switch(switchVal, onCheckedChange = {
                switchVal = it
                if (switchVal) {
                    permissionLauncher.launch(BLUETOOTH_PERMISSIONS)
                } else btViewModel.stopScan()
            },
                enabled = hasCooledDown
            )
        }
        DevicesListView(btViewModel.scanResults)
    }
}