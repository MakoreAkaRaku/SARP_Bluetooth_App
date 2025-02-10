package com.example.sarpapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MainScreen() {
    Column(

    ) {
        WifiBroadcastForm()
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 5.dp
        )
        FoundDevicesContent()
    }
}