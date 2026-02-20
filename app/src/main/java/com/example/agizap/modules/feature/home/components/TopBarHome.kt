package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.agizap.R
import com.example.agizap.modules.components.IconButtonComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(onClick1: () -> Unit, onClickLogout: () -> Unit) {
    Column() {
        TopAppBar(
            title = { Text(text = "Agizap", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold) },
            actions = {
                IconButtonComponent(
                    painter = painterResource(R.drawable.camera),
                    onClick = { onClick1() },
                    size = 30
                )
                DropDownOptions(
                    onClickLogout = onClickLogout
                )
            }
        )

    }
}