package com.example.ifataliku.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.ifataliku.domain.repository.LocationTrackerRepo
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationTrackerRepoImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Context
) : LocationTrackerRepo {
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result) { cause, _, _ -> } // Resume coroutine with location result
                    } else {
                        cont.resume(null) { cause, _, _ -> } // Resume coroutine with null location result
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) { cause, _, _ -> }  // Resume coroutine with location result
                }
                addOnFailureListener {
                    cont.resume(null) { cause, _, _ -> } // Resume coroutine with null location result
                }
                addOnCanceledListener {
                    cont.cancel() // Cancel the coroutine
                }
            }
        }
    }

}
