package com.projetointegrador.pi_application.data.repository

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeocoderRepository(private val applicationContext: Context) {

    suspend fun getLatLongFromAddress(address: String) = withContext(Dispatchers.IO) {
        runCatching {
            val geocoder = Geocoder(applicationContext)
            val geocoderResult = geocoder.getFromLocationName(address, 1)
            LatLng(geocoderResult[0].latitude, geocoderResult[0].longitude)
        }
    }
}