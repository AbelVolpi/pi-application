package com.example.testingfirebase.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testingfirebase.R
import com.example.testingfirebase.databinding.FragmentRegisterBinding
import com.example.testingfirebase.utils.Utils.validateEmail
import com.example.testingfirebase.utils.Utils.validatePassword
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentRegisterBinding
    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        with(binding) {
            arrowBack.setOnClickListener {
                navController.popBackStack()
            }
            buttonSignUp.setOnClickListener {
                makeSignUp()
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

            firebaseAuth.createUserWithEmailAndPassword(email, password).apply {
                addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        val userEmail = firebaseUser?.email
                        Toast.makeText(requireContext(), "Sign Up completed as $userEmail", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                        Log.e("ERROR", task.exception?.message.toString())
                    }

                    progressBarSignUp.visibility = View.INVISIBLE
                }
            }
        }
    }
}