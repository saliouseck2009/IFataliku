package com.example.ifataliku.domain.usecase

import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.repository.SouvenirRepo
import javax.inject.Inject

class GetAllSouvenirsUseCase @Inject constructor(
    private val souvenirRepo: SouvenirRepo
){
    suspend operator fun invoke() :Pair<List<Souvenir>, Map<String, List<Souvenir>>>{
        val souvenirs = souvenirRepo.getAllSouvenirs()
       return Utils.groupAndSortSouvenirs(souvenirs = souvenirs)
    }

}