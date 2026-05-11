package com.projetointegrador.pi_application.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Campaign(
    @SerialName("campaign_id") var campaignId: String = "",
    @SerialName("user_id") var userId: String = "",
    @SerialName("campaign_name") var campaignName: String = "",
    @SerialName("campaign_description") var campaignDescription: String = "",
    @SerialName("campaign_category") var campaignCategory: String = "",
    @SerialName("campaign_address") var campaignAddress: Address? = null,
    @SerialName("campaign_lat_lng") var campaignLatLng: MyLatLng? = null,
    @SerialName("campaign_image_url") var campaignImageUrl: String = "",
)
