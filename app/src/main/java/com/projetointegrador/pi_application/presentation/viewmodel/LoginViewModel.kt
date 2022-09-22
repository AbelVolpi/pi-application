package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    fun login(email: String, password: String) = userRepository.login(email, password)

}