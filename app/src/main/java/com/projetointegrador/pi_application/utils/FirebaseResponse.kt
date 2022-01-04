package com.projetointegrador.pi_application.utils

sealed class FirebaseResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : FirebaseResponse<T>()
    data class Failure<T : Any>(val errorMessage: String) : FirebaseResponse<T>()
}
