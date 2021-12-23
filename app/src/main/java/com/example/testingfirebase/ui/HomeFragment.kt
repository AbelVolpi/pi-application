package com.example.testingfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testingfirebase.R
import com.example.testingfirebase.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            buttonCreateCampaign.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }
            buttonGoToMap.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_filterFragment)
            }
        }
    }
}
