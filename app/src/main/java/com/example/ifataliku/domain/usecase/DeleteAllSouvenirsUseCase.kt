package com.example.ifataliku.domain.usecase

import com.example.ifataliku.domain.repository.SouvenirRepo
import javax.inject.Inject

class DeleteAllSouvenirsUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
) {
    suspend operator fun invoke(): Boolean {
        return souvenirRepo.deleteAllSouvenirs()
    }
}