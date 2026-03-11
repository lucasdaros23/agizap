package com.example.agizap.modules.feature.chatinfo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.model.User

@Composable
fun GroupUser(
    user: User,
    isCurrentUser: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable{ onClick() }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            ) {
            ImageFromUrl(
                url = user.photo,
                modifier = Modifier
                    .size(size = 60.dp)
                    .clip(shape = CircleShape)
            )
            Spacer(Modifier.size(16.dp))
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleLarge
            )
        }
        if (isCurrentUser){
            Text(
                text = "Você",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupUserPreview() {
    GroupUser(User(name = "lucas"), isCurrentUser = true, {})
}