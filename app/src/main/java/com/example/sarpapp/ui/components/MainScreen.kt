package com.example.sarpapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sarpapp.data.api.TokenViewModel
import com.example.sarpapp.data.ble.BluetoothBLEViewModel

@Composable
fun MainScreen(tokenViewModel: TokenViewModel, btHandler: BluetoothBLEViewModel = viewModel()) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1)
    )
    Column(modifier = Modifier.fillMaxSize()){

        HorizontalPager(state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fill,
            beyondViewportPageCount = 1,
            flingBehavior = fling
        ) { page ->
            when (page) {
                0 -> TokenBLEView(tokenViewModel, btHandler)
                1 -> WiFiLEAdvertiseFormView(btHandler)
                2 -> LeScannerView(btHandler)
            }
        }

    }
}