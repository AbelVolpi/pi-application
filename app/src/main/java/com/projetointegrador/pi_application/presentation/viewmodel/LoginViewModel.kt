package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.domain.usecases.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
        private val sessionManager: SessionManager
    ) : ViewModel() {
        fun login(
            email: String,
            password: String
        ): LiveData<TaskResponse<String>> =
            loginUseCase.login(email, password).map { response ->
                when (response) {
                    is TaskResponse.Success -> {
                        sessionManager.saveUserData(response.data.userId, response.data.email)
                        TaskResponse.Success(response.data.email)
                    }
                    is TaskResponse.Failure -> TaskResponse.Failure(response.errorMessage)
                }
            }
    }
