package com.projetointegrador.pi_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.data.repository.UserRepository

class SignUpViewModel: ViewModel() {

    fun signUp(email:String, password: String) =  UserRepository().signUp(email, password)

}