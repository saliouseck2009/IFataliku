package com.example.ifataliku.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomDropDownMenu(
    items: List<String>,
    title: String,
    expanded: Boolean,
    currentValue: String,
    onValueChanged: (value: String) -> Unit,
    onExpanded: (value: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
       // border = BorderStroke(2.dp, color = Color(0xFFCFCFCF)),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(1.dp),
        onClick = {
            onExpanded(!expanded)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Text(text = title, style = TextStyle(fontWeight = FontWeight.Bold))
            if (!expanded) Row(

            ) {
                Text(text = currentValue)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    Icons.Default.UnfoldMore,
                    contentDescription = "Expand More",
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    onExpanded(false)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items.forEach {
                    DropdownMenuItem(text = {
                        Text(text = it, style = TextStyle(fontWeight = FontWeight.Bold))
                    },
                        onClick = {
                            onValueChanged(it)
                            onExpanded(false)
                        })
                }
            }
        }
    }
}