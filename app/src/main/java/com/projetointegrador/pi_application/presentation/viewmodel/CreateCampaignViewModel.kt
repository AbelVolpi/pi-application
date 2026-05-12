package com.projetointegrador.pi_application.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.domain.models.Address
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.domain.usecases.campaign.CreateCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCampaignViewModel
    @Inject
    constructor(
        private val createCampaignUseCase: CreateCampaignUseCase,
        private val sessionManager: SessionManager
    ) : ViewModel() {
        fun createCampaign(
            name: String,
            description: String,
            category: String,
            street: String,
            number: String,
            district: String,
            city: String,
            state: String,
            imageUri: Uri?
        ): LiveData<TaskResponse<Boolean>> =
            liveData {
                val campaign =
                    Campaign(
                        userId = sessionManager.getUserId() ?: "",
                        campaignName = name,
                        campaignDescription = description,
                        campaignCategory = category,
                        campaignAddress = Address(street, number, district, city, state)
                    )
                try {
                    emitSource(createCampaignUseCase.createCampaign(campaign, imageUri))
                } catch (throwable: Throwable) {
                    Log.e("CreateCampaign", throwable.message.toString())
                    emit(TaskResponse.Failure(throwable.message.toString()))
                }
            }
    }
