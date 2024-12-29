package com.example.ifataliku.domain.repository

import android.location.Location

interface LocationTrackerRepo {
    suspend fun getCurrentLocation(): Location?
}