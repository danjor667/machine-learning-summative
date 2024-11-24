package com.example.glucoguardclient.ui.metics

import android.media.session.MediaSession.Token
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glucoguardclient.NavigationEvent
import com.example.glucoguardclient.ui.auth.login.DangerAlert

@Composable
fun MetricsInputScreen(
    viewModel: MetricsInputViewModel,
    onNavigateToPredictionScreen: (Float) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val navigationEvent by viewModel.navigationEvent.collectAsState()


    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToPredictionResult -> {
                val posPercentage = (navigationEvent as NavigationEvent.NavigateToPredictionResult).posPercentage
                onNavigateToPredictionScreen(posPercentage)
                viewModel.onNavigationHandled()
            } else -> { }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Input Prediction Metrics",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        MetricInput(
            label = "Glucose",
            value = uiState.Glucose,
            onValueChange = { viewModel.updateGlucose(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "Blood Pressure",
            value = uiState.BloodPressure,
            onValueChange = { viewModel.updateBloodPressure(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "Insulin",
            value = uiState.Insulin,
            onValueChange = { viewModel.updateInsulin(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "BMI",
            value = uiState.BMI.toString(),
            onValueChange = { viewModel.updateBMI(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "Age",
            value = uiState.Age.toString(),
            onValueChange = { viewModel.updateAge(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "prenancies",
            value = uiState.pregnancies.toString(),
            onValueChange = { viewModel.updatePregnancies(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "DPF",
            value = uiState.DiabetesPedigreeFunction.toString(),
            onValueChange = { viewModel.updateDiabetesPedigreeFunction(it) },
            keyboardType = KeyboardType.Decimal
        )

        MetricInput(
            label = "SkinThickness",
            value = uiState.SkinThickness.toString(),
            onValueChange = { viewModel.updateSkinThickness(it) },
            keyboardType = KeyboardType.Decimal
        )

        if(uiState.predicting){
            CircularProgressIndicator()
        }

        if(uiState.errorMessage != null){
            DangerAlert(
                message = uiState.errorMessage.toString(),
                onDismiss = { viewModel.dismissErrorDialog() }
            )

        }



        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick ={ viewModel.getPredictions() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text("predict")
        }
    }
}

@Composable
fun MetricInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        singleLine = true
    )
}


@Preview
@Composable
fun Page(){
    MetricsInputScreen(viewModel = MetricsInputViewModel(""), {})
}