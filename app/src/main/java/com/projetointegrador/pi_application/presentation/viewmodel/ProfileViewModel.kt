package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.domain.usecases.campaign.DeleteCampaignUseCase
import com.projetointegrador.pi_application.domain.usecases.user.DeleteAccountUseCase
import com.projetointegrador.pi_application.domain.usecases.user.LogoutUseCase
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
