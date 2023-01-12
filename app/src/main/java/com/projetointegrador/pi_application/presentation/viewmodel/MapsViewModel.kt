package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val campaignRepository: CampaignRepository
) : ViewModel() {

    fun getAllCampaigns() = campaignRepository.getAllCampaigns()

    fun getCampaignByCategory(category: String) = campaignRepository.getCampaignsByCategory(category)

}
