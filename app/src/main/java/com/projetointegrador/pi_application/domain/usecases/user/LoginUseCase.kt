package com.projetointegrador.pi_application.domain.usecases.user

import com.projetointegrador.pi_application.data.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun login(email: String, password: String) = userRepository.login(email, password)

    fun verifyUserAlreadyLogged() = userRepository.checkAlreadyLogged()
}
