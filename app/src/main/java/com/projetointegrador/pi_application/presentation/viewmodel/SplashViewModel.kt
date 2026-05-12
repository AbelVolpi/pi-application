package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.domain.usecases.user.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val checkSessionUseCase: CheckSessionUseCase
    ) : ViewModel() {
        fun isLoggedIn(): Boolean = checkSessionUseCase.isLoggedIn()
    }
