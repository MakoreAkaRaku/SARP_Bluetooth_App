package com.example.sarpapp.ui.components

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.app.ActivityCompat
import com.example.sarpapp.BLUETOOTH_PERMISSIONS
import com.example.sarpapp.BluetoothHandler


val dummyVars = arrayOf("Dummy1", "Dummy2", "Dummy3","Dummy1", "Dummy2", "Dummy3")
@Composable
@Preview
fun FoundDevicesContent (){
    var switchVal by rememberSaveable { mutableStateOf(false) }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }

    Column(
        modifier = Modifier.padding(20.dp).fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        )
        {
            Text(
                "Enable scanning",
                color = Color.White,
                fontSize = 5.em
                )
            Switch(
                switchVal,
                onCheckedChange = {
                    switchVal = it
                    if (switchVal) permissionLauncher.launch(
                        BLUETOOTH_PERMISSIONS
                    )
                },
                colors = SwitchDefaults.colors(Color.LightGray,Color.DarkGray,Color.LightGray)
            )
        }

        Row (

        )
        {

        }

    }
}