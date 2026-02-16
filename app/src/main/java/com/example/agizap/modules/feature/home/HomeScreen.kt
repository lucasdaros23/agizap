package com.example.agizap.modules.feature.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agizap.R
import com.example.agizap.model.User
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.IconButtonComponent
import com.example.agizap.modules.components.TextFieldComponent
import com.example.agizap.modules.feature.home.components.AddChatButton
import com.example.agizap.modules.feature.home.components.AddChatDialog
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
    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopBarHome(
                onClick1 = { viewModel.onShowAlert() },
                onClick2 = { viewModel.onShowAlert() },
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(innerPadding)) {
                TextFieldComponent(
                    value = uiState.textField,
                    onValueChange = { viewModel.onTextFieldChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    placeholder = "Pesquisar",
                    leadingIcon = {
                        IconButtonComponent(
                            painter = painterResource(R.drawable.search),
                            onClick = { },
                            size = 25
                        )
                    },
                )
                Box() {
                    LazyColumn() {
                        items(viewModel.chatsSortedByDate()) {
                            viewModel.getCurrentUser()
                            if (uiState.currentUser.id in it.users){
                                ChatCard(
                                    chat = it,
                                    onclick = { navController.navigate(Routes.CHAT) },
                                    time = viewModel.formatTime(viewModel.convertTime(it.messages.last().time)),
                                    chatName = viewModel.getChatName(it),
                                    checkSent = viewModel.checkSent(
                                        User(),
                                        it.messages.last()
                                    ) // CHAMAR O GET CURRENT USER DO AUTH TBM
                                )
                            }
                        }
                    }
                }
            }

            AddChatButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(30.dp),
                onClick = {
                    viewModel.onShowAddChat()
                }
            )


            if (uiState.showAlert) {
                Alert(
                    title = "Essa função ainda não foi implementada",
                    "",
                    "OK",
                    "",
                    { viewModel.onShowAlert() },
                )
            }
            if (uiState.showAddChat) {
                AddChatDialog(uiState.users, onClick = { viewModel.onShowAddChat() }, onItemClick = {})
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        viewModel = viewModel(),
        navController = rememberNavController()
    )
}