package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.CampaignRepository

class CampaignsHistoricViewModel : ViewModel() {

    fun getCampaignsByUser(userId: String) = CampaignRepository().getCampaignsByUser(userId)

    fun deleteCampaign(campaignId: String) = CampaignRepository().deleteCampaign(campaignId)

}