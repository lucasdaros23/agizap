package com.example.agizap.modules.feature.login.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginTextField(onValueChange: (String) -> Unit, value: String, visualTransformation: VisualTransformation, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            errorIndicatorColor = Color(0xffF53F27),
            focusedContainerColor = Color(0x00000000),
            unfocusedContainerColor = Color(0x00000000),
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
        ),
        visualTransformation = visualTransformation,
        label = { Text(label) },
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginTextFieldPreview() {
    LoginTextField(
        onValueChange = {},
        value = "",
        visualTransformation = VisualTransformation.None,
        label = "Email"
    )
}