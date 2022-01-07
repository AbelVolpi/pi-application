package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentCreateCampaignBinding
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.extensions.toast
import com.projetointegrador.pi_application.viewmodel.CreateCampaignViewModel

class CreateCampaignFragment : Fragment() {

    private lateinit var binding: FragmentCreateCampaignBinding
    private val viewModel: CreateCampaignViewModel by activityViewModels()
    private val navController by lazy {
        findNavController()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCampaignBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        with(binding) {
            buttonCreateCampaign.setOnClickListener {
                createCampaign()
            }
            categoryFieldOptions.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item,
                    resources.getStringArray(R.array.donate_options)
                )
            )
        }
    }

    private fun createCampaign() {
        with(binding) {

            val userId = SessionManager.getGetUserId() ?: ""
            val campaignName = campaignNameField.text.toString()
            val campaignAddress = campaignAddressField.text.toString()
            val campaignCategory = categoryFieldOptions.text.toString()

            if (campaignName.isNotEmpty() && campaignAddress.isNotEmpty() && campaignCategory.isNotEmpty()) {
                sendRequest(
                    Campaign(
                        userId = userId,
                        campaignName = campaignName,
                        campaignAddress = campaignAddress,
                        campaignCategory = campaignCategory
                    )
                )
            } else {
                Toast.makeText(requireContext(), getString(R.string.review_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendRequest(campaign: Campaign) {
        viewModel.createCampaign(campaign).observe(viewLifecycleOwner) { response ->
            when (response) {
                is FirebaseResponse.Success -> {
                    showFeedbackAndBackStack()
                }
                is FirebaseResponse.Failure -> {
                    showErrorMessage(response)
                }
            }
        }
    }

    private fun showErrorMessage(response: FirebaseResponse<Boolean>) {
        response as FirebaseResponse.Failure
        Log.e("LoginError", response.errorMessage)
        requireContext().toast(response.errorMessage)
    }

    private fun showFeedbackAndBackStack() {
        requireContext().toast(getString(R.string.campaign_created_successfully))
        navController.popBackStack()
    }

}