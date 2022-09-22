package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.UserRepository

class SplashViewModel : ViewModel() {

    fun verifyUserAlreadyLogged() = UserRepository().checkAlreadyLogged()

}