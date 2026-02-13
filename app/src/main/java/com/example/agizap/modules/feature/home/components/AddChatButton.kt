package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddChatButton(onClick: () -> Unit, modifier: Modifier) {
    Button(
        shape = CircleShape,
        onClick = { onClick() },
        modifier = modifier
            .size(80.dp)
        ,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ){
        Text("+", textAlign = TextAlign.Center, fontSize = 50.sp)
    }
}

@Preview
@Composable
private fun AddChatButtonPreview() {
    AddChatButton(modifier = Modifier, onClick = {})
}