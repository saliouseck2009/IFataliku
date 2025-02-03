package com.example.ifataliku.home.souvenir_detail

import IFatalikuTheme
import android.content.ContentResolver
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.ifataliku.R
import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.core.di.asColor
import com.example.ifataliku.data.datasource.local.entities.Souvenir
import com.example.ifataliku.data.datasource.local.entities.souvenirs
import com.example.ifataliku.home.reflection.EmojiCard
import com.example.ifataliku.home.souvenirs.SouvenirUIEvent
import com.example.ifataliku.widgets.CircularIconView
import com.example.ifataliku.widgets.RoundedCardText
import com.example.ifataliku.widgets.StaticMapView

@Composable
@Preview(showBackground = true)
fun PreviewSouvenirDetail(){
    IFatalikuTheme {
        SouvenirDetail(souvenir = souvenirs.first(), onClose = {}, onEvent = {}, )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSouvenirDetailDark(){
    IFatalikuTheme(darkTheme = true) {
        SouvenirDetail(souvenir = souvenirs.first(), onClose = {}, onEvent = {}, )
    }
}

@Composable
fun SouvenirDetailSheet(
    souvenirs: List<Souvenir>,
    currentPage: Int,
    onClose: () -> Unit,
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
            SouvenirDetail(souvenir = souvenirs[it], onClose = onClose, onEvent = onEvent)
            
        }
    }
}

@Composable
fun SouvenirDetail(souvenir: Souvenir,
                   onClose: () -> Unit, onEvent: (SouvenirUIEvent) -> Unit,
                   ){
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
    val context = LocalContext.current
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(souvenir.images) {
        val uriToDelete = mutableListOf<String>()
        souvenir.images.forEach { uri ->
            if (isUriAvailable(Uri.parse(uri), context.contentResolver).not()) {
                uriToDelete.add(uri)
            }
        }
        if(uriToDelete.isNotEmpty()) onEvent(SouvenirUIEvent.OnDeletedImage(souvenir, uriToDelete))
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
            if(souvenir.images.isNotEmpty()){
                ImagePreviewSection(
                    imageUris = souvenir.images.map { Uri.parse(it) },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@Composable
fun ImagePreviewSection(
    modifier: Modifier = Modifier,
    imageUris: List<Uri>,
) {
    val context = LocalContext.current
    Column {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(16.dp)
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            imageUris.forEach { uri ->
                Card{
                    if (isUriAvailable(uri, context.contentResolver)) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "",
                            modifier = Modifier.size(150.dp, 150.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }else{

                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier.size(150.dp, 150.dp)){
                            Icon(imageVector = Icons.Default.HideImage, contentDescription =
                            "image", modifier = Modifier.fillMaxSize().padding(32.dp))
                        }
                    }
                }
            }
        }
    }
}

fun isUriAvailable(uri: Uri, contentResolver: ContentResolver): Boolean {
    return try {
        contentResolver.openInputStream(uri)?.close()
        true
    } catch (e: Exception) {
        false
    }
}


@Composable
private fun UrlLauncherView(
    link: String,
    color: Color
) {
    val launcher = LocalUriHandler.current
    Surface(
        onClick = {
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