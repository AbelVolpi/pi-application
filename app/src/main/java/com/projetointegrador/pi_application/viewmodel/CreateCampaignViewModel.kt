package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.repository.CampaignRepository


class CreateCampaignViewModel : ViewModel() {

    fun createCampaign(campaign: Campaign) = CampaignRepository().createCampaign(campaign)




}

