package com.example.agizap.modules.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.grpc.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Alert(
    title: String = "",
    desc: String = "",
    confirmText: String = "",
    cancelText: String = "",
    confirmAction: () -> Unit = {},
    cancelAction: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = {  },
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge
                )
                if (desc != "") {
                    Spacer(Modifier.size(10.dp))
                    Text(
                        desc,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                content()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (cancelText != "") AlertTextButton(
                        text = cancelText,
                        onClick = cancelAction
                    )
                    if (confirmText != "") AlertTextButton(
                        text = confirmText,
                        onClick = confirmAction
                    )

                }


            }
        }
    }


}

@Preview
@Composable
private fun AlertPreview() {
    Alert(
        "titulo",
        "desc",
        "oi",
        "oi",
        {},
        {}
        )
}