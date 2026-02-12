package com.example.agizap.modules.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.feature.login.components.LoginConfirmButton
import com.example.agizap.modules.feature.login.components.LoginTextButton
import com.example.agizap.modules.feature.login.components.LoginTextField
import com.example.agizap.modules.navigation.Routes

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
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
                onClick = {
                    viewModel.login(uistate.email, uistate.password)
                          },
                text = "Entrar"
            )
            Row(
                modifier = Modifier.fillMaxWidth(.75f),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                LoginTextButton(
                    text = "Criar Conta",
                    onClick = {
                        viewModel.onChangeMessage("Essa função ainda não foi implementada")
                        viewModel.onShowAlertLogin()
                    }
                )
                LoginTextButton(
                    text = "Redefinir senha",
                    onClick = {
                        viewModel.onChangeMessage("Essa função ainda não foi implementada")
                        viewModel.onShowAlertLogin()
                    }

                )
            }
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

        if (uistate.showAlert){
            Alert(
                title = uistate.message,
                confirmText = "Ok",
                confirmAction = {
                    viewModel.onShowAlertLogin()
                    if (uistate.success){
                        navController.navigate(Routes.HOME)
                    }
                }
            )
        }
    }

}