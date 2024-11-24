package com.example.glucoguardclient.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glucoguardclient.NavigationEvent
import com.example.glucoguardclient.data.send.PostData
import com.example.glucoguardclient.network.api.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val token: String): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()





    fun navigateToPredict(){
        _navigationEvent.value = NavigationEvent.NavigateToPredictScreen(token)

    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

}

data class HomeUiState(
    val state: String? = null
)