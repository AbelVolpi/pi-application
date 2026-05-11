package com.projetointegrador.pi_application.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String = "",
    var number: String = "",
    var district: String = "",
    var city: String = "",
    var state: String = "",
)
