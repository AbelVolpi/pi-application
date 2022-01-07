package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.adapter.CampaignsHistoricAdapter
import com.projetointegrador.pi_application.databinding.FragmentCampaignsHistoricBinding
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.extensions.toast
import com.projetointegrador.pi_application.viewmodel.CampaignsHistoricViewModel

class CampaignsHistoricFragment : Fragment() {

    private lateinit var binding: FragmentCampaignsHistoricBinding
    private val viewModel: CampaignsHistoricViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
                    adapter = CampaignsHistoricAdapter(campaignsList)
                    layoutManager = LinearLayoutManager(requireContext())
                    addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                }
            }
        }
    }
}