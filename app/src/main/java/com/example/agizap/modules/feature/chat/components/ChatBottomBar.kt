package com.example.agizap.modules.feature.chat.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ChatBottomBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.ime)
            .imePadding(),
        containerColor = Color.Transparent,
    ) {
        Row() {

        }
    }
}