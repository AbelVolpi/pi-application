package com.projetointegrador.pi_application.domain.user

import com.projetointegrador.pi_application.data.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun logOut() = userRepository.logOut()
}
