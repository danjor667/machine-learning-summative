package com.example.glucoguardclient.ui.auth.login

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.glucoguardclient.NavigationEvent
import com.example.glucoguardclient.R
import com.example.glucoguardclient.ui.composable.CustomTextField


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onNavigateToSignup: () -> Unit,
    onNavigateToHome: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()


    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToSignUp -> {
                onNavigateToSignup()
                viewModel.onNavigationHandled()
            }
            is NavigationEvent.NavigateToHome -> {
                val token = (navigationEvent as NavigationEvent.NavigateToHome).token
                onNavigateToHome(token)
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
                    start = 16.dp,  //LargeSpacing + MediumSpacing,
                    end = 16.dp, //LargeSpacing + MediumSpacing,
                    bottom = 4.dp // LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy( 24.dp )//LargeSpacing)
            ) {


            Image(
                painterResource(id = R.drawable.logo), "App Logo", Modifier
                    .size(120.dp)
                    .padding(bottom = 32.dp)
            )

                CustomTextField(
                    value = uiState.email,
                    onValueChange = {newValue ->
                        viewModel.updateEmail(newValue)
                    },
                    hint =   R.string.email_hint,
                    keyboardType = KeyboardType.Email
                )

                CustomTextField(
                    value = uiState.password,
                    onValueChange = {newValue ->
                        viewModel.updatePassword(newValue)
                    },
                    hint = R.string.password_hint,
                    keyboardType = KeyboardType.Password,
                    isPasswordTextField = true
                )

                val ButtonHeight = 46.dp
                Button(
                    onClick = {
                        viewModel.signIn()
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(ButtonHeight),
                    elevation =  null, //ButtonDefaults.elevation( defaultElevation = 0.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Login") //stringResource(id = R.string.login_button_label))
                }

                GoToSignup(modifier, onNavigateToSignup)
            }



            if (uiState.isAuthenticating) {
                CircularProgressIndicator()
            }
    }





}



@Composable
fun GoToSignup(
    modifier: Modifier = Modifier,
    onNavigateToSignup: () -> Unit
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
             4.dp //SmallSpacing
        )
    ) {
        Text(text = "Don't have an account?", style = MaterialTheme.typography.titleSmall)

        Text(
            text = "SignUp",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.clickable { onNavigateToSignup() }
        )
    }
}


@Composable
fun DangerAlert(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Failed Action",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleSmall
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleSmall
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK", color = MaterialTheme.colorScheme.error)
            }
        },

    )
}




@Preview
@Composable
fun showPage(){

    LoginScreen(
        viewModel = LoginViewModel(),
        onNavigateToSignup = { /*TODO*/ },
        onNavigateToHome = { }
    )
}