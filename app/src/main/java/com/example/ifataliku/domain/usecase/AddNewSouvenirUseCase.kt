package com.example.ifataliku.domain.usecase

import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs
import javax.inject.Inject

class AddNewSouvenirUseCase @Inject constructor() {
    suspend operator fun invoke(souvenir: Souvenir) {
        souvenirs = souvenirs + souvenir
    }
}