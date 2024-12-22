package com.example.ifataliku.home.souvenirs

import android.net.Uri
import com.example.ifataliku.core.di.UiText
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.TitleEmoji
import com.example.ifataliku.home.reflection.Category

sealed interface SouvenirState {
    data object Loading : SouvenirState
    data object Success : SouvenirState
    data class Error(val message: UiText) : SouvenirState
}

data class SouvenirStateData(
    val souvenirs: List<Pair<String, List<Souvenir>>>,
    val souvenir: Souvenir? = null
)

sealed interface SouvenirViewModelEvent {
    data object OpenAddSouvenir : SouvenirViewModelEvent
    data class ShowMessage(val message: UiText) : SouvenirViewModelEvent
}

sealed interface SouvenirUIEvent {
    data object InitPageData : SouvenirUIEvent
    data object OpenAddSouvenir : SouvenirUIEvent
    data class OnEmojiSelected(val emoji: String) : SouvenirUIEvent
    data class OnCategorySelected(val category: String) : SouvenirUIEvent
    data class OnColorSelected(val color: String) : SouvenirUIEvent
    data class OnFeelingSelected(val feeling: TitleEmoji) : SouvenirUIEvent
    data class OnImageSelected(val images: List<Uri>) : SouvenirUIEvent
    data class OnTitleChanged(val title: String) : SouvenirUIEvent
    data class OnDateChanged(val date: String) : SouvenirUIEvent
    data object OnValidateNewSouvenir : SouvenirUIEvent
}


data class ColorItem(
    val color: String,
    val isSelected: Boolean
)