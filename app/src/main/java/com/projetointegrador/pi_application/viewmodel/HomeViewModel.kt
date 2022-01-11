package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.UserRepository

class HomeViewModel : ViewModel() {

    fun verifyUserAlreadyLogged() = UserRepository().checkAlreadyLogged()

}