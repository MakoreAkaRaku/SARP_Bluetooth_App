package com.example.sarpapp.ui.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sarpapp.BLUETOOTH_PERMISSIONS
import com.example.sarpapp.BluetoothHandler

@SuppressLint("MissingPermission")
@Composable
fun WiFiLEAdvertiseFormView(btHandler: BluetoothHandler) {
    var context = LocalContext.current
    var ssidVal by rememberSaveable { mutableStateOf("") }
    var pwdVal by rememberSaveable { mutableStateOf("") }
    var pwdVisible by rememberSaveable { mutableStateOf(false) }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            for (perm in it) {
                if (!perm.value)
                    return@rememberLauncherForActivityResult
            }
            btHandler.broadcastMsg(ssidVal, pwdVal)
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = ssidVal,
            label = { Text("SSID", textAlign = TextAlign.Center) },
            placeholder = { Text("Enter the WiFi SSID") },
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            maxLines = 1,
            singleLine = true,
            onValueChange = {
                if (it.length > BluetoothHandler.SSID_MAX_LENGTH) {
                    Toast.makeText(
                        context,
                        "SSID too long, only up to ${BluetoothHandler.SSID_MAX_LENGTH} characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OutlinedTextField
                }
                ssidVal = it
            },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(40.dp)
        )

        OutlinedTextField(
            value = pwdVal,
            onValueChange = {
                if (it.length > BluetoothHandler.PWD_MAX_LENGTH) {
                    Toast.makeText(
                        context,
                        "Password too long, only up to ${BluetoothHandler.PWD_MAX_LENGTH} characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OutlinedTextField
                }
                pwdVal = it
            },
            label = { Text("PASSWORD", textAlign = TextAlign.Center) },
            placeholder = { Text("Enter WiFi password") },
            singleLine = true,
            visualTransformation = if (pwdVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (pwdVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (pwdVisible) "Hide password" else "Show password"
                val iconTint = if (pwdVisible) Color.LightGray else Color.Gray
                IconButton(onClick = { pwdVisible = !pwdVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = iconTint)
                }
            },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(40.dp)
        )
        Button(
            onClick = {
                permissionLauncher.launch(
                    BLUETOOTH_PERMISSIONS
                )
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(200.dp, 50.dp)
        ) {
            Text("Send Signal")
        }

    }
}