package com.example.testingfirebase.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testingfirebase.R
import com.example.testingfirebase.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {


    private lateinit var binding: FragmentSplashBinding
    private val navController by lazy {
        findNavController()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        delay(1000) {
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }

    }

    private fun delay(delay: Long = 1500, action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, delay)
    }
}