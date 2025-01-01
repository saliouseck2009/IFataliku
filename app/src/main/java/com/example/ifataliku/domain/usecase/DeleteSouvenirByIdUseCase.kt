package com.example.ifataliku.domain.usecase

import com.example.ifataliku.domain.repository.SouvenirRepo
import javax.inject.Inject

class DeleteSouvenirByIdUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
) {
    suspend operator fun invoke(id: String): Boolean {
        return souvenirRepo.deleteSouvenirById(id)
    }
}