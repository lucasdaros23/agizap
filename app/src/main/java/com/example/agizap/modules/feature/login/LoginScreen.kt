package com.example.agizap.modules.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.modules.auth.AuthViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, authViewModel: AuthViewModel, navController: NavHostController) {
    val uistate = viewModel.uiState.collectAsStateWithLifecycle().value
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = uistate.email,
                onValueChange = { viewModel.onEmailChange(it) }
            )
            Spacer(modifier = Modifier.size(30.dp))
            OutlinedTextField(
                value = uistate.password,
                onValueChange = { viewModel.onPasswordChange(it) }
            )
            Spacer(modifier = Modifier.size(30.dp))
            Button(
                onClick = {authViewModel.register(uistate.email, uistate.password, navController)}
            ) {
                Text("Entrar")
            }
    }

}