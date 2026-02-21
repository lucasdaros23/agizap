package com.example.agizap.modules.feature.register.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.agizap.R
import com.example.agizap.modules.components.IconButtonComponent

@Composable
fun ReturnButton(onClick: () -> Unit) {
    IconButtonComponent(
        painter = painterResource(R.drawable.voltar),
        onClick = {
            onClick()
        },
        size = 30
    )

}