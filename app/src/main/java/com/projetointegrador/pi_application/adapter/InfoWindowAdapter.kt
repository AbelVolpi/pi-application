package com.projetointegrador.pi_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.CustomMarkerContentBinding
import com.projetointegrador.pi_application.models.Campaign

class InfoWindowAdapter(private val context: Context, private val campaign: Campaign) : GoogleMap.InfoWindowAdapter {

    private lateinit var binding: CustomMarkerContentBinding

    override fun getInfoWindow(p0: Marker?): View {
        binding = CustomMarkerContentBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )

        changeInfoWindowContent()

        return binding.root
    }

    private fun changeInfoWindowContent() {
        with(binding) {
            tittleLabel.text = campaign.campaignName
            campaignAddress.text =
                context.getString(
                    R.string.address_model,
                    campaign.campaignAddress?.street,
                    campaign.campaignAddress?.number,
                    campaign.campaignAddress?.district,
                    campaign.campaignAddress?.city,
                    campaign.campaignAddress?.state
                )
            campaignCategoryText.text = campaign.campaignCategory
        }
    }

    override fun getInfoContents(p0: Marker?): View? {
        return null
    }
}