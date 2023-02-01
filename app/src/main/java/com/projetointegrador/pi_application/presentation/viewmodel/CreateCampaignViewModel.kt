package com.projetointegrador.pi_application.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projetointegrador.pi_application.core.base.BaseViewModel
import com.projetointegrador.pi_application.domain.campaign.CreateCampaignUseCase
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.FirebaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*

@HiltViewModel
class CreateCampaignViewModel @Inject constructor(
    private val createCampaignUseCase: CreateCampaignUseCase
) : BaseViewModel() {

    fun createCampaign(campaign: Campaign, imageUri: Uri?): LiveData<FirebaseResponse<Boolean>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<Boolean>>()

        launch {
            try {
                mutableLiveData.value = createCampaignUseCase.createCampaign(campaign, imageUri).value
                // todo adjust this part
            } catch (throwable: Throwable) {
                Log.e("Create campaign", throwable.message.toString())
                mutableLiveData.value = FirebaseResponse.Failure(throwable.message.toString())
            }
        }
        return mutableLiveData
    }
}
