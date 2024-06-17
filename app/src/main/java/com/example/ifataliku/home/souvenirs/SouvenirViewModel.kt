package com.example.ifataliku.home.souvenirs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifataliku.R
import com.example.ifataliku.core.di.UiText
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.TitleEmoji
import com.example.ifataliku.domain.usecase.AddNewSouvenirUseCase
import com.example.ifataliku.domain.usecase.GetAllSouvenirsUseCase
import com.example.ifataliku.home.reflection.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SouvenirViewModel @Inject constructor(
    private val getAllSouvenirsUseCase: GetAllSouvenirsUseCase,
    private val addNewSouvenirUseCase: AddNewSouvenirUseCase
) : ViewModel(){
    private val  _state : MutableStateFlow<SouvenirState> = MutableStateFlow(SouvenirState.Loading)
    val state = _state.asStateFlow()
    private val _souvenirStateData : MutableStateFlow<SouvenirStateData> =
        MutableStateFlow(SouvenirStateData(emptyList()))
    val souvenirStateData = _souvenirStateData.asStateFlow()
    private val _viewModelEvent = Channel<SouvenirViewModelEvent>()
    val viewModelEvent = _viewModelEvent.receiveAsFlow()
//    private val _uiEvent = Channel<SouvenirUIEvent>()
//    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        initPageData()
    }
    private val initialSouvenir = Souvenir(
        emoji = "🎉",
        title = "",
        date = LocalDate.now().toString(),
        time = "",
        category = Category("📖", "Education"),
        color = "640D6B",
        feeling = TitleEmoji("🙂", "Good"),
        images = emptyList()
    )

    private fun initPageData() {
        viewModelScope.launch {
            delay(1000)
            _state.value = SouvenirState.Loading
            getAllSouvenirsUseCase().let {
                _souvenirStateData.value = SouvenirStateData(it)
                _state.value = SouvenirState.Success
            }

        }
    }

    fun dispatchUiEvent(event: SouvenirUIEvent){
        when(event){
            is SouvenirUIEvent.InitPageData -> initPageData()
            is SouvenirUIEvent.OpenAddSouvenir -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(souvenir = initialSouvenir)
                    _viewModelEvent.send(SouvenirViewModelEvent.OpenAddSouvenir)
                }
            }
            is SouvenirUIEvent.OnEmojiSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(emoji = event.emoji)
                    )
                }
            }
            is SouvenirUIEvent.OnCategorySelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(category =  Category(
                            emoji = event.category.split(" ")[0],
                            title = event.category.split(" ")[1]
                        )
                        )
                    )
                }
            }
            is SouvenirUIEvent.OnColorSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(color = event.color)
                    )
                }
            }
            is SouvenirUIEvent.OnFeelingSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(feeling = event.feeling)
                    )
                }
            }
            is SouvenirUIEvent.OnImageSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(images = event.images)
                    )
                }
            }
            is SouvenirUIEvent.OnTitleChanged -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(title = event.title)
                    )
                }
            }
            is SouvenirUIEvent.OnDateChanged -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir?.copy(date = event.date)
                    )
                }
            }
            is SouvenirUIEvent.OnValidateNewSouvenir -> {
                viewModelScope.launch {
                    _souvenirStateData.value.souvenir?.let {
                        if(
                            it.title.isNotEmpty() && it.date.isNotEmpty()){
                            //add souvenir to list
                            addNewSouvenirUseCase.invoke(it)
                            _souvenirStateData.value = _souvenirStateData.value.copy(souvenir = null)

                        }else{
                            viewModelScope.launch {
                                _viewModelEvent.send(SouvenirViewModelEvent.ShowMessage(
                                    UiText.StringResource(R.string.veillez_remplir_tous_les_champs)
                                ))
                            }
                        }
                    }
                }
            }
        }
    }
}