package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.UserRepository

class ForgotPasswordViewModel : ViewModel() {

    private val userRepository = UserRepository()

    fun forgotPassword(email: String) = userRepository.forgotPassword(email)

}