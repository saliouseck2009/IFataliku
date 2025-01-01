package com.example.ifataliku.domain.usecase

import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.repository.SouvenirRepo
import javax.inject.Inject

class CreateSouvenirUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
) {
    suspend operator fun invoke(souvenir: Souvenir): Boolean {
        return souvenirRepo.createSouvenir(souvenir)
    }
}