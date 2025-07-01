package com.example.sarpapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object NetworkModule {

    val retrofit by lazy {
        val loginInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val URL =  "https://sarp01.westeurope.cloudapp.azure.com"

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(loginInterceptor)
            .build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}