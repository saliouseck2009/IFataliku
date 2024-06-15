package com.example.ifataliku.domain.usecase

import com.example.ifataliku.core.di.Utils
import com.example.ifataliku.domain.entities.souvenirs
import javax.inject.Inject

class GetAllSouvenirsUseCase @Inject constructor(){
        suspend operator fun invoke() = souvenirs
            .map {it
                it.copy(attachments = Utils.getEmojiFromSouvenir(it))
            }
                .groupBy { Utils.getDateFromString(it.date).month.name }
                .map { (month, souvenirs) ->
                       Pair(month, souvenirs)
                        }


}