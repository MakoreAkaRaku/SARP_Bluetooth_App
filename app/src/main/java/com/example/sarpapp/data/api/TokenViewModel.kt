package com.example.sarpapp.data.api

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TokenViewModel(
    private val apiService: ApiService = NetworkModule.getApiService() // Or inject
) : ViewModel() {
    val credentialsResponse = MutableStateFlow<CredentialsResponse?>(null)
    private lateinit var authToken: String
    private val _tokenList = MutableStateFlow<List<ApiTokenEntity>>(emptyList())
    val tokenList: StateFlow<List<ApiTokenEntity>> = _tokenList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    suspend fun fetchTokens() {
        _isLoading.value = true
        _errorMessage.value = null
        try {
            if (credentialsResponse.value != null) {
                val tokenAccess = credentialsResponse.value!!.accessToken
                _tokenList.value = apiService.getTokens(authToken)

            } else throw Exception("No credentials")
        } catch (e: Exception) {
            _errorMessage.value = "Failed to load tokens: ${e.message}"
            // Log.e("TokenViewModel", "Error fetching tokens", e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        try {
            val response = apiService.login(UserCredentialsEntity(username, password))
            println(response)
            authToken = "authorization=${response.accessToken}; "
            credentialsResponse.value = response
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return credentialsResponse.value != null
    }
}