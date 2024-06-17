package com.example.ifataliku.home.reflection

import IFatalikuTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ifataliku.NavigationDestination
import com.example.ifataliku.R
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.widgets.CategoryView
import com.example.ifataliku.widgets.SmallCategoryGridView

object ReflectionDestination : NavigationDestination {
    override val route: String = "reflection"
    override val titleRes: Int = R.string.home_page
}



@Composable
fun ReflectionPage(
    onGoToSouvenir: () -> Unit = {}
) {
    val dayOfToday = Utils.getCurrentDate()
    Scaffold() {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                item(span = { GridItemSpan(2) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            dayOfToday,
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            "My Reflection Page on life",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight(600)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }

                items(categories.take(5)) {
                    CategoryView(
                        it.emoji, it.title, modifier = Modifier
                            .padding(4.dp)
                            .height(150.dp)
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            "Categories",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight(600)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        val pagerState = rememberPagerState(pageCount = {
                            categories.size / 6
                        })
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                        ){
                                SmallCategoryGridView(categories = categories.chunked(6)[pagerState.currentPage],
                                    modifier = Modifier
                                , )
                        }
                        Spacer(modifier = Modifier.height(16.dp))



                    }
                }

            }

        }
    }
}

@Composable
@Preview
fun ReflectionPagePreview() {
    IFatalikuTheme {
        ReflectionPage()
    }
}

@Composable
@Preview(
//    showBackground = true,
//    showSystemUi = true,
    )
fun ReflectionPagePreviewDark() {
    IFatalikuTheme(darkTheme = true) {
        ReflectionPage()
    }
}