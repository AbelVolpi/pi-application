package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.domain.usecases.user.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
        private val sessionManager: SessionManager
    ) : ViewModel() {
        fun signUp(
            email: String,
            password: String
        ): LiveData<TaskResponse<String>> =
            signUpUseCase.signUp(email, password).map { response ->
                when (response) {
                    is TaskResponse.Success -> {
                        sessionManager.saveUserData(response.data.userId, response.data.email)
                        TaskResponse.Success(response.data.email)
                    }
                    is TaskResponse.Failure -> TaskResponse.Failure(response.errorMessage)
                }
            }
    }
