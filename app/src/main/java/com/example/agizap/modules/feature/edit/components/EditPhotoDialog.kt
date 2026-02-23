package com.example.agizap.modules.feature.edit.components

import android.hardware.camera2.params.BlackLevelPattern
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.agizap.R
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.feature.edit.imagesList

@Composable
fun EditPhotoDialog(
    url: String,
    confirmAction: () -> Unit,
    cancelAction: () -> Unit,
    onPhotoClick: (String) -> Unit,
) {
    Alert(
        title = "Editar foto de perfil",
        confirmText = "Confirmar",
        confirmAction = { confirmAction() },
        cancelText = "Cancelar",
        cancelAction = { cancelAction() },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(imagesList) {
                    val color = MaterialTheme.colorScheme.primary

                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ){
                        ImageEditPhoto(
                            it,
                            onClick = { onPhotoClick(it) },
                            modifier = Modifier.align(Alignment.Center)
                        )

                        if (it == url) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .border(
                                        width = 2.dp,
                                        color = color,
                                        shape = CircleShape
                                    )
                            )
                        }
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
        modifier = modifier
            .size(70.dp)
            .clip(CircleShape)
            .clickable { onClick() },
    )
}

@Preview
@Composable
private fun BlackLevelPattern() {
    EditPhotoDialog(
        cancelAction = {},
        confirmAction = {},
        onPhotoClick = {},
        url = ""
        )
}