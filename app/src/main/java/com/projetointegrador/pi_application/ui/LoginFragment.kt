package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentLoginBinding
import com.projetointegrador.pi_application.models.User
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.Utils.validateEmail
import com.projetointegrador.pi_application.utils.Utils.validatePassword
import com.projetointegrador.pi_application.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.utils.extensions.toast
import com.projetointegrador.pi_application.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            mainLayout.setOnClickListener {
                activity?.hideSoftKeyboard()
                it.clearScreenFocus()
            }
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }
            textForgotPassword.setOnClickListener {
                //TODO("SEND A EMAIL")
            }
            textSignUp.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }
            buttonLogin.setOnClickListener {
                makeLogin()
            }
        }
    }

    private fun makeLogin() {
        with(binding) {
            if (!validateEmail(emailField.text.toString()) || !validatePassword(passwordField.text.toString())) {
                requireContext().toast(getString(R.string.review_credentials))
            } else {
                firebaseLogin(emailField.text.toString(), passwordField.text.toString())
            }
        }
    }

    private fun firebaseLogin(email: String, password: String) {
        with(binding) {
            progressGoToMap.visibility = View.VISIBLE
            viewModel.login(email, password).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        saveUserAndNavigate(response)
                    }
                    is FirebaseResponse.Failure -> {
                        showErrorMessage(response)
                    }
                }
                progressGoToMap.visibility = View.INVISIBLE
            }
        }
    }

    private fun saveUserAndNavigate(response: FirebaseResponse<User>) {
        response as FirebaseResponse.Success
        SessionManager.saveUserData(response.data.userId, response.data.email)
        requireContext().toast(getString(R.string.logged_message, response.data.email))
        navController.navigate(R.id.action_loginFragment_to_profileFragment)
    }

    private fun showErrorMessage(response: FirebaseResponse<User>) {
        response as FirebaseResponse.Failure
        Log.e("LoginError", response.errorMessage)
        requireContext().toast(response.errorMessage)
    }

}