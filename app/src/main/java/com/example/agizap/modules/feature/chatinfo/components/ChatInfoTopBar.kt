package com.example.agizap.modules.feature.chatinfo.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.agizap.modules.feature.register.components.ReturnButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInfoTopBar(onReturn: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = { ReturnButton({ onReturn() }) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    )
}