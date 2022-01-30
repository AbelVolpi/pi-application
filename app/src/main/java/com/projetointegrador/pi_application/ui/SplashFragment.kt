package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentSplashBinding
import com.projetointegrador.pi_application.viewmodel.SplashViewModel

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by activityViewModels()
    private lateinit var binding: FragmentSplashBinding
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        delay {
            if (viewModel.verifyUserAlreadyLogged())
                navController.navigate(R.id.action_splashFragment_to_profileFragment)
            else
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

    private fun delay(action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, 1000)
    }
}