package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.core.base.BaseFragment
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.core.utils.Utils.validateEmail
import com.projetointegrador.pi_application.core.utils.Utils.validatePassword
import com.projetointegrador.pi_application.core.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.core.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.core.utils.extensions.toast
import com.projetointegrador.pi_application.databinding.FragmentLoginBinding
import com.projetointegrador.pi_application.domain.models.User
import com.projetointegrador.pi_application.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    @Inject lateinit var sessionManager: SessionManager

    private val viewModel: LoginViewModel by viewModels()
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
            mainLayout.setOnClickListener {
                activity?.hideSoftKeyboard()
                it.clearScreenFocus()
            }
            arrowBack.setOnClickListener { navController.popBackStack() }
            textForgotPassword.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
            textSignUp.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }
            buttonLogin.setOnClickListener { makeLogin() }
        }
    }

    private fun makeLogin() {
        with(binding) {
            if (!validateEmail(emailField.text.toString()) || !validatePassword(passwordField.text.toString())) {
                requireContext().toast(getString(R.string.review_credentials))
            } else {
                doLogin(emailField.text.toString(), passwordField.text.toString())
            }
        }
    }

    private fun doLogin(
        email: String,
        password: String
    ) {
        binding.progressGoToMap.visibility = View.VISIBLE
        viewModel.login(email, password).observe(viewLifecycleOwner) { response ->
            when (response) {
                is TaskResponse.Success -> saveUserAndNavigate(response.data)
                is TaskResponse.Failure -> showErrorMessage(response.errorMessage)
            }
            binding.progressGoToMap.visibility = View.INVISIBLE
        }
    }

    private fun saveUserAndNavigate(user: User) {
        sessionManager.saveUserData(user.userId, user.email)
        requireContext().toast(getString(R.string.logged_message, user.email))
        navController.navigate(R.id.action_loginFragment_to_profileFragment)
    }

    private fun showErrorMessage(errorMessage: String) {
        Log.e("LoginError", errorMessage)
        requireContext().toast(errorMessage)
    }
}
