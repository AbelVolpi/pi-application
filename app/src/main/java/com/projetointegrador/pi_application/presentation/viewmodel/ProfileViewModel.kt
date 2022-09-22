package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.UserRepository
import com.projetointegrador.pi_application.core.utils.SessionManager

class ProfileViewModel : ViewModel() {

    fun removeAccount(userId: String) {
        CampaignRepository().deleteAllCampaigns(userId)
        UserRepository().deleteUser()
        SessionManager.logout()
    }

    fun logOut() {
        SessionManager.logout()
        UserRepository().logOut()
    }

}