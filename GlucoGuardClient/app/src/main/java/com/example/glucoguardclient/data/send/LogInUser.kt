package com.example.glucoguardclient.data.send

import com.google.gson.annotations.SerializedName


data class LogInUser(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)