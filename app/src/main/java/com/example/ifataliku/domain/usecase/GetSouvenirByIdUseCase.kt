package com.example.ifataliku.domain.usecase

import com.example.ifataliku.data.datasource.local.entities.Souvenir
import com.example.ifataliku.domain.repository.SouvenirRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSouvenirByIdUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
) {
    operator fun invoke(id: String): Flow<Souvenir?> {
        return souvenirRepo.getSouvenirById(id)
    }
}