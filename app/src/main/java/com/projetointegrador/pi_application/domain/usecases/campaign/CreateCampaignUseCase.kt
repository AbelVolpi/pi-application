package com.projetointegrador.pi_application.domain.usecases.campaign

import android.net.Uri
import androidx.lifecycle.LiveData
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.GeocoderRepository
import com.projetointegrador.pi_application.domain.models.Campaign
import javax.inject.Inject

class CreateCampaignUseCase
    @Inject
    constructor(
        private val campaignRepository: CampaignRepository,
        private val geocoderRepository: GeocoderRepository,
    ) {
        suspend fun createCampaign(
            campaign: Campaign,
            imageUri: Uri?,
        ): LiveData<TaskResponse<Boolean>> {
            val address = "${campaign.campaignAddress?.street},${campaign.campaignAddress?.number}-${campaign.campaignAddress?.district},${campaign.campaignAddress?.city}-${campaign.campaignAddress?.state}"
            campaign.campaignLatLng = geocoderRepository.getLatLongFromAddress(address).getOrThrow()

            return campaignRepository.createCampaign(campaign, imageUri)
        }
    }
