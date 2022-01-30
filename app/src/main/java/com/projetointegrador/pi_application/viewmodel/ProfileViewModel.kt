package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.CampaignRepository
import com.projetointegrador.pi_application.repository.UserRepository
import com.projetointegrador.pi_application.utils.SessionManager

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