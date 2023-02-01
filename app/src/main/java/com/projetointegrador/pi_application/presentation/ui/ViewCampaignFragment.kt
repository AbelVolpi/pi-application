package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentViewCampaignBinding
import com.projetointegrador.pi_application.utils.extensions.setImageUsingGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewCampaignFragment : Fragment() {

    private lateinit var binding: FragmentViewCampaignBinding
    private val arguments: ViewCampaignFragmentArgs by navArgs()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewCampaignBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        with(binding) {
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }

            campaignName.text = arguments.campaign.campaignName
            campaignDescription.text = arguments.campaign.campaignDescription
            campaignCategoryText.text = arguments.campaign.campaignCategory
            campaignAddressValue.text = context?.getString(
                R.string.address_model,
                arguments.campaign.campaignAddress?.street,
                arguments.campaign.campaignAddress?.number,
                arguments.campaign.campaignAddress?.district,
                arguments.campaign.campaignAddress?.city,
                arguments.campaign.campaignAddress?.state
            )
            if (arguments.campaign.campaignImageUrl.isNotEmpty())
                campaignImageView.setImageUsingGlide(requireContext(), arguments.campaign.campaignImageUrl)
        }
    }
}
