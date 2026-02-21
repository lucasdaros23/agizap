package com.example.agizap.modules.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.agizap.R

@Composable
fun AgiZapIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(R.drawable.icon),
        contentDescription = "",
        modifier = modifier
    )
}