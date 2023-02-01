package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.user.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    fun signUp(email: String, password: String) = signUpUseCase.signUp(email, password)
}
