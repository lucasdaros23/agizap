package com.example.agizap.modules.feature.edit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.feature.edit.imagesList

@Composable
fun EditPhotoDialog(
    confirmAction: () -> Unit,
    cancelAction: () -> Unit,
    onPhotoClick: (String) -> Unit,
    checkSelected: (String) -> Boolean
) {
    Alert(
        title = "Editar foto de perfil",
        confirmText = "Confirmar",
        confirmAction = { confirmAction() },
        cancelText = "Cancelar",
        cancelAction = { cancelAction() },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(imagesList.size/3),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(imagesList) {
                    val color = MaterialTheme.colorScheme.primary

                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .drawBehind {
                                if (checkSelected(it)) {
                                    drawRoundRect(
                                        color = color,
                                        cornerRadius = CornerRadius(35f, 35f)
                                    )
                                }
                            }
                    ){
                        ImageEditPhoto(
                            it,
                            onClick = { onPhotoClick(it) },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ImageEditPhoto(url: String, onClick: () -> Unit, modifier: Modifier) {
    ImageFromUrl(
        url,
        modifier = modifier.size(70.dp).clip(CircleShape).clickable{ onClick() },
    )
}