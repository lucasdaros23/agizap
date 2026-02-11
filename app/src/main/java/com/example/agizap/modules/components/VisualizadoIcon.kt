package com.example.agizap.modules.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.agizap.R

@Composable
fun VisualizadoIcon(modifier: Modifier = Modifier) {
    Icon(
        painterResource(R.drawable.seen),
        "",
        modifier = modifier,
        tint = MaterialTheme.colorScheme.tertiary
    )
}