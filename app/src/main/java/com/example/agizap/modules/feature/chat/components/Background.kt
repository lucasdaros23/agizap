package com.example.agizap.modules.feature.chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.agizap.R

@Composable
fun Background(){
    Image(
        painter = painterResource(if (isSystemInDarkTheme()) R.drawable.dark_background else R.drawable.light_background),
        contentDescription = "background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
        colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(Color(0xFF505050)) else null
    )
}