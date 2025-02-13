package com.example.sarpapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sarpapp.data.ble.ScannedDevice

@SuppressLint("MissingPermission")
@Composable
fun DevicesListView(deviceList: List<ScannedDevice>) {

    LazyColumn(
    ) {
        items(deviceList) {
            ListItem(
                headlineContent = { Text(it.name) },
                trailingContent = { Text("RSSI: ${it.rssi} dBm") },
                supportingContent = { Text(text=it.macAddress) },
                modifier = Modifier
                    .animateItem()
                    .fillParentMaxWidth()
                    .padding(
                        vertical = 0.dp
                    )
            )
        }
    }
}
