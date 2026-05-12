package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.domain.usecases.campaign.GetCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel
    @Inject
    constructor(
        private val getCampaignUseCase: GetCampaignUseCase
    ) : ViewModel() {
        private val _campaigns = MutableLiveData<TaskResponse<List<Campaign>>>()

        fun getAllCampaigns(): LiveData<TaskResponse<List<Campaign>>> {
            if (_campaigns.value == null) {
                val source = getCampaignUseCase.getAllCampaigns()
                source.observeForever(
                    object : Observer<TaskResponse<List<Campaign>>> {
                        override fun onChanged(value: TaskResponse<List<Campaign>>) {
                            _campaigns.value = value
                            source.removeObserver(this)
                        }
                    }
                )
            }
            return _campaigns
        }

        fun getCampaignByCategory(category: String) = getCampaignUseCase.getCampaignsByCategory(category)
    }
