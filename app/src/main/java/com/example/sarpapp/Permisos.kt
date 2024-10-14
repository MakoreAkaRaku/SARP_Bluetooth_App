package com.example.sarpapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


fun AppCompatActivity.hasPermission(str: String): Boolean {
    val hasPermissionInt = ActivityCompat.checkSelfPermission(this, str)
    return hasPermissionInt == PackageManager.PERMISSION_GRANTED
}

fun AppCompatActivity.hasPermissions(vararg permissions: String): Boolean {
    return permissions.all { hasPermission(it) }
}

val BLUETOOTH_PERMISSIONS = arrayOf(
    Manifest.permission.BLUETOOTH_SCAN,
    Manifest.permission.BLUETOOTH_ADVERTISE
)

fun AppCompatActivity.hasBTPermissions() = hasPermissions(*BLUETOOTH_PERMISSIONS)

