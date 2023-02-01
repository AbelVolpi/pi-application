package com.projetointegrador.pi_application.domain.campaign

import com.projetointegrador.pi_application.data.repository.CampaignRepository
import javax.inject.Inject

class GetCampaignUseCase @Inject constructor(
    private val campaignRepository: CampaignRepository
) {

    fun getCampaignsByUser(userId: String) = campaignRepository.getCampaignsByUser(userId)

    fun getCampaignsByCategory(category: String) = campaignRepository.getCampaignsByCategory(category)

    fun getAllCampaigns() = campaignRepository.getAllCampaigns()
}
