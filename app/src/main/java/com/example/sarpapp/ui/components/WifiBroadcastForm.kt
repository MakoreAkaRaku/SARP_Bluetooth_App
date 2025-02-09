package com.example.sarpapp.ui.components

import android.content.pm.PackageManager
import android.graphics.ColorSpace.Rgb
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sarpapp.BLUETOOTH_PERMISSIONS

@Composable
@Preview
fun WifiBroadcastForm() {
    val context = LocalContext.current
    val hasBLECapabilities by produceState(false) {
        value = context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }
//    if (!hasBLECapabilities) {
//        NoBLEWarningScreen()
//        return
//    }

    var ssidVal by rememberSaveable { mutableStateOf("") }
    var pwdVal by rememberSaveable { mutableStateOf("") }
    var pwdVisible by rememberSaveable { mutableStateOf(false) }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = ssidVal,
            label = { Text("SSID", textAlign = TextAlign.Center, color = Color.White) },
            placeholder = { Text("Enter the WiFi SSID", color = Color.LightGray) },
            textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
            maxLines = 1,
            singleLine = true,
            onValueChange = { ssidVal = it },
            modifier = Modifier
                .padding(20.dp)
                .border(0.dp, color = Color.Transparent),
            shape = RoundedCornerShape(40.dp)
        )

        TextField(
            value = pwdVal,
            onValueChange = { pwdVal = it },
            label = { Text("PASSWORD", textAlign = TextAlign.Center, color = Color.White) },
            placeholder = { Text("Enter WiFi password", color = Color.LightGray) },
            singleLine = true,
            textStyle = TextStyle(color = Color.White),
            visualTransformation = if (pwdVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (pwdVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                // TODO: make localized descriptions
                val description = if (pwdVisible) "Hide password" else "Show password"
                val iconTint = if (pwdVisible) Color.LightGray else Color.Gray
                IconButton(onClick = { pwdVisible = !pwdVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = iconTint)
                }
            },
            modifier = Modifier.padding(20.dp).border(0.dp,Color.Transparent),
            shape = RoundedCornerShape(40.dp)
        )
        Button(
            onClick = {
                var result = permissionLauncher.launch(
                    BLUETOOTH_PERMISSIONS
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors( Color.DarkGray),
            modifier = Modifier.size(200.dp,50.dp)
        ) {
            Text("Send Signal", color = Color.White)
        }
    }
}