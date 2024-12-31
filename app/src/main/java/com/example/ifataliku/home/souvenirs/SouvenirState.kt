package com.example.ifataliku.home.souvenirs

import android.net.Uri
import com.example.ifataliku.core.di.UiText
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.home.reflection.Category

sealed interface SouvenirState {
    data object Loading : SouvenirState
    data object Success : SouvenirState
    data class Error(val message: UiText) : SouvenirState
}

data class SouvenirStateData(
    val souvenirsMap:  Map<String, List<Souvenir>>,
    val souvenirs: List<Souvenir>,
    val souvenir: Souvenir? = null
)

sealed interface SouvenirViewModelEvent {
    data object OpenAddSouvenir : SouvenirViewModelEvent
    data class ShowMessage(val message: UiText) : SouvenirViewModelEvent
}

sealed interface AddSouvenirViewModelEvent {
    data class RetrieveImageLocation(val uri: Uri) : AddSouvenirViewModelEvent
}

sealed interface SouvenirUIEvent {
    data object InitPageData : SouvenirUIEvent
    data object OpenAddSouvenir : SouvenirUIEvent
    data class OnEmojiSelected(val emoji: String) : SouvenirUIEvent
    data class OnCategorySelected(val category: com.example.ifataliku.home.reflection.Category) : SouvenirUIEvent
    data class OnColorSelected(val color: LabelledColor) : SouvenirUIEvent
    data class OnFeelingSelected(val feeling: Category) : SouvenirUIEvent
    data class OnImageSelected(val images: List<Uri>) : SouvenirUIEvent
    data class OnTitleChanged(val title: String) : SouvenirUIEvent
    data class OnDescriptionChanged(val description: String) : SouvenirUIEvent
    data class OnDateChanged(val date: String) : SouvenirUIEvent
    data object OnValidateNewSouvenir : SouvenirUIEvent
    data class OnLocationSelected(val lat: Double?, val lng: Double?) : SouvenirUIEvent
    data object OnFetchCurrentLocation : SouvenirUIEvent
    data class OnLinkChanged(val link: String) : SouvenirUIEvent
    data class OnToggleFavourite(val souvenir: Souvenir) : SouvenirUIEvent
    data class OnDeleteSouvenir(val souvenir: Souvenir) : SouvenirUIEvent
    data class OnEditSouvenir(val souvenir: Souvenir) : SouvenirUIEvent

}

class AppData {
    companion object{
        val colorItems =  listOf(
            LabelledColor( label= "BlueBerry", color = "f72585"),
            LabelledColor( label= "Slate", color = "757B87"),
            LabelledColor( label= "Arctic", color = "82EDFD"),
            LabelledColor( label= "Moss", color = "466D1E"),
            LabelledColor( label= "Chartreuse", color = "B0FC38"),
            LabelledColor( label= "Mantis", color = "00FF00"),
            LabelledColor( label= "Iris", color = "640aff"),
            LabelledColor( label= "Mauve", color = "E29FF6"),
            LabelledColor( label= "Lilac", color = "B660CD"),
            LabelledColor( label= "Persian Pink", color = "F988D8"),
            LabelledColor( label= "Mandy", color = "FF1695"),
            LabelledColor( label= "Strawberry", color = "FC4C4E"),
            LabelledColor( label= "Crepe", color = "F2B8C6"),
            LabelledColor( label= "Yellow Sea", color = "F98228"),
            LabelledColor( label= "Butter", color = "FEE227"),
            LabelledColor( label= "Salt", color = "F7EFEC"),
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
        val emojis  =  listOf(
            Category("😩", "Terrible"),
            Category("☹️️", "Bad"),
            Category("😐", "Okay"),
            Category("🙂", "Good"),
            Category("😄", "Awesome"),
        )
    }
}




data class ColorItem(
    val color: String,
    val isSelected: Boolean
)