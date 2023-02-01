package com.projetointegrador.pi_application.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.databinding.FragmentSignUpBinding
import com.projetointegrador.pi_application.models.User
import com.projetointegrador.pi_application.presentation.viewmodel.SignUpViewModel
import com.projetointegrador.pi_application.utils.FirebaseResponse
import com.projetointegrador.pi_application.utils.SessionManager
import com.projetointegrador.pi_application.utils.Utils.validateEmail
import com.projetointegrador.pi_application.utils.Utils.validatePassword
import com.projetointegrador.pi_application.utils.extensions.clearScreenFocus
import com.projetointegrador.pi_application.utils.extensions.hideSoftKeyboard
import com.projetointegrador.pi_application.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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
            buttonSignUp.setOnClickListener {
                makeSignUp()
            }
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
                Toast.makeText(requireContext(), getString(R.string.review_credentials), Toast.LENGTH_SHORT).show()
            } else {
                firebaseSignUp(emailField.text.toString(), passwordField.text.toString())
            }
        }
    }

    private fun firebaseSignUp(email: String, password: String) {
        with(binding) {
            progressBarSignUp.visibility = View.VISIBLE
            viewModel.signUp(email, password).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        saveUserAndNavigate(response)
                        progressBarSignUp.visibility = View.INVISIBLE
                    }
                    is FirebaseResponse.Failure -> {
                        showErrorMessage(response)
                        progressBarSignUp.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun saveUserAndNavigate(response: FirebaseResponse<User>) {
        response as FirebaseResponse.Success
        SessionManager.saveUserData(response.data.userId, response.data.email)
        requireContext().toast(getString(R.string.sign_up_completed, response.data.email))
        navController.navigate(R.id.action_registerFragment_to_profileFragment)
    }

    private fun showErrorMessage(response: FirebaseResponse<User>) {
        response as FirebaseResponse.Failure
        Log.e("LoginError", response.errorMessage)
        requireContext().toast(response.errorMessage)
    }
}
