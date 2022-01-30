package com.projetointegrador.pi_application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentHomeBinding
import com.projetointegrador.pi_application.utils.Utils.showDialogAbout

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
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
                showDialogAbout(requireContext(), layoutInflater)
            }
        }
    }

}
