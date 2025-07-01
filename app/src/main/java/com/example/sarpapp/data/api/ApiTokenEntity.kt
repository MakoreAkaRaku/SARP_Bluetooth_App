package com.example.sarpapp.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class UserCredentialsEntity(
    @SerializedName("username")
    var username: String,
    @SerializedName("pwd")
    var password: String
)

data class ApiTokenEntity(
    @SerializedName("token_api")
    val token: String
)

data class CredentialsResponse(
    @SerializedName("accessToken")
    val accessToken: String
)


interface ApiService {

    @POST("/api/authentication/login")
    suspend fun login(@Body body: UserCredentialsEntity): CredentialsResponse

    @POST("/api/authentication/logout")
    suspend fun logout()

    @GET("/api/token/")
    suspend fun getTokens(@Header("Cookie") authToken: String): List<ApiTokenEntity>

}