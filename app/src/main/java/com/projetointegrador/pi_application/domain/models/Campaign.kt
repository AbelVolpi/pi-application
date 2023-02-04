package com.projetointegrador.pi_application.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Campaign(
    var campaignId: String = "",
    var userId: String = "",
    var campaignName: String = "",
    var campaignDescription: String = "",
    var campaignCategory: String = "",
    var campaignAddress: Address? = null,
    var campaignLatLng: MyLatLng? = null,
    var campaignImageUrl: String = ""
) : Parcelable
