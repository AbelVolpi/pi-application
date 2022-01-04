package com.projetointegrador.pi_application.viewmodel

import androidx.lifecycle.ViewModel
import com.projetointegrador.pi_application.repository.UserRepository

class SignUpViewModel: ViewModel() {

    fun signUp(email:String, password: String) =  UserRepository().signUp(email, password)

}