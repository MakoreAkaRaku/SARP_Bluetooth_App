package com.example.sarpapp

import android.bluetooth.le.ScanResult
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class LeScanResultList: ViewModel() {

    private val scanResults = mutableStateListOf<ScanResult>()

    fun addScanResult(scanResult: ScanResult) {
        scanResults.add(scanResult)
    }

    fun getList() = scanResults

    fun clear(){
        scanResults.clear()
    }

}