package com.example.agizap.modules.feature.edit.components

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.agizap.modules.feature.register.components.ReturnButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopBar(onReturn: () -> Unit) {
    TopAppBar(
        title = { Text("Configurações", fontSize = 28.sp) },
        navigationIcon = { ReturnButton({ onReturn() }) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    )
}