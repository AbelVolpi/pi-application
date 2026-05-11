package com.projetointegrador.pi_application.presentation.adapter

import android.view.MotionEvent
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.CustomMarkerContentBinding
import com.projetointegrador.pi_application.domain.models.Campaign
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

class CampaignInfoWindow(
    mapView: MapView,
    private val onWindowClick: (campaignId: String) -> Unit
) : MarkerInfoWindow(R.layout.custom_marker_content, mapView) {
    override fun onOpen(item: Any?) {
        val campaign = (item as? Marker)?.relatedObject as? Campaign ?: return
        CustomMarkerContentBinding.bind(mView).apply {
            tittleLabel.text = campaign.campaignName
            campaignAddress.text =
                mView.context.getString(
                    R.string.address_model,
                    campaign.campaignAddress?.street,
                    campaign.campaignAddress?.number,
                    campaign.campaignAddress?.district,
                    campaign.campaignAddress?.city,
                    campaign.campaignAddress?.state
                )
            campaignCategoryText.text = campaign.campaignCategory
        }

        mView.setOnClickListener { onWindowClick(campaign.campaignId) }
        mView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) view.performClick()
            true
        }
    }

    override fun onClose() {}
}
