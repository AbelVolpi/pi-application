package com.projetointegrador.pi_application.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.MainApplication
import com.projetointegrador.pi_application.databinding.HistoricItemBinding
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.extensions.dpToPx

class CampaignsHistoricAdapter(
    private val campaignsList: ArrayList<Campaign>,
    private val removeCampaign: (String) -> Boolean,
    private val openCampaign: (Campaign) -> Unit
) :
    RecyclerView.Adapter<CampaignsHistoricAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: HistoricItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(campaign: Campaign, position: Int) {
            with(binding) {
                campaignTittle.text = campaign.campaignName
                campaignAddress.text = MainApplication.applicationContext()
                    .getString(
                        R.string.address_model,
                        campaign.campaignAddress?.street,
                        campaign.campaignAddress?.number,
                        campaign.campaignAddress?.district,
                        campaign.campaignAddress?.city,
                        campaign.campaignAddress?.state
                    )
                campaignCategoryText.text = campaign.campaignCategory
                if (position == 0)
                    historicItemMainLayout.setPadding(20.dpToPx(), 20, 20.dpToPx(), 0)

                historicItemMainLayout.apply {
                    setOnLongClickListener {
                        deleteCampaignButton.visibility = View.VISIBLE

                        return@setOnLongClickListener true
                    }
                    setOnClickListener {
                        if (deleteCampaignButton.visibility == View.VISIBLE)
                            deleteCampaignButton.visibility = View.INVISIBLE
                        else
                            openCampaign(campaign)
                    }
                }
                deleteCampaignButton.setOnClickListener {
                    removeCampaign(campaign.campaignId)
                    removeItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HistoricItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            campaignsList[position],
            position
        )
    }

    override fun getItemCount() = campaignsList.size

    private fun removeItem(campaignPosition: Int) {
        campaignsList.removeAt(campaignPosition)
        notifyItemRemoved(campaignPosition)
        notifyItemRangeChanged(campaignPosition, campaignsList.size)
    }
}
