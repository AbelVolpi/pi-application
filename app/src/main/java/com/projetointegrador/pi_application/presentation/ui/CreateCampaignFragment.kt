package com.projetointegrador.pi_application.presentation.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.core.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.core.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.core.utils.extensions.toast
import com.projetointegrador.pi_application.databinding.FragmentCreateCampaignBinding
import com.projetointegrador.pi_application.domain.models.Address
import com.projetointegrador.pi_application.domain.models.Campaign
import com.projetointegrador.pi_application.presentation.viewmodel.CreateCampaignViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateCampaignFragment : BaseFragment<FragmentCreateCampaignBinding>(FragmentCreateCampaignBinding::inflate) {
    @Inject lateinit var sessionManager: SessionManager

    private lateinit var launcherForGallery: ActivityResultLauncher<String>
    private val viewModel: CreateCampaignViewModel by viewModels()
    private var imageUri: Uri? = null
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultContracts()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            cardViewPhoto.setOnClickListener { launcherForGallery.launch("image/*") }
            removeImage.setOnClickListener {
                imagePhoto.setImageResource(0)
                withoutImageTextView.visibility = View.VISIBLE
                addPhotoImageView.visibility = View.VISIBLE
                removeImage.visibility = View.INVISIBLE
            }
            mainLayout.setOnClickListener {
                activity?.hideSoftKeyboard()
                it.clearScreenFocus()
            }
            mainLinearLayout.setOnClickListener {
                activity?.hideSoftKeyboard()
                mainLayout.clearScreenFocus()
            }
            buttonCreateCampaign.setOnClickListener { createCampaign() }
            arrowBack.setOnClickListener { navController.popBackStack() }
            campaignCategoryFieldOptions.setAdapter(
                ArrayAdapter(requireContext(), R.layout.dropdown_item, resources.getStringArray(R.array.donate_options))
            )
        }
    }

    private fun createCampaign() {
        if (!verifyFields()) {
            context?.toast(getString(R.string.review_fields))
            return
        }
        binding.progressCreateCampaign.visibility = View.VISIBLE
        with(binding) {
            val campaign =
                Campaign(
                    userId = sessionManager.getUserId() ?: "",
                    campaignName = campaignNameField.text.toString(),
                    campaignDescription = campaignDescriptionField.text.toString(),
                    campaignCategory = campaignCategoryFieldOptions.text.toString(),
                    campaignAddress =
                        Address(
                            campaignsStreetField.text.toString(),
                            campaignsNumberField.text.toString(),
                            campaignsDistrictField.text.toString(),
                            campaignsCityField.text.toString(),
                            campaignsStateField.text.toString()
                        )
                )
            sendRequest(campaign)
        }
    }

    private fun sendRequest(campaign: Campaign) {
        viewModel.createCampaign(campaign, imageUri).observe(viewLifecycleOwner) { response ->
            binding.progressCreateCampaign.visibility = View.INVISIBLE
            when (response) {
                is TaskResponse.Success -> {
                    requireContext().toast(getString(R.string.campaign_created_successfully))
                    navController.popBackStack()
                }
                is TaskResponse.Failure -> {
                    Log.e("CreateCampaign", response.errorMessage)
                    requireContext().toast(response.errorMessage)
                }
            }
        }
    }

    private fun verifyFields(): Boolean {
        with(binding) {
            return !campaignNameField.text.isNullOrEmpty() &&
                !campaignDescriptionField.text.isNullOrEmpty() &&
                !campaignCategoryFieldOptions.text.isNullOrEmpty() &&
                !campaignsStreetField.text.isNullOrEmpty() &&
                !campaignsNumberField.text.isNullOrEmpty() &&
                !campaignsDistrictField.text.isNullOrEmpty() &&
                !campaignsCityField.text.isNullOrEmpty() &&
                !campaignsStateField.text.isNullOrEmpty()
        }
    }

    private fun initResultContracts() {
        launcherForGallery =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    with(binding) {
                        withoutImageTextView.visibility = View.INVISIBLE
                        addPhotoImageView.visibility = View.INVISIBLE
                        removeImage.visibility = View.VISIBLE
                        imagePhoto.setImageURI(uri)
                        imageUri = uri
                    }
                }
            }
    }
}
