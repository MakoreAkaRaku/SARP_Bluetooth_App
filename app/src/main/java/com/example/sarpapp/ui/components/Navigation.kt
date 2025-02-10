package com.example.sarpapp.ui.components

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(packageManager: PackageManager) {
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