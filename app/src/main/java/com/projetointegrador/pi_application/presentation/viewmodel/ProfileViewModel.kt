package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.CampaignRepository
import com.projetointegrador.pi_application.data.repository.UserRepository
import com.projetointegrador.pi_application.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val campaignRepository: CampaignRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    fun removeAccount(userId: String) {
        campaignRepository.deleteAllCampaigns(userId)
        userRepository.deleteUser()
        SessionManager.logout()
    }

    fun logOut() {
        SessionManager.logout()
        userRepository.logOut()
    }

}