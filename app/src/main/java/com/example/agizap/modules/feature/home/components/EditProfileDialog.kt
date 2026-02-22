package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.Surface
import com.example.agizap.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    user: User,
    // as funcoes
) {
    AlertDialog(
        onDismissRequest = { },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                }

            }
        }

}