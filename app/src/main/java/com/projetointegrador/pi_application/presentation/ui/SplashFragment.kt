package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.databinding.FragmentSplashBinding
import com.projetointegrador.pi_application.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.verifyUserAlreadyLogged()) {
                navController.navigate(R.id.action_splashFragment_to_profileFragment)
            } else {
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 1000)
    }
}
