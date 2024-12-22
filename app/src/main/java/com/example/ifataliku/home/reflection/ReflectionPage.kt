package com.example.ifataliku.home.reflection

import IFatalikuTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.ifataliku.NavigationDestination
import com.example.ifataliku.R
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.widgets.CategoryView
import com.example.ifataliku.widgets.SmallCategoryGridView


object ReflectionDestination : NavigationDestination {
    override val route: String = "reflection"
    override val titleRes: Int = R.string.home_page
}

class FunShape(
    private val morphProgress: Float,
    private val triangleCornerRadius: Float = 150f,
    private val squareCornerRadius: Float = 40f
): Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

            val triangle = RoundedPolygon(
                numVertices = 3,
                radius = size.minDimension / 2f,
                centerX = size.width / 2f,
                centerY = size.height / 2f,
                rounding = CornerRounding(
                    triangleCornerRadius,
                    smoothing = 0.2f
                )
            )
            val square = RoundedPolygon(
                numVertices = 4,
                radius = size.minDimension / 2f,
                centerX = size.width / 2f,
                centerY = size.height / 2f,
                rounding = CornerRounding(
                    squareCornerRadius,
                    smoothing = 0.1f
                )
            )

            val morph = Morph(start = triangle, end = square)
            val morphPath = morph
                .toPath(progress = morphProgress).asComposePath()
            return Outline.Generic(morphPath)

    }

}



@Composable
fun ReflectionPage(
) {
    val dayOfToday = Utils.getCurrentDate()
    Scaffold {
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
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "My Reflection on life",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight(600)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding()
                        )
                    }

                }

                items(categories.take(5)) {category ->
                    CategoryView(
                        category.emoji,
                        category.title,
                        modifier = Modifier
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
fun TimeLineComposable(modifier:Modifier=Modifier){
    Text(text = "",
        modifier = modifier
            .height(100.dp)
            .width(80.dp)
            .background(Color.Transparent)
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(size.width / 2, size.height/2),
                    end = Offset(size.width / 2, size.height*1.18f),
                    strokeWidth = 0.8f
                )

            }

    )
}

@Composable
fun EmojiCard(
    modifier: Modifier = Modifier,
    emoji: String = "😄",
    rotateAngle: Float = 45f,
    shapeSize: Int = 100,
    morphProgress: Float = 0.1f,
    triangleCornerRadius : Float = 50f,
    backgroundColor: Color = Color.Cyan,
) {
    val funShape = remember {
        FunShape(
            morphProgress = morphProgress,
            triangleCornerRadius = triangleCornerRadius,
        )
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(80.dp)
            .clip(
                funShape
            )

    ) {
        Box(
            modifier = Modifier
                .rotate(rotateAngle)
                .size(shapeSize.dp)
                .background(backgroundColor)
        )
        Text(text = emoji, fontSize = 22.sp)
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
    showBackground = true,
    showSystemUi = true,
    )
fun ReflectionPagePreviewDark() {
    IFatalikuTheme(darkTheme = true) {
        ReflectionPage()
    }
}