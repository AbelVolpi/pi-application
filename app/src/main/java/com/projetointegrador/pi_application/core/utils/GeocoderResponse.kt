package com.projetointegrador.pi_application.core.utils

sealed class GeocoderResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : GeocoderResponse<T>()
    data class Failure<T : Any>(val errorMessage: String) : GeocoderResponse<T>()
}