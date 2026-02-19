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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.ConfirmButton
import com.example.agizap.modules.feature.login.components.LoginTextButton
import com.example.agizap.modules.feature.login.components.LoginTextField
import com.example.agizap.modules.navigation.Routes

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val uistate = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
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
            ConfirmButton(
                onClick = {
                    viewModel.onButtonEnabled()
                    if (uistate.email != "" && uistate.password != "") {
                        viewModel.login(context, uistate.email, uistate.password)
                    }else{
                        viewModel.onChangeMessage("Preencha todos os campos corretamente")
                        viewModel.onShowAlert()
                    }
                },
                text = "Entrar",
                enabled = uistate.buttonEnabled
            )
            Row(
                modifier = Modifier.fillMaxWidth(.75f),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                LoginTextButton(
                    text = "Criar Conta",
                    onClick = {
                        navController.navigate(Routes.REGISTER)
                    }
                )
                LoginTextButton(
                    text = "Redefinir senha",
                    onClick = {
                        viewModel.onChangeMessage("Essa função ainda não foi implementada")
                        viewModel.onShowAlert()
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
                confirmText = "OK",
                confirmAction = {
                    viewModel.onShowAlert()
                    viewModel.onButtonEnabled()
                    if (uistate.success){
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }
    }

}