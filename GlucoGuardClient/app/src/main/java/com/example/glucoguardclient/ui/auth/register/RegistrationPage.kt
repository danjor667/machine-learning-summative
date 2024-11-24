package com.example.glucoguardclient.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glucoguardclient.NavigationEvent
import com.example.glucoguardclient.R
import com.example.glucoguardclient.ui.auth.login.DangerAlert
import com.example.glucoguardclient.ui.composable.CustomTextField


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()


    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToLogin -> {
                onNavigateToLogin()
                viewModel.onNavigationHandled()
            }
            else -> { }
        }
    }


    if(uiState.authErrorMessage != null){
        DangerAlert(
            message = uiState.authErrorMessage.toString(),
            onDismiss = { viewModel.dismissErrorDialog() }
        )
    }


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.background
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(
                    top = 120.dp, //ExtraLargeSpacing + LargeSpacing,
                    start = 16.dp, //LargeSpacing + MediumSpacing,
                    end = 16.dp, //LargeSpacing + MediumSpacing,
                    bottom = 8.dp, //LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy( 24.dp) //LargeSpacing)
        ) {


            Image(
                painterResource(id = R.drawable.logo), "App Logo", Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp)
            )


            CustomTextField(
                value = uiState.firstName,
                onValueChange = { newValue ->
                    viewModel.updateFirstName(newValue)
                },
                hint = R.string.first_hint
            )

            CustomTextField(
                value = uiState.lastName,
                onValueChange = { newValue ->
                    viewModel.updateLastName(newValue)
                },
                hint = R.string.last_hint
            )

            CustomTextField(
                value = uiState.email,
                onValueChange = { newValue ->
                    viewModel.updateEmail(newValue)
                },
                hint = R.string.email_hint,
                keyboardType = KeyboardType.Email
            )

            CustomTextField(
                value = uiState.password,
                onValueChange = { newValue ->
                    viewModel.updatePassword(newValue)
                },
                hint = R.string.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            val ButtonHeight = 46.dp
            Button(
                onClick = {
                    viewModel.register()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = null,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Register")
            }


            GoToLogin(modifier, onNavigateToLogin)

        }

        if (uiState.isAuthenticating) {
            CircularProgressIndicator()
        }
    }


}



@Composable
fun GoToLogin(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
            6.dp
        )
    ) {
        Text(text = "Have already an account?", style = MaterialTheme.typography.titleSmall)
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.clickable { onNavigateToLogin() }
        )
    }
}


@Preview
@Composable
fun Test() {

    SignUpScreen(
        viewModel = RegisterViewModel(),
        onNavigateToLogin = { /*TODO*/ }
    )
}