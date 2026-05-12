package com.projetointegrador.pi_application.core.utils

sealed class TaskResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : TaskResponse<T>()

    data class Failure<T : Any>(val errorMessage: String) : TaskResponse<T>()
}
