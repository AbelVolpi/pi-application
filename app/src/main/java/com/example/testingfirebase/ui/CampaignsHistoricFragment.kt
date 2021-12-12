package com.example.testingfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testingfirebase.R
import com.example.testingfirebase.databinding.FragmentCampaignsHistoricBinding
import com.example.testingfirebase.databinding.FragmentViewCampaignBinding

class CampaignsHistoricFragment : Fragment() {

    private lateinit var binding: FragmentCampaignsHistoricBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCampaignsHistoricBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {

    }

}