package com.example.ifataliku.home.souvenirs

import android.util.Patterns
import android.webkit.URLUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifataliku.R
import com.example.ifataliku.core.di.UiText
import com.example.ifataliku.domain.entities.Coordinates
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.repository.LocationTrackerRepo
import com.example.ifataliku.domain.usecase.GetAllSouvenirsUseCase
import com.example.ifataliku.domain.usecase.SouvenirUseCase
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
    private val souvenirUseCase: SouvenirUseCase,
    private val locationTrackerRepo: LocationTrackerRepo,
) : ViewModel(){
    private val  _state : MutableStateFlow<SouvenirState> = MutableStateFlow(SouvenirState.Loading)
    val state = _state.asStateFlow()
    private val _souvenirStateData : MutableStateFlow<SouvenirStateData> =
        MutableStateFlow(SouvenirStateData(souvenirs = emptyList(), souvenirsMap =  emptyMap(),
            souvenir = AppData.initialSouvenir))
    val souvenirStateData = _souvenirStateData.asStateFlow()
    private val _viewModelEvent = Channel<SouvenirViewModelEvent>()
    val viewModelEvent = _viewModelEvent.receiveAsFlow()
    private val _addSouvenirViewModelEvent = Channel<AddSouvenirViewModelEvent>()
    val addSouvenirViewModelEvent = _addSouvenirViewModelEvent.receiveAsFlow()
    init {
        initPageData()
    }


    private fun initPageData() {
        viewModelScope.launch {
            delay(500)
            _state.value = SouvenirState.Loading
            getAllSouvenirsUseCase().let {
                _souvenirStateData.value = SouvenirStateData(souvenirs = it.first, souvenirsMap =
                it.second, souvenir = AppData.initialSouvenir)
                _state.value = SouvenirState.Success
            }

        }
    }

    fun dispatchUiEvent(event: SouvenirUIEvent){
        when(event){
            is SouvenirUIEvent.InitPageData -> initPageData()
            is SouvenirUIEvent.OpenAddSouvenir -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(souvenir = AppData
                        .initialSouvenir)
                    _viewModelEvent.send(SouvenirViewModelEvent.OpenAddSouvenir)
                }
            }
            is SouvenirUIEvent.OnEmojiSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(emoji = event.emoji)
                    )
                }
            }
            is SouvenirUIEvent.OnCategorySelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(category =
                                event.category,
                        )
                    )
                }
            }
            is SouvenirUIEvent.OnColorSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(color = event.color)
                    )
                }
            }
            is SouvenirUIEvent.OnFeelingSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(feeling = event.feeling)
                    )
                }
            }
            is SouvenirUIEvent.OnImageSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(images = event.images)
                    )
                    if (event.images.isNotEmpty()) {
                        _addSouvenirViewModelEvent.send(
                            AddSouvenirViewModelEvent.RetrieveImageLocation(
                                uri = event.images.first()
                            )
                        )
                    }
                }
            }
            is SouvenirUIEvent.OnTitleChanged -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(title = event.title)
                    )
                }
            }
            is SouvenirUIEvent.OnDateChanged -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(date = event.date)
                    )
                }
            }
            is SouvenirUIEvent.OnValidateNewSouvenir -> {
                viewModelScope.launch {
                    _souvenirStateData.value.souvenir.let {
                        if(it.link!=null && isValidUrl(it.link).not()){
                                _viewModelEvent.send(SouvenirViewModelEvent.ShowMessage(
                                    UiText.StringResource(R.string.le_lien_n_est_pas_valide)
                                ))
                            return@launch
                        }
                        if(it.title.isEmpty() ){
                            _viewModelEvent.send(SouvenirViewModelEvent.ShowMessage(
                                UiText.StringResource(R.string.veillez_remplir_le_titre)
                            ))
                            return@launch
                        }
                        if (it.id.isBlank()) {
                            souvenirUseCase.createSouvenir(it)
                        }else{
                            souvenirUseCase.updateSouvenir(it)
                        }
                        _viewModelEvent.send(SouvenirViewModelEvent.CloseAddSouvenir)
                        _souvenirStateData.value = _souvenirStateData.value.copy(souvenir =
                        AppData.initialSouvenir)
                        initPageData()
                    }
                }
            }

            is SouvenirUIEvent.OnDescriptionChanged -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(description = event.description)
                    )
                }
            }
            is SouvenirUIEvent.OnLocationSelected -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(
                            position = if (event.lat != null && event.lng != null) {
                                Coordinates(event.lat, event.lng)
                            } else {
                                null
                            }
                        )
                    )
                }
            }
            is SouvenirUIEvent.OnFetchCurrentLocation -> {
                viewModelScope.launch {
                    locationTrackerRepo.getCurrentLocation().let {
                        if (it != null){
                            _souvenirStateData.value = _souvenirStateData.value.copy(
                                souvenir = _souvenirStateData.value.souvenir.copy(
                                    position = Coordinates(it.latitude, it.longitude)
                                )
                            )}
                        else{
                            _viewModelEvent.send(SouvenirViewModelEvent.ShowMessage(
                                UiText.StringResource(R.string.location_not_found)
                            ))
                        }
                    }
                }
            }
            is SouvenirUIEvent.OnLinkChanged -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(
                        souvenir = _souvenirStateData.value.souvenir.copy(link = event.link)
                    )
                }
            }

            is SouvenirUIEvent.OnDeleteSouvenir -> {
                viewModelScope.launch {
                    souvenirUseCase.deleteSouvenir(event.souvenir.id)
                    _viewModelEvent.send(SouvenirViewModelEvent.CloseSouvenirDetail)
                    initPageData()
                }
            }
            is SouvenirUIEvent.OnEditSouvenir -> {
                viewModelScope.launch {
                    _souvenirStateData.value = _souvenirStateData.value.copy(souvenir = event.souvenir)
                    _viewModelEvent.send(SouvenirViewModelEvent.OpenAddSouvenir)
                }
            }
            is SouvenirUIEvent.OnToggleFavourite -> {
                viewModelScope.launch {
                    souvenirUseCase.updateSouvenir(event.souvenir.copy(isFavorite = !event.souvenir
                        .isFavorite))
                    initPageData()
                }
            }
        }
    }
    private fun isValidUrl(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }
}