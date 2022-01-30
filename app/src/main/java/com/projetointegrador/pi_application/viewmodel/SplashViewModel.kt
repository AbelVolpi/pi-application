package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.UserRepository

class SplashViewModel : ViewModel() {

    fun verifyUserAlreadyLogged() = UserRepository().checkAlreadyLogged()

}