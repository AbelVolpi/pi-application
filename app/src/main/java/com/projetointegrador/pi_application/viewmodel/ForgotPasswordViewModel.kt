package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.UserRepository

class ForgotPasswordViewModel : ViewModel() {

    private val userRepository = UserRepository()

    fun forgotPassword(email: String) = userRepository.forgotPassword(email)

}