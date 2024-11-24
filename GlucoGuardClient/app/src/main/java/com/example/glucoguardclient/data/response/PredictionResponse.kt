package com.example.glucoguardclient.data.response

data class PredictionResponse(
    val prediction: List<Int>,
    val probability: List<Double>
)