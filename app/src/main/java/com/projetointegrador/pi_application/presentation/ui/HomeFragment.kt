package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.databinding.FragmentHomeBinding
import com.projetointegrador.pi_application.core.utils.Utils.showAboutDialog

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val navController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            cardViewLoginAndCreateCampaign.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }
            cardViewGoToMap.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_mapsFragment)
            }
            aboutAppButton.setOnClickListener {
                showAboutDialog(requireContext(), layoutInflater)
            }
        }
    }

}
