package com.example.agizap.modules.feature.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.R
import com.example.agizap.model.User
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.feature.edit.components.EditNameDialog
import com.example.agizap.modules.feature.edit.components.EditPhotoDialog
import com.example.agizap.modules.feature.edit.components.EditTopBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun EditScreen(
    viewModel: EditViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val user = uiState.currentUser
    val photo = uiState.newPhoto
    LaunchedEffect(Unit) {
        viewModel.updateUser()
    }
    Scaffold(
        topBar = {
            EditTopBar(onReturn = {
                viewModel.onBackButton { navController.popBackStack() }
            })
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ImageFromUrl(
                        user.photo,
                        modifier = Modifier
                            .size(200.dp)
                            .clickable {}
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.size(10.dp))
                    TextButton(
                        onClick = { viewModel.onEditPhotoAlert() }
                    ) {
                        Text("Editar", color = MaterialTheme.colorScheme.primary, fontSize = 15.sp)
                    }
                }
                Spacer(Modifier.size(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onEditNameAlert() }
                        .padding(vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Spacer(Modifier.size(30.dp))
                    Icon(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.size(20.dp))
                    Column() {
                        Text("Nome", fontSize = 20.sp, color = MaterialTheme.colorScheme.tertiary)
                        Text(
                            user.name,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
                Spacer(Modifier.size(10.dp))
                TextButton(
                    onClick = { viewModel.onShowDeleteAlert() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Desativar conta",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp
                    )
                }
                if (uiState.showDeleteAlert) {
                    Alert(
                        title = "Deseja mesmo desativar a sua conta?",
                        desc = "Essa ação não pode ser desfeita",
                        confirmText = "Desativar",
                        cancelText = "Cancelar",
                        confirmAction = {
                            viewModel.editUser("", "", false)
                            viewModel.onShowDeleteAlert()
                            viewModel.logout(navController)
                        },
                        cancelAction = { viewModel.onShowDeleteAlert() },
                    )
                }
                if (uiState.showEditName) {
                    EditNameDialog(
                        value = uiState.nameTextField,
                        onValueChange = { viewModel.onTextNameChange(it) },
                        confirmAction = {
                            viewModel.editUser(uiState.nameTextField, "", true)
                            viewModel.onEditNameAlert()
                        },
                        cancelAction = {
                            viewModel.onEditNameAlert()
                            viewModel.onTextNameChange(user.name)
                        }
                    )
                }
                if (uiState.showEditPhoto) {
                    EditPhotoDialog(
                        confirmAction = {
                            viewModel.editUser("", uiState.newPhoto, true)
                            viewModel.onEditPhotoAlert()
                        },
                        cancelAction = {
                            viewModel.onEditPhotoAlert()
                            viewModel.onNewPhoto(user.photo)
                        },
                        onPhotoClick = { viewModel.onNewPhoto(it) },
                        url = uiState.newPhoto
                    )
                }
            }
        }
    }
}
