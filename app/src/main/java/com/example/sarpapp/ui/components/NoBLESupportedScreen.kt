package com.example.sarpapp.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em


@Composable
@Preview
fun NoBLESupportedScreen() {
    val description = "Your device does not support \'BLE\' technology"
    val mainActivity = (LocalContext.current as? Activity)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector  = Icons.Filled.Error, contentDescription = "BLE permission warning", tint = Color.Red, modifier = Modifier.size(100.dp))
        Spacer(Modifier.size(16.dp))
        Text(fontSize = 5.em,text = description,textAlign = TextAlign.Center)
        Spacer(Modifier.size(16.dp))
        Button(onClick = {
            mainActivity?.finish()
        },
            modifier = Modifier.size(150.dp,50.dp)
        ) {
            Text("Close App")
        }
    }
}
