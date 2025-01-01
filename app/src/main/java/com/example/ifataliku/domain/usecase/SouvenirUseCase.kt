package com.example.ifataliku.domain.usecase

import javax.inject.Inject

data class SouvenirUseCase @Inject constructor(
    val createSouvenir: CreateSouvenirUseCase,
    val deleteAllSouvenirs: DeleteAllSouvenirsUseCase,
    val deleteSouvenir: DeleteSouvenirByIdUseCase,
    val getAllSouvenirs: GetAllSouvenirsUseCase,
    val getSouvenirById: GetSouvenirByIdUseCase,
    val updateSouvenir: UpdateSouvenirUseCase
)
