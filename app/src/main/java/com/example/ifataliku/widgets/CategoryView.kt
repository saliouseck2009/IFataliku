package com.example.ifataliku.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ifataliku.home.reflection.Category

@Composable
fun CategoryView(emoji: String, title: String, modifier : Modifier= Modifier) {
    Card(
        modifier = modifier,
    ){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(emoji,
                  style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp),
                 modifier = Modifier.padding( 16.dp))
            Text(title,
                 style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(16.dp) )       }
    }
}


@Composable
@Preview
fun CategoryViewPreview() {
    CategoryView("❤️", "My favorites")
}

@Composable
fun SmallCategoryView(emoji: String, title: String, modifier : Modifier= Modifier) {
    Card(
        modifier = modifier,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(emoji,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding( vertical = 4.dp, horizontal = 2.dp))
            Text(title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical =4.dp, horizontal = 2.dp) )       }
    }
}

// displa small category in  a grid of 2 columns and 3 rows
@Composable
fun SmallCategoryGridView(
    categories: List<Category> = listOf(
        Category("❤️", "My favorites"),
        Category("📚", "Books"),
        Category("🎨", "Art"),
        Category("🎵", "Music"),
        Category("🎮", "Games"),
        Category("🎬", "Movies"),
    ),
    modifier: Modifier= Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = modifier

        ) {
            categories.chunked(2).forEach { rowCategories ->
                Row(
                    modifier = Modifier

                ) {
                    rowCategories.forEach { category ->
                        SmallCategoryView(
                            emoji = category.emoji,
                            title = category.title,
                            modifier = Modifier
                                .height(60.dp)
                                .weight(1f)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SmallCategoryGridViewPreview() {
    SmallCategoryGridView()
}

@Composable
@Preview(showBackground = true)
fun CategorySmallViewPreview() {
    SmallCategoryView("❤️", "My favorites")
}