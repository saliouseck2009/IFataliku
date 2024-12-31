package com.example.ifataliku.home.souvenir_detail

import IFatalikuTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ifataliku.R
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.core.di.asColor
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs
import com.example.ifataliku.home.reflection.EmojiCard
import com.example.ifataliku.home.souvenirs.SouvenirUIEvent
import com.example.ifataliku.widgets.CircularIconView
import com.example.ifataliku.widgets.RoundedCardText
import com.example.ifataliku.widgets.StaticMapView

@Composable
@Preview(showBackground = true)
fun PreviewSouvenirDetail(){
    IFatalikuTheme {
        SouvenirDetail(souvenir = souvenirs.first(), onClose = {}, onEvent = {}, onEdit = {})
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSouvenirDetailDark(){
    IFatalikuTheme(darkTheme = true) {
        SouvenirDetail(souvenir = souvenirs.first(), onClose = {}, onEvent = {}, onEdit = {})
    }
}

@Composable
fun SouvenirDetailSheet(
    souvenirs: List<Souvenir>,
    currentPage: Int,
    onClose: () -> Unit,
    onEdit: (Souvenir) -> Unit,
    onEvent: (SouvenirUIEvent) -> Unit,
){
    val pagerState = rememberPagerState(pageCount = {
        souvenirs.size
    })
    LaunchedEffect(key1 = currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        HorizontalPager(state = pagerState) {
            SouvenirDetail(souvenir = souvenirs[it], onClose = onClose, onEvent = onEvent, onEdit
            = onEdit)
            
        }
    }
}

@Composable
fun SouvenirDetail(souvenir: Souvenir,
                   onClose: () -> Unit, onEvent: (SouvenirUIEvent) -> Unit,
                   onEdit: (Souvenir) -> Unit){
    val surfaceColor = MaterialTheme.colorScheme.surface
    val randomRotateAngle = listOf(45f,90f,135f,180f).random()
    val morphProgress = listOf(0.0f,0.1f,0.2f,0.3f).random()
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(souvenir.color.color.asColor().copy(alpha = 0.2f),surfaceColor ,
               ),
                center = size.center,
                radius = biggerDimension / 1f,
                colorStops = listOf(0f, 0.40f,)
            )
        }
    }
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(largeRadialGradient)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CircularIconView(icon = Icons.Default.Close, onClose = onClose)
                Box {
                    CircularIconView(icon = Icons.Default.MoreHoriz, onClose = {
                        isDropDownExpanded.value = true
                    })
                    DropdownMenu(
                        expanded = isDropDownExpanded.value,
                        onDismissRequest = {
                            isDropDownExpanded.value = false
                        }) {
                            DropdownMenuItem(
                            text = {
                                if(souvenir.isFavorite )
                                    Text(text = stringResource(R.string.unfavorite))
                                else
                                    Text(text = stringResource(R.string.favorite))
                            },
                            trailingIcon = {
                                Icon(imageVector =if(souvenir.isFavorite) Icons.Filled.FavoriteBorder
                                        else Icons.Filled.Favorite,
                                    contentDescription = "favorite"
                                )
                            },
                            onClick = {
                                isDropDownExpanded.value = false
                                onEvent(SouvenirUIEvent.OnToggleFavourite(souvenir))
                            })
                            DropdownMenuItem(
                                text = {
                                Text(text = stringResource(R.string.edit))
                            },
                                trailingIcon = {
                                    Icon(imageVector = Icons.Default.EditNote,
                                        contentDescription =
                                        stringResource(R.string.edit))
                                },
                                onClick = {
                                    onEvent(SouvenirUIEvent.OnEditSouvenir(souvenir))
                                    isDropDownExpanded.value = false
                                })
                            DropdownMenuItem(
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.error,
                                    leadingIconColor = MaterialTheme.colorScheme.error,
                                    trailingIconColor = MaterialTheme.colorScheme.error,
                                ),
                                text = {
                                    Text(text = stringResource(R.string.delete),modifier = Modifier.padding(end = 16.dp))
                                },
                                trailingIcon = {
                                    Icon(imageVector = Icons.Default.Delete,
                                        contentDescription =
                                        stringResource(R.string.delete))
                                },
                                onClick = {
                                    onEvent(SouvenirUIEvent.OnDeleteSouvenir(souvenir))
                                    isDropDownExpanded.value = false
                                })

                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            EmojiCard(
                emoji = souvenir.emoji,
                shapeSize = 80,
                rotateAngle = randomRotateAngle,
                morphProgress = morphProgress,
                backgroundColor = souvenir.color.color.asColor(),
                modifier = Modifier
                    .offset((-16).dp, (-16).dp)
            )
            Text(
                text = Utils.getFormattedDate(souvenir.date, "EEEE, d MMMM yyyy") +
                        " ${stringResource(id = R.string.at)} ${souvenir.time}",
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    RoundedCardText(
                        category = souvenir.feeling,
                        color = souvenir.color.color.asColor(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    RoundedCardText(
                        category = souvenir.category,
                        color = souvenir.color.color.asColor(),
                    )
                }
                if(souvenir.isFavorite) Text(text = "❤")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = souvenir.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (souvenir.description.isNullOrBlank().not()) {
                Text(text = souvenir.description ?: "",
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .drawBehind {
                            // Draw a line Vertical line before the text
                            drawLine(
                                color = souvenir.color.color.asColor(),
                                start = Offset(x = 0f, y = 0f),
                                end = Offset(x = 0f, y = size.height),
                                strokeWidth = 3.dp.toPx()
                            )
                        }
                        .padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            StaticMapView(
                coordinates = souvenir.position,
                color = souvenir.color.color.asColor(),
                onClick = {},
                modifier = Modifier.padding()
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (souvenir.link.isNullOrBlank().not()) {
            UrlLauncherView(souvenir.link?:"", souvenir.color.color.asColor())
                }
        }
    }
}

@Composable
private fun UrlLauncherView(
    link: String,
    color: Color
) {
    val launcher = LocalUriHandler.current
    Surface(
        onClick ={
             launcher.openUri(link)
        },
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start

            ) {
                Icon(imageVector = Icons.Filled.Link, contentDescription = "link")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = link, color = color)
            }
            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "link")
        }
    }
}