package com.example.glucoguardclient.data.send

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("Pregnancies")val pregnancies: Int,
    @SerializedName("Glucose")val Glucose: Double,
    @SerializedName("BloodPressure")val BloodPressure: Double,
    @SerializedName("SkinThickness")val SkinThickness: Double,
    @SerializedName("Insulin")val Insulin: Double,
    @SerializedName("BMI")val BMI: Double,
    @SerializedName("DiabetesPedigreeFunction")val DiabetesPedigreeFunction: Double,
    @SerializedName("Age")val Age: Int
)



//data = {"Pregnancies": 10,
//    "Glucose": 182,
//    "BloodPressure": 84,
//    "SkinThickness": 40,
//    "Insulin": 105,
//    "BMI": 33.6,
//    "DiabetesPedigreeFunction": 0.501,
//    "Age": 20
//}
