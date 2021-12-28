package com.projetointegrador.pi_application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val navController by lazy {
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        with(binding){
            buttonCreateCampaign.setOnClickListener{
                navController.navigate(R.id.action_profileFragment_to_createCampaignFragment)
            }
            buttonSeeHistoric.setOnClickListener{
                navController.navigate(R.id.action_profileFragment_to_campaignsHistoricFragment)
            }
            buttonGoToMap.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_mapsFragment)
            }
        }
    }

}