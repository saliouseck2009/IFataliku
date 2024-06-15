package com.example.ifataliku.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorItemWidget(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(15),
        border = BorderStroke(5.dp, color) ,
        colors = CardDefaults.cardColors(
            containerColor = if(isSelected) color else Color.Transparent,
        ),
        modifier = modifier
            .size(40.dp)
            .clickable(onClick = onClick)
    ) {

    }
}

@Composable
@Preview(showBackground = true)
fun ColorItemWidgetPreview() {
    ColorItemWidget(
        color = Color.Yellow,
        isSelected = true,
        onClick = {}
    )
}