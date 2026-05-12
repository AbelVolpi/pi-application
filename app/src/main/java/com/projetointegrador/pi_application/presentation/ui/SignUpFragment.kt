package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.core.utils.Utils.validateEmail
import com.projetointegrador.pi_application.core.utils.Utils.validatePassword
import com.projetointegrador.pi_application.core.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.core.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.core.utils.extensions.toast
import com.projetointegrador.pi_application.databinding.FragmentSignUpBinding
import com.projetointegrador.pi_application.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private val viewModel: SignUpViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            arrowBack.setOnClickListener { navController.popBackStack() }
            buttonSignUp.setOnClickListener { makeSignUp() }
            mainLayout.setOnClickListener {
                activity?.hideSoftKeyboard()
                it.clearScreenFocus()
            }
        }
    }

    private fun makeSignUp() {
        with(binding) {
            if (
                !validateEmail(emailField.text.toString()) ||
                !validatePassword(passwordField.text.toString()) ||
                passwordField.text.toString() != confirmPasswordField.text.toString()
            ) {
                requireContext().toast(getString(R.string.review_credentials))
            } else {
                doSignUp(emailField.text.toString(), passwordField.text.toString())
            }
        }
    }

    private fun doSignUp(
        email: String,
        password: String
    ) {
        binding.progressBarSignUp.visibility = View.VISIBLE
        viewModel.signUp(email, password).observe(viewLifecycleOwner) { response ->
            binding.progressBarSignUp.visibility = View.INVISIBLE
            when (response) {
                is TaskResponse.Success -> {
                    requireContext().toast(getString(R.string.sign_up_completed, response.data))
                    navController.navigate(R.id.action_registerFragment_to_profileFragment)
                }
                is TaskResponse.Failure -> {
                    Log.e("SignUpError", response.errorMessage)
                    requireContext().toast(response.errorMessage)
                }
            }
        }
    }
}
