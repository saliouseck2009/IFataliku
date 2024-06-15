package com.example.ifataliku.widgets

import IFatalikuTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs

@Composable
fun SouvenirItemView(
    souvenir: Souvenir,
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        //Surface {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                Text(souvenir.emoji,
                    modifier = Modifier.padding(4.dp),
                    style = TextStyle(fontSize = 30.sp),
                    )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(souvenir.title,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Row {
                            Text("📅${Utils.getFormattedDate(souvenir.date)}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                                )
                        }
                        Row {
                            Text(
                                souvenir.attachments.joinToString(separator = " ") { it },
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                )
                        }

                    }
                    HorizontalDivider(
                        thickness = 0.2.dp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
       // }
    }
}

@Composable
@Preview(showBackground = true)
fun SouvenirItemViewPreview() {
    SouvenirItemView(souvenir = souvenirs[0])
}

@Composable
fun SouvenirListItemView(
    dateTitle: String,
    items: List<Souvenir>,
    modifier : Modifier = Modifier) {
    Column(modifier = modifier){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(dateTitle,
                modifier = Modifier.padding(4.dp),
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                )
            Text("${items.size} souvenir${if(items.size==1) ' ' else 's'}",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
                )
        }
        items.forEach {
            SouvenirItemView(it)
           // HorizontalDivider(thickness = 0.1.dp)
        }


    }
}

@Composable
@Preview(showBackground = true)
fun SouvenirListItemViewPreview() {
    IFatalikuTheme() {
        SouvenirListItemView( "February", souvenirs)
    }
}

@Composable
@Preview(showBackground = true)
fun SouvenirListItemViewDarkPreview() {
    IFatalikuTheme(darkTheme = true) {
        SouvenirListItemView("February", souvenirs)
    }
}
