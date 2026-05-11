package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.usecases.campaign.GetCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewCampaignViewModel
    @Inject
    constructor(
        private val getCampaignUseCase: GetCampaignUseCase,
    ) : ViewModel() {
        fun getCampaignById(campaignId: String) = getCampaignUseCase.getCampaignById(campaignId)
    }
