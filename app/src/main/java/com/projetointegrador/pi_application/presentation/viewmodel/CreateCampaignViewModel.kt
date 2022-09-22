package com.projetointegrador.pi_application.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.GeocoderRepository
import com.projetointegrador.pi_application.core.utils.GeocoderResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CreateCampaignViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    fun createCampaign(campaign: Campaign, imageUri: Uri?) = CampaignRepository().createCampaign(campaign, imageUri)

    fun getLatLongFromAddress(address: String): MutableLiveData<GeocoderResponse<LatLng>> {
        val mutableLiveData = MutableLiveData<GeocoderResponse<LatLng>>()

        launch {
            try {
                val latLng = GeocoderRepository().getLatLongFromAddress(address)
                mutableLiveData.value = GeocoderResponse.Success(latLng)
            } catch (throwable: Throwable) {
                Log.e("Create campaign", throwable.message.toString())
                mutableLiveData.value = GeocoderResponse.Failure(throwable.message.toString())
            }
        }

        return mutableLiveData
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

}

