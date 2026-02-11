package com.example.agizap.modules.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.modules.auth.AuthViewModel
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.feature.login.components.LoginConfirmButton
import com.example.agizap.modules.feature.login.components.LoginTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val uistate = viewModel.uiState.collectAsStateWithLifecycle().value
    Box() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginTextField(
                value = uistate.email,
                onValueChange = { viewModel.onEmailChange(it) },
                visualTransformation = VisualTransformation.None,
                label = "Email"
            )
            Spacer(modifier = Modifier.size(30.dp))
            LoginTextField(
                value = uistate.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                visualTransformation = PasswordVisualTransformation(),
                label = "Senha"
            )
            Spacer(modifier = Modifier.size(30.dp))
            LoginConfirmButton(
                onClick = { authViewModel.login(uistate.email, uistate.password, navController) },
                text = "Entrar"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Agizap", fontSize = 80.sp)
        }
        Alert()
    }

}