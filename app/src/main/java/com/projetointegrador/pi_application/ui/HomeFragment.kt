package com.projetointegrador.pi_application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentHomeBinding
import com.projetointegrador.pi_application.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
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

        with(binding){
            buttonCreateCampaign.setOnClickListener {
                if (viewModel.verifyUserAlreadyLogged())
                    navController.navigate(R.id.action_homeFragment_to_profileFragment)
                else
                    navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }
            buttonGoToMap.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_mapsFragment)
            }
        }
    }
}
