package com.example.sarpapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.sarpapp.ui.components.Navigation
import com.example.sarpapp.ui.theme.SARPTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SARPTheme {
                Navigation()
            }
        }
    }

}