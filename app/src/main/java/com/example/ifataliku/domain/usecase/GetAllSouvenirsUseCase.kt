package com.example.ifataliku.domain.usecase

import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.data.datasource.local.entities.Souvenir
import com.example.ifataliku.domain.repository.SouvenirRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAllSouvenirsUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
){
    suspend operator fun invoke() :Pair<List<Souvenir>, Map<String, List<Souvenir>>>{
        val souvenirs = souvenirRepo.getAllSouvenirs().first()
       return Utils.groupAndSortSouvenirs(souvenirs = souvenirs)
    }

}