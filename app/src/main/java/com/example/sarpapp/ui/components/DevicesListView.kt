package com.example.sarpapp.ui.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sarpapp.LeScanResultList

@SuppressLint("MissingPermission")
@Composable
fun DevicesListView(deviceList: LeScanResultList) {
    LazyColumn(
    ) {
        items(deviceList.getList()) {
            var deviceName = if (it.device.name == null) "N/A" else it.device.name
            var context = LocalContext.current
            ListItem(
                headlineContent = {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${deviceName}")
                        Text("RSSI: ${it.rssi} dBm")
                    }
                },
                modifier = Modifier
                    .animateItem()
                    .fillParentMaxWidth()
                    .padding(
                        vertical = 0.dp
                    )
                    .clickable {
                        Toast.makeText(
                            context,
                            "MAC Address: ${it.device.address}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            )
        }
    }
}
