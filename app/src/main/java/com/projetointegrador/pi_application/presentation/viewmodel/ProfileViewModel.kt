package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.campaign.DeleteCampaignUseCase
import com.projetointegrador.pi_application.domain.user.DeleteAccountUseCase
import com.projetointegrador.pi_application.domain.user.LogoutUseCase
import com.projetointegrador.pi_application.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val deleteCampaignUseCase: DeleteCampaignUseCase
) : ViewModel() {

    fun removeAccount(userId: String) {
        deleteCampaignUseCase.deleteAllCampaigns(userId)
        deleteAccountUseCase.deleteAccount()
        SessionManager.logout()
    }

    fun logOut() {
        logoutUseCase.logOut()
    }
}
