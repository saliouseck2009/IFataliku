package com.example.ifataliku.home.stats

import IFatalikuTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ifataliku.NavigationDestination
import com.example.ifataliku.R

object StatPageDestination : NavigationDestination {
    override val route: String = "stats"
    override val titleRes: Int = R.string.my_souvenirs
}


@Composable
@Preview(showBackground = true)
fun StatPagePreview() {
    IFatalikuTheme {
        StatPage()
    }
}
@Composable
fun StatPage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Bienvenue dans la page de stat Stats")
    }
}