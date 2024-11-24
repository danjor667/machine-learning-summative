package com.example.glucoguardclient.data.send

import com.google.gson.annotations.SerializedName

data class User(
    val email:String,
    val password: String,
    @SerializedName("first_name")val firstName: String,
    @SerializedName("last_name")val lastName: String
)