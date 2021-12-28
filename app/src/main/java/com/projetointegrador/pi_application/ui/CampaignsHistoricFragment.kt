package com.projetointegrador.pi_application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projetointegrador.pi_application.databinding.FragmentCampaignsHistoricBinding

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