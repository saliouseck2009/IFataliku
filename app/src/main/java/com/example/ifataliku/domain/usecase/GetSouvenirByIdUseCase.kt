package com.example.ifataliku.domain.usecase

import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.repository.SouvenirRepo
import javax.inject.Inject

class GetSouvenirByIdUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
) {
    suspend operator fun invoke(id: String): Souvenir? {
        return souvenirRepo.getSouvenirById(id)
    }
}