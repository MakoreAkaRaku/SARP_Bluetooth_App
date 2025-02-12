package com.example.sarpapp

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sarpapp.ui.components.Navigation
import com.example.sarpapp.ui.theme.SARPTheme

class MainActivity : AppCompatActivity() {

    lateinit var btAdapter: BluetoothAdapter
    val requestToTurnBT =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                askTurnBT()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SARPTheme {
                Navigation(packageManager,btAdapter)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        var btManager: BluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = if (Build.VERSION.SDK_INT in 1..JELLY_BEAN_MR1)
            BluetoothAdapter.getDefaultAdapter()
        else
            btManager.adapter
        askTurnBT()
    }

    fun askTurnBT() {
        if (!btAdapter.isEnabled) {
            var requestIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestToTurnBT.launch(requestIntent)
        }
    }

    //Doubting if this is good practice...
    override fun onUserInteraction() {
        super.onUserInteraction()
        askTurnBT()
    }
}