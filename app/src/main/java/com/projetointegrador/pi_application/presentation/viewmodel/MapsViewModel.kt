package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.campaign.GetCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getCampaignUseCase: GetCampaignUseCase
) : ViewModel() {

    fun getAllCampaigns() = getCampaignUseCase.getAllCampaigns()

    fun getCampaignByCategory(category: String) = getCampaignUseCase.getCampaignsByCategory(category)
}
