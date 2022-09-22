package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.databinding.FragmentViewCampaignBinding
import com.projetointegrador.pi_application.core.utils.extensions.setImageUsingGlide

class ViewCampaignFragment : BaseFragment<FragmentViewCampaignBinding>(FragmentViewCampaignBinding::inflate) {

    private val arguments: ViewCampaignFragmentArgs by navArgs()
    private val navController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
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