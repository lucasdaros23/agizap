package com.example.agizap.modules.feature.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agizap.R
import com.example.agizap.model.User
import com.example.agizap.modules.component.Alert
import com.example.agizap.modules.component.IconButtonComponent
import com.example.agizap.modules.component.TextFieldComponent
import com.example.agizap.modules.feature.home.components.TopBarHome
import com.example.agizap.modules.feature.home.components.ChatCard
import com.example.agizap.modules.navigation.Routes

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopBarHome(
                onClick1 = { viewModel.onShowAlertHome() },
                onClick2 = { viewModel.onShowAlertHome() },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextFieldComponent(
                value = uiState.textField,
                onValueChange = { viewModel.onTextFieldChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                placeholder = "Pergunte à Meta AI ou pesquise",
                leadingIcon = {
                    IconButtonComponent(
                        painter = painterResource(R.drawable.search),
                        onClick = { },
                        size = 25
                    )
                },
                trailingIcon = { },
            )
            LazyColumn() {
                items(viewModel.chatsSortedByDate()) {
                    ChatCard(
                        chat = it,
                        onclick = { navController.navigate(Routes.CHAT) },
                        time = viewModel.formatTime(viewModel.convertTime(it.messages.last().time)),
                        chatName = viewModel.getChatName(it),
                        checkSent = viewModel.checkSent(User(), it.messages.last()) // CHAMAR O GET CURRENT USER DO AUTH TBM
                        )
                }
            }
        }

        if (uiState.showAlert){
            Alert(
                title = "Essa função ainda não foi implementada",
                "",
                "OK",
                "",
                { viewModel.onShowAlertHome() },
                {}
            )
        }


    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
}