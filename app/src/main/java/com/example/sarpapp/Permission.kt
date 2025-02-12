package com.example.sarpapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


fun AppCompatActivity.hasPermission(str: String): Boolean {
    val hasPermissionInt = ActivityCompat.checkSelfPermission(this, str)
    return hasPermissionInt == PackageManager.PERMISSION_GRANTED
}

fun AppCompatActivity.hasPermissions(vararg permissions: String): Boolean {
    return permissions.all { hasPermission(it) }
}

/**
 * List of permissions needed to use this application
 */
val BLUETOOTH_PERMISSIONS = buildList<String> {
    addAll(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
        )
    )
    //From Android 12 we must take care of these flags, else it doesn't matter
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        addAll(
            listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        )
    }
}.toTypedArray()

fun AppCompatActivity.hasBTPermissions() = hasPermissions(*BLUETOOTH_PERMISSIONS)

