package com.projetointegrador.pi_application.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyLatLng(
    val latitude: String = "",
    val longitude: String = ""
) : Parcelable