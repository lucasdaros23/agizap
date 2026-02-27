package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.model.User
import com.example.agizap.modules.components.ImageComponent
import com.example.agizap.modules.components.ImageFromUrl

@Composable
fun UserCardSearch(user: User, onClick: ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable(
                true,
                onClick = { onClick() },
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageFromUrl(user.photo, modifier = Modifier
            .size(40.dp)
            .clip(CircleShape))
        Spacer(modifier = Modifier.size(10.dp))
        Text(user.name, modifier = Modifier.weight(1f), fontSize = 20.sp)
    }
}

@Preview
@Composable
private fun UserCardSearchPreview() {
    UserCardSearch(User(), {})
}