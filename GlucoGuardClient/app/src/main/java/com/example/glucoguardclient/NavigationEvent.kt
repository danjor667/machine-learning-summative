package com.example.glucoguardclient

import android.health.connect.datatypes.units.Percentage


sealed class NavigationEvent {
    object NavigateToLogin : NavigationEvent()
    object NavigateToSignUp : NavigationEvent()
    data class NavigateToHome(val token: String) : NavigationEvent()
    data class NavigateToPredictScreen(val token: String): NavigationEvent()
    data class NavigateToPredictionResult(
        val posPercentage: Float
    ) : NavigationEvent()

}