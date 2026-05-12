package com.projetointegrador.pi_application.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.domain.usecases.campaign.CreateCampaignUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCampaignViewModel
    @Inject
    constructor(
        private val createCampaignUseCase: CreateCampaignUseCase
    ) : androidx.lifecycle.ViewModel() {
        fun createCampaign(
            campaign: Campaign,
            imageUri: Uri?
        ): LiveData<TaskResponse<Boolean>> =
            liveData {
                try {
                    emitSource(createCampaignUseCase.createCampaign(campaign, imageUri))
                } catch (throwable: Throwable) {
                    Log.e("CreateCampaign", throwable.message.toString())
                    emit(TaskResponse.Failure(throwable.message.toString()))
                }
            }
    }
