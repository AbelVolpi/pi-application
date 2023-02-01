package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun login(email: String, password: String) = loginUseCase.login(email, password)
}
