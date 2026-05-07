package com.projetointegrador.pi_application.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MyLatLng(
    val latitude: String = "",
    val longitude: String = ""
) : Parcelable
