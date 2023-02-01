package com.projetointegrador.pi_application.domain.user

import com.projetointegrador.pi_application.data.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun signUp(email: String, password: String) = userRepository.signUp(email, password)
}
