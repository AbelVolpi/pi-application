package com.projetointegrador.pi_application.presentation.ui

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.databinding.FragmentSplashBinding
import com.projetointegrador.pi_application.presentation.viewmodel.SplashViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()
    private val navController by lazy {
        findNavController()
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