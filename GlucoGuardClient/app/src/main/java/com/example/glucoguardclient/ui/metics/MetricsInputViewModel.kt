package com.example.glucoguardclient.ui.metics

import android.renderscript.ScriptIntrinsicYuvToRGB
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.glucoguardclient.data.send.PostData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.glucoguardclient.NavigationEvent
import com.example.glucoguardclient.network.api.RetrofitClient
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MetricsInputViewModel(private val token: String): ViewModel() {
    private val _uiState = MutableStateFlow(PredictScreenState())
    val uiState: StateFlow<PredictScreenState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()


    private val apiService = RetrofitClient.apiService



    fun getPredictions(){

        viewModelScope.launch{
            try{
                val data = PostData(
                    pregnancies = uiState.value.pregnancies.toInt(),
                    Glucose = uiState.value.Glucose.toDouble(),
                    BloodPressure = uiState.value.BloodPressure.toDouble(),
                    SkinThickness = uiState.value.SkinThickness.toDouble(),
                    Insulin = uiState.value.Insulin.toDouble(),
                    BMI = uiState.value.BMI.toDouble(),
                    DiabetesPedigreeFunction = uiState.value.DiabetesPedigreeFunction.toDouble(),
                    Age = uiState.value.Age.toInt()
                )
                _uiState.update { it.copy(predicting = true) }

                val response = apiService.getPrediction(postData = data, token="Token ${token}")
                if(response.isSuccessful){
                    _uiState.update { it.copy(predicting = false) }
                    val prediction = response.body()!!.prediction[0]
                    val negPercentage = response.body()!!.probability[0]
                    val posPercentage = response.body()!!.probability[1].toFloat()
                    _navigationEvent.value = NavigationEvent.NavigateToPredictionResult(posPercentage)


                }else{
                   _uiState.update { it.copy(errorMessage = "sorry an error occurred") }
                }

            }catch (e: Exception){
                _uiState.update { it.copy(errorMessage = "Sorry an error occurred") }
            }

        }
    }


    fun updatePregnancies(value: String){
        _uiState.update { it.copy(pregnancies = value) }
    }

    fun updateGlucose(value: String){
        _uiState.update { it.copy(Glucose = value) }

    }

    fun updateAge(value: String){
        _uiState.update { it.copy(Age = value) }

    }

    fun updateBMI(value: String){
        _uiState.update { it.copy(BMI = value) }

    }

    fun updateDiabetesPedigreeFunction(value: String){
        _uiState.update { it.copy(DiabetesPedigreeFunction = value) }

    }

    fun updateSkinThickness(value: String){
        _uiState.update { it.copy(SkinThickness = value) }

    }

    fun updateBloodPressure(value: String){
        _uiState.update { it.copy(BloodPressure = value) }

    }


    fun updateInsulin(value: String){
        _uiState.update { it.copy(Insulin = value) }

    }

    fun dismissErrorDialog(){
        _uiState.update { it.copy(errorMessage = null) }
    }


    fun navigateToResultScreen(posPercentage: Float){
        _navigationEvent.value = NavigationEvent.NavigateToPredictionResult(
            posPercentage
        )

    }

    fun onNavigationHandled(){
        _navigationEvent.value = null
    }


}


data class PredictScreenState(
    val pregnancies: String = "",
    val Glucose: String = "",
    val BloodPressure: String = "",
    val SkinThickness: String = "",
    val Insulin: String = "",
    val BMI: String = "",
    val DiabetesPedigreeFunction: String = "",
    val Age: String = "",
    val predicting: Boolean = false,
    val resultReceived: Boolean = false,
    val errorMessage: String? = null
)