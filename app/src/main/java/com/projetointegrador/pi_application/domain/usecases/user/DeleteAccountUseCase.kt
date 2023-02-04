package com.projetointegrador.pi_application.domain.usecases.user

import com.projetointegrador.pi_application.data.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun deleteAccount() = userRepository.deleteUser()
}
