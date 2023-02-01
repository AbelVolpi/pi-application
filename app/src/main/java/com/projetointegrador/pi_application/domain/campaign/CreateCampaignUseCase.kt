package com.projetointegrador.pi_application.domain.campaign

import android.net.Uri
import androidx.lifecycle.LiveData
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.GeocoderRepository
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.models.MyLatLng
import com.projetointegrador.pi_application.utils.FirebaseResponse
import javax.inject.Inject

class CreateCampaignUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository,
    private val geocoderRepository: GeocoderRepository
) {
    suspend fun createCampaign(campaign: Campaign, imageUri: Uri?): LiveData<FirebaseResponse<Boolean>> {
        val address = "${campaign.campaignAddress?.street},${campaign.campaignAddress?.number}-${campaign.campaignAddress?.district},${campaign.campaignAddress?.city}-${campaign.campaignAddress?.state}"
        val latLng = geocoderRepository.getLatLongFromAddress(address).getOrThrow()
        campaign.campaignLatLng = MyLatLng(latLng.latitude.toString(), latLng.longitude.toString())

        return campaignRepository.createCampaign(campaign, imageUri)
    }
}
