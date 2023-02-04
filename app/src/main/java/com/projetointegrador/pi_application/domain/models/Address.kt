package com.projetointegrador.pi_application.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val street: String = "",
    var number: String = "",
    var district: String = "",
    var city: String = "",
    var state: String = ""
) : Parcelable
