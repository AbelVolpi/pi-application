package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.domain.usecases.campaign.DeleteCampaignUseCase
import com.projetointegrador.pi_application.domain.usecases.campaign.GetCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignsHistoricViewModel
    @Inject
    constructor(
        private val getCampaignUseCase: GetCampaignUseCase,
        private val deleteCampaignUseCase: DeleteCampaignUseCase,
        private val sessionManager: SessionManager
    ) : ViewModel() {
        fun getCampaignsForCurrentUser() = getCampaignUseCase.getCampaignsByUser(sessionManager.getUserId() ?: "")

        fun deleteCampaign(campaignId: String) = deleteCampaignUseCase.deleteCampaign(campaignId)
    }
