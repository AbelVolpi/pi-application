package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.core.utils.extensions.toast
import com.projetointegrador.pi_application.databinding.FragmentCampaignsHistoricBinding
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.presentation.adapter.CampaignsHistoricAdapter
import com.projetointegrador.pi_application.presentation.viewmodel.CampaignsHistoricViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CampaignsHistoricFragment : BaseFragment<FragmentCampaignsHistoricBinding>(FragmentCampaignsHistoricBinding::inflate) {
    @Inject lateinit var sessionManager: SessionManager

    private val viewModel: CampaignsHistoricViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowBack.setOnClickListener { navController.popBackStack() }
        loadHistoric()
    }

    private fun loadHistoric() {
        val userId = sessionManager.getUserId() ?: ""
        viewModel.getCampaignsByUser(userId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is TaskResponse.Success -> populateRecyclerView(response.data as ArrayList<Campaign>)
                is TaskResponse.Failure -> requireContext().toast(response.errorMessage)
            }
            binding.historicProgressBar.visibility = View.GONE
        }
    }

    private fun populateRecyclerView(campaignsList: ArrayList<Campaign>) {
        with(binding) {
            if (campaignsList.isEmpty()) {
                historicCenterLabel.apply {
                    text = getString(R.string.you_havent_created_a_campaign_yet)
                    visibility = View.VISIBLE
                }
            } else {
                historicRecycler.apply {
                    adapter = CampaignsHistoricAdapter(campaignsList, ::removeCampaign, ::openCampaign)
                    layoutManager = LinearLayoutManager(requireContext())
                    addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                }
            }
        }
    }

    private fun removeCampaign(campaignId: String): Boolean {
        var campaignWasRemoved = false
        viewModel.deleteCampaign(campaignId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is TaskResponse.Success -> {
                    campaignWasRemoved = true
                    context?.toast(getString(R.string.campaign_removed_successfully))
                }
                is TaskResponse.Failure -> {
                    context?.toast(response.errorMessage)
                    campaignWasRemoved = false
                }
            }
        }
        return campaignWasRemoved
    }

    private fun openCampaign(campaign: Campaign) {
        navController.navigate(
            CampaignsHistoricFragmentDirections.actionCampaignsHistoricFragmentToViewCampaignFragment(campaign.campaignId)
        )
    }
}
