package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignsHistoricViewModel @Inject constructor(
    private val campaignRepository: CampaignRepository
) : ViewModel() {

    fun getCampaignsByUser(userId: String) = campaignRepository.getCampaignsByUser(userId)

    fun deleteCampaign(campaignId: String) = campaignRepository.deleteCampaign(campaignId)

}