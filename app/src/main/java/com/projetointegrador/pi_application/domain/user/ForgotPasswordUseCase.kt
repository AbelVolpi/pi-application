package com.projetointegrador.pi_application.domain.user

import com.projetointegrador.pi_application.data.repository.UserRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun forgotPassword(email: String) = userRepository.forgotPassword(email)
}
