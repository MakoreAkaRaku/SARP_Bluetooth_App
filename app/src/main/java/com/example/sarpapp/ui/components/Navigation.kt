package com.example.sarpapp.ui.components

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val hasBLECapabilities = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (hasBLECapabilities) Screen.MainScreen.route else Screen.BLENotSupportedScreen.route
    ){
        composable(Screen.MainScreen.route) {
            MainScreen()
        }
        composable(Screen.BLENotSupportedScreen.route) {
            NoBLESupportedScreen()
        }
    }
}


sealed class Screen(val route: String) {
    object MainScreen : Screen("main_view")
    object BLENotSupportedScreen : Screen("not_supported")
}