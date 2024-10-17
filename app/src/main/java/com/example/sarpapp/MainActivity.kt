package com.example.sarpapp

import android.bluetooth.BluetoothManager
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sarpapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private final val BT_REQUEST_CODE = 1
    private lateinit var bluetoothHandler: BluetoothHandler
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val bluetoothLEAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
        if (!bluetoothLEAvailable)
            showExplanation("Get the fuck out", "Your device is stupid and doesn't even have bluetooth, for fuck sake",
                *BLUETOOTH_PERMISSIONS,
                permissionRequestCode = BT_REQUEST_CODE)
        if (!hasBTPermissions()) {
            showExplanation(
                "Bluetooth Permission",
                "To use this app, you need to allow bluetooth for Scan and Advertise",
                *BLUETOOTH_PERMISSIONS,
                permissionRequestCode = BT_REQUEST_CODE
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    private fun showExplanation(
        title: String,
        message: String,
        vararg permissions: String,
        permissionRequestCode: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.ok,
                DialogInterface.OnClickListener { _, id ->
                    requestPermissions(
                        permissions,
                        permissionRequestCode
                    )
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener {
                        _, id->exitProcess(0)
                })
        builder.create().show()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == BT_REQUEST_CODE)
        {
            val permissionsGranted = grantResults.all{
            it == PackageManager.PERMISSION_GRANTED
            }
            if (permissionsGranted)
                bluetoothHandler = BluetoothHandler(getSystemService<BluetoothManager>()!!)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}