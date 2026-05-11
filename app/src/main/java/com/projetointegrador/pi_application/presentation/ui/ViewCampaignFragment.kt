package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.utils.FirebaseResponse
import com.projetointegrador.pi_application.core.utils.extensions.setImageUsingGlide
import com.projetointegrador.pi_application.databinding.FragmentViewCampaignBinding
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.presentation.viewmodel.ViewCampaignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewCampaignFragment : Fragment() {
    private lateinit var binding: FragmentViewCampaignBinding
    private val args: ViewCampaignFragmentArgs by navArgs()
    private val viewModel: ViewCampaignViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentViewCampaignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowBack.setOnClickListener { navController.popBackStack() }
        loadCampaign()
    }

    private fun loadCampaign() {
        viewModel.getCampaignById(args.campaignId).observe(viewLifecycleOwner) { response ->
            when (response) {
                is FirebaseResponse.Success -> bindCampaign(response.data)
                is FirebaseResponse.Failure -> Unit
            }
        }
    }

    private fun bindCampaign(campaign: Campaign) {
        with(binding) {
            campaignName.text = campaign.campaignName
            campaignDescription.text = campaign.campaignDescription
            campaignCategoryText.text = campaign.campaignCategory
            campaignAddressValue.text =
                context?.getString(
                    R.string.address_model,
                    campaign.campaignAddress?.street,
                    campaign.campaignAddress?.number,
                    campaign.campaignAddress?.district,
                    campaign.campaignAddress?.city,
                    campaign.campaignAddress?.state,
                )
            if (campaign.campaignImageUrl.isNotEmpty()) {
                campaignImageView.setImageUsingGlide(requireContext(), campaign.campaignImageUrl)
            }
        }
    }
}
