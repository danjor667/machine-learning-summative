package com.example.glucoguardclient.ui.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glucoguardclient.NavigationEvent
import com.example.glucoguardclient.data.send.User
import com.example.glucoguardclient.network.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    private val apiService = RetrofitClient.apiService

    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isAuthenticating = true) }
            try {
                val user = User(
                    email = uiState.value.email,
                    password = uiState.value.password,
                    firstName = uiState.value.firstName,
                    lastName = uiState.value.lastName
                )
                val response = apiService.register(user)

                if (response.isSuccessful) {
                    _uiState.update { it.copy(authenticationSucceed = true) }
                    navigateToLogin()
                } else {
                    _uiState.update {
                        it.copy(authErrorMessage = "Registration failed: ${response.message()}")
                    }
                    Log.v("msg", "error occured on register")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(authErrorMessage = "Registration failed: ${e.message ?: "Unknown error"}")
                }
            } finally {
                _uiState.update { it.copy(isAuthenticating = false) }
            }
        }
    }

    private fun navigateToLogin() {
        _navigationEvent.value = NavigationEvent.NavigateToLogin
    }

    // Call this method when navigation is handled to reset the event
    fun onNavigationHandled() {
        _navigationEvent.value = null
    }


    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateFirstName(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun updateLastName(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }


    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun dismissErrorDialog(){
        _uiState.update { it.copy(authErrorMessage = null) }
    }


}





data class SignUpUiState(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false
)