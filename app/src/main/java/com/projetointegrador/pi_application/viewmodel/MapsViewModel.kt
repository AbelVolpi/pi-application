package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.CampaignRepository

class MapsViewModel : ViewModel() {

    private val campaignRepository = CampaignRepository()

    fun getAllCampaigns() = campaignRepository.getAllCampaigns()

    fun getCampaignByCategory(category: String) = campaignRepository.getCampaignsByCategory(category)

}
