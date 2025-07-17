package com.example.sarpapp.ui.components

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sarpapp.viewmodel.api.TokenViewModel


@Composable
fun Navigation() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val hasBLECapabilities = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    val navController = rememberNavController()
    val tokenViewModel = TokenViewModel()
    NavHost(
        navController = navController,
        startDestination = if (hasBLECapabilities) Screen.LoginForm.route else Screen.BLENotSupportedScreen.route
    ){
        composable(Screen.LoginForm.route) {
            LoginScreen(navController, tokenViewModel)
        }
        composable(Screen.MainScreen.route) {
            MainScreen(tokenViewModel)
        }
        composable(Screen.BLENotSupportedScreen.route) {
            NoBLESupportedScreen()
        }
    }
}


sealed class Screen(val route: String) {
    object MainScreen : Screen("main_view")
    object LoginForm : Screen("login")
    object BLENotSupportedScreen : Screen("not_supported")
}