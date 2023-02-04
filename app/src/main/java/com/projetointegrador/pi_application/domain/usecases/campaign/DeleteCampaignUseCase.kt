package com.projetointegrador.pi_application.domain.usecases.campaign

import com.projetointegrador.pi_application.data.repository.CampaignRepository
import javax.inject.Inject

class DeleteCampaignUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {

    fun deleteCampaign(campaignId: String) = campaignRepository.deleteCampaign(campaignId)

    fun deleteAllCampaigns(userId: String) = campaignRepository.deleteAllCampaigns(userId)
}
