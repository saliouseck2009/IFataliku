package com.example.ifataliku.data.datasource.local.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.ifataliku.data.datasource.LabelledColor
import com.example.ifataliku.home.reflection.Category
import com.example.ifataliku.home.souvenirs.AppData
import com.google.gson.Gson
import java.util.UUID

@Entity(tableName = "souvenirs",indices = [Index(value = ["id"], unique = true)])
data class Souvenir(
    @PrimaryKey
    val id: String,
    val emoji: String,
    val title: String,
    val date: String,
    val time: String?,
    val description: String? = "",
    val category: Category,
    val color: LabelledColor,
    val feeling: Category,
    val images: List<String>,
    val isFavorite: Boolean = false,
    val link: String? =  "https://news.google.com/",
    val position: Coordinates? = null,
)

data class Coordinates(val lat: Double, val lng: Double)
class CoordinateConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCoordinates(coordinate: Coordinates?): String {
        return gson.toJson(coordinate)
    }

    @TypeConverter
    fun toCoordinates(value: String): Coordinates? {
        return try {
            gson.fromJson(value, Coordinates::class.java)
        } catch (e: Exception) {
            null
        }
    }
}




var souvenirs = listOf(
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🌅",
        title = "A mesmerizing sunrise at the beach. It was breathtaking!",
        date = "2024-03-15",
        time = "06:30 AM",
        category = AppData.categories.first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("😌", "Peaceful"),
        images = listOf(),
        description ="A stunning memory of a mesmerizing sunrise at the beach, where the golden hues of the sun painted the sky and reflected on the calm waves. The serene atmosphere and the beauty of nature made it a truly breathtaking moment to cherish forever."
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "📚",
        title = "Finally finished that amazing book! It left me inspired and take a lot of time " +
                "to achieve it.",
        date = "2024-02-10",
        time = "10:00 PM",
        isFavorite = true,
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("📖", "Reflective"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "✈️",
        title = "My first solo trip abroad. The adventure was unforgettable!",
        date = "2024-05-05",
        time = "09:30 AM",
        isFavorite = true,
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("🌍", "Adventurous"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🍽️",
        title = "Tried a new recipe for dinner, and it turned out delicious!",
        date = "2024-04-18",
        time = "07:15 PM",
        isFavorite = true,
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("🤤", "Satisfied"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🏆",
        title = "Won first place in the local coding challenge. What a day!",
        date = "2024-06-25",
        time = "03:45 PM",
        isFavorite = true,
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("🥇", "Proud"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🌧️",
        title = "Rainy day vibes with tea and music. Felt calm and cozy.",
        date = "2024-07-12",
        time = "04:00 PM",
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("☔", "Relaxed"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "👩‍💻",
        title = "Spent the whole day coding and finally fixed that bug!",
        date = "2024-03-07",
        time = "11:30 PM",
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("💪", "Accomplished"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🎨",
        title = "Took some time to paint today. It felt so therapeutic!",
        date = "2024-08-01",
        time = "02:00 PM",
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("🎨", "Creative"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🛠️",
        title = "Fixed the kitchen shelf all by myself. Feeling handy today!",
        date = "2024-09-14",
        time = "01:30 PM",
        isFavorite = true,
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("🔧", "Resourceful"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🏡",
        title = "Had a great family gathering at home. Full of laughter!",
        date = "2024-11-10",
        time = "06:00 PM",
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("❤️", "Loved"),
        images = listOf(),
    ),

    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🌌",
        title = "Stargazing night with friends. The sky was magical.",
        date = "2024-12-20",
        time = "10:30 PM",
        category = AppData.categories.shuffled().first(),
        color =  LabelledColor( label= "BlueBerry", color = "f72585"),
        feeling = Category("🌠", "Amazed"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🍕",
        title = "Pizza night with friends. Lots of laughs and good vibes.",
        date = "2024-08-15",
        time = "08:00 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Cheese Yellow", color = "FFD700"),
        feeling = Category("🍕", "Content"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🌋",
        title = "Witnessed a volcanic eruption from a safe distance. What a thrill!",
        date = "2024-02-17",
        time = "05:00 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Lava Red", color = "FF4500"),
        feeling = Category("🔥", "Thrilled"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🍂",
        title = "A walk in the woods during autumn. The leaves were stunning.",
        date = "2023-10-25",
        time = "04:30 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Autumn Gold", color = "DAA520"),
        feeling = Category("🍁", "Serene"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "📚",
        title = "Spent an afternoon reading a captivating book at the park.",
        date = "2024-04-18",
        time = "03:00 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Bookish Beige", color = "F5DEB3"),
        feeling = Category("📖", "Inspired"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "✈️",
        title = "First international trip! Exploring new cultures and cuisines.",
        date = "2024-05-05",
        time = "11:30 AM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Sky Blue", color = "87CEEB"),
        feeling = Category("🌍", "Excited"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🎂",
        title = "A cozy birthday celebration with family and loved ones.",
        date = "2024-03-12",
        time = "07:00 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Birthday Pink", color = "FFC0CB"),
        feeling = Category("🎉", "Loved"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🎶",
        title = "Attended a live concert of my favorite band. What a night!",
        date = "2024-07-21",
        time = "08:00 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Vibrant Purple", color = "800080"),
        feeling = Category("🎸", "Euphoric"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🏔️",
        title = "An adventurous hike to the mountain top. The view was incredible!",
        date = "2023-09-10",
        isFavorite = true,
        time = "09:00 AM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Mountain Green", color = "228B22"),
        feeling = Category("🥾", "Accomplished"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🌧️",
        title = "Dancing in the rain with friends. It felt so refreshing!",
        date = "2023-06-02",
        time = "04:15 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Rainy Gray", color = "6C757D"),
        feeling = Category("💃", "Free"),
        images = listOf(),
    ),
    Souvenir(
        id = UUID.randomUUID().toString(),
        emoji = "🌅",
        title = "A peaceful evening by the lake, watching the sunset.",
        date = "2023-10-15",
        time = "06:45 PM",
        category = AppData.categories.shuffled().first(),
        color = LabelledColor(label = "Sunset Orange", color = "FF5733"),
        feeling = Category("😌", "Calm"),
        images = listOf(),
    )




)
