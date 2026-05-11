package com.projetointegrador.pi_application.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MyLatLng(
    val latitude: String = "",
    val longitude: String = "",
)
