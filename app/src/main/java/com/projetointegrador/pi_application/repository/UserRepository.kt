package com.projetointegrador.pi_application.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.projetointegrador.pi_application.models.User
import com.projetointegrador.pi_application.utils.FirebaseResponse

class UserRepository {

    private lateinit var firebaseAuth: FirebaseAuth

    fun login(email: String, password: String): MutableLiveData<FirebaseResponse<User>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<User>>()

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithEmailAndPassword(email, password).apply {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val userEmail = firebaseUser?.email ?: ""
                    val userId = firebaseUser?.uid ?: ""

                    mutableLiveData.value = FirebaseResponse.Success(User(userEmail, userId))

                } else {
                    mutableLiveData.value = FirebaseResponse.Failure(task.exception?.message.toString())
                }
            }
        }
        return mutableLiveData
    }

    fun signUp(email: String, password: String): MutableLiveData<FirebaseResponse<User>> {
        val mutableLiveData = MutableLiveData<FirebaseResponse<User>>()

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password).apply {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val userEmail = firebaseUser?.email ?: ""
                    val userId = firebaseUser?.uid ?: ""

                    mutableLiveData.value = FirebaseResponse.Success(User(userEmail, userId))

                } else {
                    mutableLiveData.value = FirebaseResponse.Failure(task.exception?.message.toString())
                }
            }
        }
        return mutableLiveData
    }

    fun checkAlreadyLogged(): Boolean {
        firebaseAuth = FirebaseAuth.getInstance()

        return firebaseAuth.currentUser != null
    }

    fun logOut() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }

}



