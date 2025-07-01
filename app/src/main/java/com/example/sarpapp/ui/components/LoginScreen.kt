package com.example.sarpapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.sarpapp.data.api.TokenViewModel

@Composable
fun LoginScreen(navController: NavController, tokenViewModel: TokenViewModel) {
    Column {
        LoginFormView(navController, tokenViewModel)
    }
}
