package com.projetointegrador.pi_application.models

data class Address(
    val street: String = "",
    var number: String = "",
    var district: String = "",
    var city: String = "",
    var state: String = ""
)
