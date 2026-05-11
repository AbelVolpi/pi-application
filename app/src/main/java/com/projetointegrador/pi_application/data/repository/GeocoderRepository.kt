package com.projetointegrador.pi_application.data.repository

import android.content.Context
import android.location.Geocoder
import com.projetointegrador.pi_application.domain.models.MyLatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeocoderRepository(private val applicationContext: Context) {
    suspend fun getLatLongFromAddress(address: String) =
        withContext(Dispatchers.IO) {
            runCatching {
                val geocoder = Geocoder(applicationContext)
                val result = geocoder.getFromLocationName(address, 1)
                MyLatLng(
                    result?.get(0)?.latitude?.toString() ?: "0.0",
                    result?.get(0)?.longitude?.toString() ?: "0.0"
                )
            }
        }
}
