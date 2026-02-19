package com.example.agizap.modules.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.ConfirmButton
import com.example.agizap.modules.feature.login.components.LoginTextField
import com.example.agizap.modules.feature.register.components.RegisterTextField
import com.example.agizap.modules.feature.register.components.ReturnButton
import com.example.agizap.modules.navigation.Routes

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    navController: NavHostController
) {
    val uistate = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    Box() {
        Row(
            Modifier.padding(10.dp),
        ) {
            ReturnButton(
                onClick = { navController.popBackStack() }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RegisterTextField(
                value = uistate.email,
                onValueChange = { viewModel.onEmailChange(it) },
                visualTransformation = VisualTransformation.None,
                label = "Email"
            )
            Spacer(modifier = Modifier.size(30.dp))
            RegisterTextField(
                value = uistate.username,
                onValueChange = { viewModel.onUsernameChange(it) },
                visualTransformation = VisualTransformation.None,
                label = "Nome do usuário"
            )
            Spacer(modifier = Modifier.size(30.dp))
            RegisterTextField(
                value = uistate.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                visualTransformation = PasswordVisualTransformation(),
                label = "Senha"
            )
            Spacer(modifier = Modifier.size(30.dp))
            ConfirmButton(
                onClick = {
                    viewModel.onButtonEnabled()
                    if (
                        uistate.email.trim() != "" &&
                        uistate.password.trim() != "" &&
                        uistate.username.trim() != ""
                    ) {
                        viewModel.register(context, email = uistate.email, password = uistate.password)
                    } else {
                        viewModel.onChangeMessage("Preencha todos os campos corretamente")
                        viewModel.onShowAlert()
                    }
                },
                text = "Criar conta",
                enabled = uistate.buttonEnabled
            )

        }
        if (uistate.showAlert) {
            Alert(
                title = uistate.message,
                confirmText = "OK",
                confirmAction = {
                    viewModel.onShowAlert()
                    viewModel.onButtonEnabled()
                    if (uistate.success) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.REGISTER) { inclusive = true }
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}