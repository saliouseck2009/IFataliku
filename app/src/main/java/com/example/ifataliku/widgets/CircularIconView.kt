package com.example.ifataliku.widgets

import IFatalikuTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun CircularIconViewPreview() {
    IFatalikuTheme {
        CircularIconView(onClose = {})
    }
}

@Composable
 fun CircularIconView(
    onClose: () -> Unit,
    icon: ImageVector = Icons.Filled.Close,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .border(1.dp, color = Color.Transparent, shape = CircleShape)
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = CircleShape
            )

    ) {
        IconButton(onClick = onClose) {
            Icon(icon, contentDescription = "Add Souvenir")
        }
    }
}