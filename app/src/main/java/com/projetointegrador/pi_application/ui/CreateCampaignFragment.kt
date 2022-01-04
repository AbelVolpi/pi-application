package com.projetointegrador.pi_application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentCreateCampaignBinding
import com.projetointegrador.pi_application.viewmodel.CreateCampaignViewModel

class CreateCampaignFragment : Fragment() {

    private lateinit var binding: FragmentCreateCampaignBinding
    private val viewModel: CreateCampaignViewModel by activityViewModels()

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
                sendRequest()
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

    private fun sendRequest() {

    }

}