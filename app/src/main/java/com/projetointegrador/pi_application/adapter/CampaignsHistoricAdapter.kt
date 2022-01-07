package com.projetointegrador.pi_application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetointegrador.pi_application.databinding.HistoricItemBinding
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.extensions.dpToPx

class CampaignsHistoricAdapter(private val campaignsList: List<Campaign>) :
    RecyclerView.Adapter<CampaignsHistoricAdapter.ViewHolder>() {

    class ViewHolder(private val binding: HistoricItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(campaign: Campaign, position: Int) {
            with(binding) {
                campaignTittle.text = campaign.campaignName
                campaignAddress.text = campaign.campaignAddress
                campaignCategoryText.text = campaign.campaignCategory
                if (position == 0)
                    historicItemMainLayout.setPadding(20.dpToPx(), 70, 20.dpToPx(), 0)
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
}