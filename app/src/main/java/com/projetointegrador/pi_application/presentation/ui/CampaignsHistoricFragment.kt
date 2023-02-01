package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentCampaignsHistoricBinding
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.presentation.adapter.CampaignsHistoricAdapter
import com.projetointegrador.pi_application.presentation.viewmodel.CampaignsHistoricViewModel
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignsHistoricFragment : Fragment() {

    private lateinit var binding: FragmentCampaignsHistoricBinding
    private val viewModel: CampaignsHistoricViewModel by viewModels()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCampaignsHistoricBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        getHistoric()
        with(binding) {
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    private fun getHistoric() {
        val userId = SessionManager.getGetUserId() ?: ""
        var campaignsList: ArrayList<Campaign>

        viewModel.getCampaignsByUser(userId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is FirebaseResponse.Success -> {
                    campaignsList = response.data as ArrayList<Campaign>
                    populateRecyclerView(campaignsList)
                }
                is FirebaseResponse.Failure -> {
                    requireContext().toast(response.errorMessage)
                }
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
                    adapter = CampaignsHistoricAdapter(
                        campaignsList,
                        ::removeCampaign,
                        ::openCampaign
                    )
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
                is FirebaseResponse.Success -> {
                    campaignWasRemoved = true
                    context?.toast("Campaign remove successfully")
                }
                is FirebaseResponse.Failure -> {
                    context?.toast(response.errorMessage)
                    campaignWasRemoved = false
                }
            }
        }
        return campaignWasRemoved
    }

    private fun openCampaign(campaign: Campaign) {
        navController.navigate(
            CampaignsHistoricFragmentDirections.actionCampaignsHistoricFragmentToViewCampaignFragment(
                campaign
            )
        )
    }
}
