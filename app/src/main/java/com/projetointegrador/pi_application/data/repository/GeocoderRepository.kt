package com.projetointegrador.pi_application.data.repository

import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.projetointegrador.pi_application.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeocoderRepository {

    suspend fun getLatLongFromAddress(address: String) = withContext(Dispatchers.IO) {
        val geocoderResult = Geocoder(MainApplication.applicationContext()).getFromLocationName(
            address,
            1
        )
        LatLng(geocoderResult[0].latitude, geocoderResult[0].longitude)
    }
}