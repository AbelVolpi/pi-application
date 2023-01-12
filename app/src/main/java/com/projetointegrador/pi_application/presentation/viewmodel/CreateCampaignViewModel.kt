package com.projetointegrador.pi_application.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.projetointegrador.pi_application.core.base.BaseViewModel
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.GeocoderRepository
import com.projetointegrador.pi_application.utils.GeocoderResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CreateCampaignViewModel @Inject constructor(
    private val campaignRepository: CampaignRepository,
    private val geocoderRepository: GeocoderRepository
) : BaseViewModel() {

    fun createCampaign(campaign: Campaign, imageUri: Uri?) = campaignRepository.createCampaign(campaign, imageUri)

    fun getLatLongFromAddress(address: String): MutableLiveData<GeocoderResponse<LatLng>> {
        val mutableLiveData = MutableLiveData<GeocoderResponse<LatLng>>()

        launch {
            try {
                val latLng = geocoderRepository.getLatLongFromAddress(address).getOrThrow()
                mutableLiveData.value = GeocoderResponse.Success(latLng)
            } catch (throwable: Throwable) {
                Log.e("Create campaign", throwable.message.toString())
                mutableLiveData.value = GeocoderResponse.Failure(throwable.message.toString())
            }
        }

        return mutableLiveData
    }
}

