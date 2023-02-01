package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentForgotPasswordBinding
import com.projetointegrador.pi_application.presentation.viewmodel.ForgotPasswordViewModel
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.Utils
import com.projetointegrador.pi_application.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }
            buttonForgotPassword.setOnClickListener {
                forgotPassword()
            }
            mainLayout.setOnClickListener {
                activity?.hideSoftKeyboard()
                it.clearScreenFocus()
            }
        }
    }

    private fun forgotPassword() {
        with(binding) {
            if (!Utils.validateEmail(emailField.text.toString())) {
                requireContext().toast(getString(R.string.review_credentials))
            } else {
                sendForgotPasswordEmail(emailField.text.toString())
            }
        }
    }

    private fun sendForgotPasswordEmail(email: String) {
        viewModel.forgotPassword(email).observe(viewLifecycleOwner) { response ->
            when (response) {
                is FirebaseResponse.Success -> {
                    requireContext().toast(getString(R.string.email_sent))
                }
                is FirebaseResponse.Failure -> {
                    requireContext().toast(getString(R.string.error_has_occurred))
                    Log.e("ForgotPasswordError:", response.errorMessage)
                }
            }
        }
    }
}
