package com.example.ifataliku.domain.entities

import android.net.Uri
import com.example.ifataliku.home.reflection.Category
import com.example.ifataliku.home.reflection.categories
import java.time.LocalDate

data class Souvenir(
    val emoji: String,
    val title: String,
    val date: String,
    val time: String,
    val category: Category,
    val color: String,
    val feeling: TitleEmoji,
    val images: List<Uri>,
    val attachments: List<String> = emptyList()
)


data class TitleEmoji(
    val emoji: String,
    val title: String,
)

var souvenirs = listOf(
    Souvenir(
        emoji = "🎉",
        title = "My Birthday",
        date = "2024-01-01",
        time = "12:00 PM",
        category = categories.random(),
        color = "FFC107",
        feeling = TitleEmoji("😊", "Happy"),

       listOf(),
    ),
    Souvenir(
        emoji = "🎓",
        title = "Graduation Day",
        date = "2024-02-01",
        time = "12:00 PM",
        category = categories.random(),
        color = "#FF5722",
        feeling = TitleEmoji("🎉", "Excited"),
        images = listOf()

    ),
    Souvenir(
        emoji = "🎉",
        title = "My Birthday",
        date = "2024-02-20",
        time = "12:00 PM",
        category = categories.random(),
        color = "#FFC107",
        feeling = TitleEmoji("😊", "Happy"),
        images = listOf(
        )
    ),
    Souvenir(
        emoji = "🎓",
        title = "Graduation Day",
        date = "2024-03-01",
        time = "12:00 PM",
        category = categories.random(),
        color = "#FF5722",
        feeling = TitleEmoji("🎉", "Excited"),
        images = listOf(

        )
    ),

)
