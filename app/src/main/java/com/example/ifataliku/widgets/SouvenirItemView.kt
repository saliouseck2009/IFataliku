package com.example.ifataliku.widgets

import IFatalikuTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.core.di.Utils.asColor
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs
import com.example.ifataliku.home.reflection.Category
import com.example.ifataliku.home.reflection.EmojiCard
import com.example.ifataliku.home.reflection.TimeLineComposable
import com.example.ifataliku.home.reflection.categories

@Composable
fun SouvenirItemView(
    souvenir: Souvenir,
    modifier : Modifier = Modifier
) {
    val randomRotateAngle = listOf(45f,90f,135f,180f).random()
    val morphProgress = listOf(0.0f,0.1f,0.2f,0.3f).random()
    Column(
        modifier = modifier
            .height(100.dp)
            .padding(4.dp)
    ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
        ) {
                EmojiWithTimeLineView(souvenir, randomRotateAngle, morphProgress)
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column {
                        Text(
                            Utils.getFormattedDate(souvenir.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            souvenir.title,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row {
                            categories.take(2).forEach {
                                RoundedCardText(category = it, color = souvenir.color.asColor())
                                Spacer(modifier = Modifier.padding(end = 4.dp))
                            }
                        }
                        AdditionalInfoPreview(souvenir)

                    }
                }
            }
    }
}

@Composable
private fun EmojiWithTimeLineView(
    souvenir: Souvenir,
    randomRotateAngle: Float,
    morphProgress: Float
) {
    Box(
        modifier = Modifier
            .offset((-10).dp)
    ) {
        TimeLineComposable(
            modifier = Modifier.offset(
            )
        )
        EmojiCard(
            emoji = souvenir.emoji,
            shapeSize = 80,
            rotateAngle = randomRotateAngle,
            morphProgress = morphProgress,
            backgroundColor = souvenir.color.asColor(),
            modifier = Modifier
                .offset(4.dp, (-16).dp)
        )
    }
}

@Composable
private fun AdditionalInfoPreview(souvenir: Souvenir) {
    val infoList = listOfNotNull(
        souvenir.isFavorite.takeIf { it }?.let { "❤️" },
        souvenir.link?.let { "🔗" },
        souvenir.description?.let { "📝" },
        souvenir.position?.let { "📍" }
    )

    Row(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        infoList.forEach { icon ->
            Text(
                text = icon,
                style = MaterialTheme.typography.bodyMedium
            )
        }
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
        }


    }
}

@Composable
fun RoundedCardText(
    category: Category,
    color: Color
){
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.4f)
    ) {
        Text(text = "${category.emoji} ${category.title}",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 11.sp),
            modifier = Modifier.padding(horizontal = 6.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun RoundedCardTextPreview(){
    IFatalikuTheme {
        RoundedCardText(
            category = Category("😩", "Terrible"),
            color = Color.Cyan.copy(alpha = 0.2f)
        )
    }
}
@Composable
@Preview(showBackground = true)
fun SouvenirListItemViewPreview() {
    IFatalikuTheme {
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
