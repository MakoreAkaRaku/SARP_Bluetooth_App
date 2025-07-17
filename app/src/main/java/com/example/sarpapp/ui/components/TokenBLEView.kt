package com.example.sarpapp.ui.components

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sarpapp.BLUETOOTH_PERMISSIONS
import com.example.sarpapp.viewmodel.api.TokenViewModel
import com.example.sarpapp.viewmodel.ble.BluetoothBLEViewModel
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun TokenBLEView(tokenViewModel: TokenViewModel, btHandler: BluetoothBLEViewModel) {
    val corroutineScope = rememberCoroutineScope()
    val tokens by tokenViewModel.tokenList.collectAsState()
    val isLoading by tokenViewModel.isLoading.collectAsState()
    val error by tokenViewModel.errorMessage.collectAsState()
    val chosenToken = rememberSaveable { mutableStateOf("") }
    val isAdvertising = btHandler.isAdvertising
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            for (perm in it) {
                if (!perm.value)
                    return@rememberLauncherForActivityResult
            }
            btHandler.broadcastMessage(chosenToken.value, BluetoothBLEViewModel.TOKEN_SHORT_UUID)
        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (error != null) {
            Text("Error: $error")
        }
        LazyColumn(
        ) {
            if (tokens.isEmpty()) {
                corroutineScope.launch {
                    tokenViewModel.fetchTokens()
                }
            } else if (isLoading) {
                item {
                    CircularProgressIndicator()
                }
            } else {

                items(tokens) {
                    ListItem(
                        headlineContent = { Text(it.token) },
                        modifier = Modifier
                            .animateItem()
                            .fillParentMaxWidth()
                            .padding(
                                vertical = 1.dp
                            )
                            .clickable(onClick = {
                                chosenToken.value = it.token
                            }),

                        )
                }
            }
        }
        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(200.dp, 50.dp),
            onClick = {
                corroutineScope.launch {
                    tokenViewModel.fetchTokens()
                }
            }) {
            Text("Refresh")
        }
        HorizontalDivider(modifier = Modifier.padding(10.dp))
        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(200.dp, 50.dp),
            enabled = !chosenToken.value.equals("") && !isAdvertising,
            onClick = {
                permissionLauncher.launch(
                    BLUETOOTH_PERMISSIONS
                )
            },
        ) {
            Text("Send Token")
        }
    }
}