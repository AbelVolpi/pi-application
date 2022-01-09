package com.projetointegrador.pi_application.models

data class Campaign(
    var campaignId: String = "",
    var userId: String = "",
    var campaignName: String = "",
    var campaignDescription: String = "",
    var campaignCategory: String = "",
    var campaignAddress: Address? = null,
    var campaignLatLng: MyLatLng? = null
)

