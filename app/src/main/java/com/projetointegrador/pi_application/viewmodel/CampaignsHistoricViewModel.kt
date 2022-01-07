package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.CampaignRepository

class CampaignsHistoricViewModel: ViewModel() {

    fun getCampaignsByUser(userId: String) = CampaignRepository().getCampaignsByUser(userId)

}