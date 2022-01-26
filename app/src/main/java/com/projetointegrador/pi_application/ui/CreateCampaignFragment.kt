package com.projetointegrador.pi_application.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentCreateCampaignBinding
import com.projetointegrador.pi_application.models.Address
import com.projetointegrador.pi_application.models.Campaign
import com.projetointegrador.pi_application.models.MyLatLng
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.GeocoderResponse
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.utils.extensions.toast
import com.projetointegrador.pi_application.viewmodel.CreateCampaignViewModel

class CreateCampaignFragment : Fragment() {

    private lateinit var binding: FragmentCreateCampaignBinding
    private lateinit var launcherForGallery: ActivityResultLauncher<String>
    private val viewModel: CreateCampaignViewModel by activityViewModels()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultContracts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCampaignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            cardViewPhoto.setOnClickListener {
                launcherForGallery.launch("image/*")
            }
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
            buttonCreateCampaign.setOnClickListener {
                createCampaign()
            }
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }
            campaignCategoryFieldOptions.setAdapter(
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

            if (verifyFields()) {
                progressCreateCampaign.visibility = View.VISIBLE

                val userId = SessionManager.getGetUserId() ?: ""
                val campaignName = campaignNameField.text.toString()
                val campaignDescription = campaignDescriptionField.text.toString()
                val campaignCategory = campaignCategoryFieldOptions.text.toString()
                val campaignStreet = campaignsStreetField.text.toString()
                val campaignNumber = campaignsNumberField.text.toString()
                val campaignDistrict = campaignsDistrictField.text.toString()
                val campaignCity = campaignsCityField.text.toString()
                val campaignState = campaignsStateField.text.toString()
                val campaignAddress = Address(
                    campaignStreet,
                    campaignNumber,
                    campaignDistrict,
                    campaignCity,
                    campaignState
                )

                val address = "${campaignStreet},${campaignNumber}-${campaignDistrict},${campaignCity}-${campaignState}"

                viewModel.getLatLongFromAddress(address).observe(viewLifecycleOwner) { geocoderResponse ->
                    when (geocoderResponse) {
                        is GeocoderResponse.Success -> {
                            val campaign = Campaign(
                                userId = userId,
                                campaignName = campaignName,
                                campaignDescription = campaignDescription,
                                campaignCategory = campaignCategory,
                                campaignAddress = campaignAddress,
                                campaignLatLng = MyLatLng(
                                    geocoderResponse.data.latitude.toString(),
                                    geocoderResponse.data.longitude.toString()
                                )
                            )
                            sendRequest(campaign)
                        }
                        is GeocoderResponse.Failure -> {
                            context?.toast(getString(R.string.error_has_occurred))
                            progressCreateCampaign.visibility = View.INVISIBLE
                        }
                    }
                }
            } else {
                context?.toast(getString(R.string.review_fields))
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
            binding.progressCreateCampaign.visibility = View.INVISIBLE
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

    private fun verifyFields(): Boolean {
        with(binding) {
            return !campaignNameField.text.isNullOrEmpty()
                    && !campaignDescriptionField.text.isNullOrEmpty()
                    && !campaignCategoryFieldOptions.text.isNullOrEmpty()
                    && !campaignsStreetField.text.isNullOrEmpty()
                    && !campaignsNumberField.text.isNullOrEmpty()
                    && !campaignsDistrictField.text.isNullOrEmpty()
                    && !campaignsCityField.text.isNullOrEmpty()
                    && !campaignsStateField.text.isNullOrEmpty()
        }
    }

    private fun initResultContracts() {
        launcherForGallery = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                with(binding) {
                    withoutImageTextView.visibility = View.INVISIBLE
                    addPhotoImageView.visibility = View.INVISIBLE
                    removeImage.visibility = View.VISIBLE

                    imagePhoto.setImageURI(uri)
                }
            }
        }
    }
}