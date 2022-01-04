package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.UserRepository

class LoginViewModel : ViewModel() {

    fun login(email: String, password: String) = UserRepository().login(email, password)

}