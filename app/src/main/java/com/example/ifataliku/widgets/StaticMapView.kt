package com.example.ifataliku.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Map
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.example.ifataliku.R
import com.example.ifataliku.domain.entities.Coordinates


@Composable
fun StaticMapView(
    coordinates: Coordinates?,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier,

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
        ) {
            if (coordinates == null) {
                IconTitledPicker(
                    icon = Icons.Default.Map,
                    color = color,
                    title = stringResource(R.string.add_location),
                    onClick = onClick
                )
            }else{
                SubcomposeAsyncImage(
                    model = getMapUrl(coordinates),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = "map image",
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

private fun getMapUrl(coordinates: Coordinates): String {
    val (lat, lng) = coordinates
    val imageUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/geojson" +
            "(%7B%22type%22%3A%22Point%22%2C%22coordinates%22%3A%5B$lng%2C$lat%5D%7D)/$lng,$lat" +
            ",15/500x200@2x?access_token=pk" +
            ".eyJ1Ijoic2FsaW91c2VjazIwMDkiLCJhIjoiY20ybGszdDY5MDlvNjJpc2cyeWphM2lqayJ9.eSwRWNn01NUFAlFIueC1Wg"
    return imageUrl
}

@Preview(showBackground = true)
@Composable
fun StaticMapViewPreview() {
    StaticMapView(
        color = Color.Red,
        coordinates = null, onClick = {}
    )
}