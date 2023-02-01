package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.campaign.DeleteCampaignUseCase
import com.projetointegrador.pi_application.domain.campaign.GetCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignsHistoricViewModel @Inject constructor(
    private val getCampaignUseCase: GetCampaignUseCase,
    private val deleteCampaignUseCase: DeleteCampaignUseCase
) : ViewModel() {

    fun getCampaignsByUser(userId: String) = getCampaignUseCase.getCampaignsByUser(userId)

    fun deleteCampaign(campaignId: String) = deleteCampaignUseCase.deleteCampaign(campaignId)
}
