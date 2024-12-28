package com.example.ifataliku.widgets

import IFatalikuTheme
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.ifataliku.R
import com.example.ifataliku.core.di.safeSublist

@Composable
fun ImagePickerView(
    modifier: Modifier = Modifier,
    pickPhotos: () -> Unit,
    images: List<Uri>,
    color: Color
) {
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {
            ImageDisplayView(
                modifier = Modifier.weight(1f),
                images = images.safeSublist(0, 3),
                imageConfigs = listOf(
                    ImageConfig(10, 10, 15),
                    ImageConfig(10, 70, -15),
                    ImageConfig(70, 45, 10)
                )
                )
            Card(
                colors = CardDefaults.cardColors(
                    contentColor = color,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant                ),
                onClick = pickPhotos
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    IconButton(
                        onClick = pickPhotos) {
                        Icon(imageVector = Icons.Filled.AddAPhoto,
                            contentDescription = "add photos")
                    }
                    Text(text = stringResource(R.string.add_photos), style = MaterialTheme.typography.titleSmall)
                }
            }
            ImageDisplayView(modifier = Modifier.weight(1f),
                    images = images.safeSublist(3, 6),
                imageConfigs = listOf(
                    ImageConfig(10, 10, 15),
                    ImageConfig(30, 70, -15),
                    ImageConfig(70, 25, 10)
                )
            )
        }
    }
}

@Composable
fun ImageDisplayView(modifier: Modifier = Modifier, images: List<Uri> = emptyList(),
                     imageConfigs: List<ImageConfig> ) {
    Card(modifier = modifier
        .size(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            imageConfigs.forEachIndexed { index, imageConfig ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .size(40.dp, 40.dp)
                        .offset(imageConfig.offsetX.dp, imageConfig.offsetY.dp)
                        .rotate(imageConfig.rotate.toFloat())
                ) {
                    if(images.getOrNull(index) != null) {
                        SubcomposeAsyncImage(
                            model = images[index],
                            loading = {
                                CircularProgressIndicator()
                            },
                            contentDescription = "image",
                            modifier = Modifier.size(40.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}

data class ImageConfig(
    val offsetX: Int,
    val offsetY: Int,
    val rotate: Int)

@Composable
@Preview(showBackground = true)
fun ImagePickerViewPreview() {
    IFatalikuTheme {
        ImagePickerView(
            pickPhotos = {},
            color = Color.Green,
            images = emptyList()
        )
    }
}
@Composable
@Preview(showBackground = true, )
fun ImageDisplayViewPreview() {
    IFatalikuTheme(darkTheme = true) {
        ImagePickerView(
            pickPhotos = {},
            color = Color.Green,
            images = emptyList()
        )
    }
}