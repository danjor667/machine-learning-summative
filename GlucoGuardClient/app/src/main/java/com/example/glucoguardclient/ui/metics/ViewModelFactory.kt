package com.example.glucoguardclient.ui.metics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucoguardclient.ui.home.HomeViewModel

class ViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MetricsInputViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MetricsInputViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}