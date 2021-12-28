package com.projetointegrador.pi_application.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.projetointegrador.pi_application.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.projetointegrador.pi_application.R
import com.projetointegrador.pi_application.utils.Utils.validateEmail
import com.projetointegrador.pi_application.utils.Utils.validatePassword

class LoginFragment : Fragment() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private val navController by lazy {
        findNavController()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun firebaseLogin(email: String, password: String) {
        with(binding) {
            progressGoToMap.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(email, password).apply {
                addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        val userEmail = firebaseUser?.email
                        Toast.makeText(requireContext(), getString(R.string.logged_message, userEmail), Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_loginFragment_to_profileFragment)

                    } else {
                        Toast.makeText(requireContext(), task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                        Log.e("ERROR", task.exception?.message.toString())
                    }
                    progressGoToMap.visibility = View.INVISIBLE
                }


//                addOnSuccessListener {
//                    val firebaseUser = firebaseAuth.currentUser
//                    val userEmail = firebaseUser?.email
//                    Toast.makeText(requireContext(), getString(R.string.logged_message, userEmail), Toast.LENGTH_SHORT).show()
//                    progressGoToMap.visibility = View.INVISIBLE
//                }
//                addOnFailureListener {
//                    Toast.makeText(requireContext(), getString(R.string.login_failed_message), Toast.LENGTH_SHORT).show()
//                    progressGoToMap.visibility = View.INVISIBLE
//                }
            }
        }
    }


    private fun initViews() {
        with(binding) {
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
//                navController.navigate(R.id.action_loginFragment_to_createCampaignFragment)
            }
        }
    }

    private fun makeLogin() {
        with(binding) {

            Log.e("email: ", emailField.text.toString())
            Log.e("password: ", passwordField.text.toString())

            if (!validateEmail(emailField.text.toString()) || !validatePassword(passwordField.text.toString())) {
                Toast.makeText(requireContext(), getString(R.string.review_credentials), Toast.LENGTH_SHORT).show()
            } else {
                firebaseLogin(emailField.text.toString(), passwordField.text.toString())
            }
        }
    }
}