package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.CampaignRepository

class MapsViewModel : ViewModel() {

    private val campaignRepository = CampaignRepository()

    fun getAllCampaigns() = campaignRepository.getAllCampaigns()

    fun getCampaignByCategory(category: String) = campaignRepository.getCampaignsByCategory(category)

}
