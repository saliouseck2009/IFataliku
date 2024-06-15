package com.example.ifataliku.home.reflection

data class Category(
    val emoji : String,
    val title : String,
)

val categories = listOf(
    Category("❤️", "Books"),
    Category("😊", "My good times"),
    Category("🌍", "My happy places"),
    Category("☹️", "My roughest times"),
    Category("📸", "My immortalized moments"),
    Category("💸", "Finance"),
    Category("🤷🏾‍", "Fluff"),
    Category("🕸️", "Online Stuff"),
    Category("🏠", "Parenting"),
    Category("😇", "Well-being"),
    Category("💻", "Work"),
    Category("🌐", "Community"),
    Category("📖", "Education"),
    Category("🎮", "Entertainment"),
    Category("🍔", "Food"),
    Category("🏋️", "Fitness"),
    Category("🎵", "Music"),
    Category("🎨", "Art"),
    Category("🚗", "Travel"),
    Category("👔", "Fashion"),
    Category("🌳", "Nature"),
)