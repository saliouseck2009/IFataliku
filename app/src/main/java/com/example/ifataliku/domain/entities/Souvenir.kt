package com.example.ifataliku.domain.entities

import android.net.Uri
import com.example.ifataliku.home.reflection.Category
import com.example.ifataliku.home.reflection.categories

data class Souvenir(
    val emoji: String,
    val title: String,
    val date: String,
    val time: String,
    val description: String? = "",
    val categories: List<Category>,
    val color: String,
    val feeling: TitleEmoji,
    val images: List<Uri>,
    val isFavorite: Boolean = false,
    val link: String? = "https://news.google.com/",
    val position: Coordinates? = Coordinates(14.7, -17.2343),
    val attachments: List<String> = emptyList()
)

data class Coordinates(val lat: Double, val lng: Double)


data class TitleEmoji(
    val emoji: String,
    val title: String,
)

var souvenirs = listOf(
    Souvenir(
        emoji = "🌅",
        title = "A mesmerizing sunrise at the beach. It was breathtaking!",
        date = "2024-03-15",
        time = "06:30 AM",
        categories = categories.shuffled().take(2),
        color = "4CAF50",
        feeling = TitleEmoji("😌", "Peaceful"),
        images = listOf(),
    ),
    Souvenir(
        emoji = "📚",
        title = "Finally finished that amazing book! It left me inspired and take a lot of time " +
                "to achieve it.",
        date = "2024-02-10",
        time = "10:00 PM",
        categories = categories.shuffled().take(2),
        color = "673AB7",
        feeling = TitleEmoji("📖", "Reflective"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "✈️",
        title = "My first solo trip abroad. The adventure was unforgettable!",
        date = "2024-05-05",
        time = "09:30 AM",
        categories = categories.shuffled().take(2),
        color = "009688",
        feeling = TitleEmoji("🌍", "Adventurous"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🍽️",
        title = "Tried a new recipe for dinner, and it turned out delicious!",
        date = "2024-04-18",
        time = "07:15 PM",
        categories = categories.shuffled().take(2),
        color = "FF5722",
        feeling = TitleEmoji("🤤", "Satisfied"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🏆",
        title = "Won first place in the local coding challenge. What a day!",
        date = "2024-06-25",
        time = "03:45 PM",
        categories = categories.shuffled().take(2),
        color = "FFC107",
        feeling = TitleEmoji("🥇", "Proud"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🌧️",
        title = "Rainy day vibes with tea and music. Felt calm and cozy.",
        date = "2024-07-12",
        time = "04:00 PM",
        categories = categories.shuffled().take(2),
        color = "03A9F4",
        feeling = TitleEmoji("☔", "Relaxed"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "👩‍💻",
        title = "Spent the whole day coding and finally fixed that bug!",
        date = "2024-03-07",
        time = "11:30 PM",
        categories = categories.shuffled().take(2),
        color = "9C27B0",
        feeling = TitleEmoji("💪", "Accomplished"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🎨",
        title = "Took some time to paint today. It felt so therapeutic!",
        date = "2024-08-01",
        time = "02:00 PM",
        categories = categories.shuffled().take(2),
        color = "8BC34A",
        feeling = TitleEmoji("🎨", "Creative"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🛠️",
        title = "Fixed the kitchen shelf all by myself. Feeling handy today!",
        date = "2024-09-14",
        time = "01:30 PM",
        categories = categories.shuffled().take(2),
        color = "FF9800",
        feeling = TitleEmoji("🔧", "Resourceful"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🏡",
        title = "Had a great family gathering at home. Full of laughter!",
        date = "2024-11-10",
        time = "06:00 PM",
        categories = categories.shuffled().take(2),
        color = "4CAF50",
        feeling = TitleEmoji("❤️", "Loved"),
        images = listOf(),
    ),

    Souvenir(
        emoji = "🌌",
        title = "Stargazing night with friends. The sky was magical.",
        date = "2024-12-20",
        time = "10:30 PM",
        categories = categories.shuffled().take(2),
        color = "3F51B5",
        feeling = TitleEmoji("🌠", "Amazed"),
        images = listOf(),
    )

)
