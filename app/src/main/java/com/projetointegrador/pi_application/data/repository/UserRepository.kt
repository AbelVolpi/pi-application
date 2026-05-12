package com.projetointegrador.pi_application.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.projetointegrador.pi_application.core.utils.SessionManager
import com.projetointegrador.pi_application.core.utils.TaskResponse
import com.projetointegrador.pi_application.domain.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(
    private val supabase: SupabaseClient,
    private val sessionManager: SessionManager
) {
    fun login(
        email: String,
        password: String
    ): LiveData<TaskResponse<User>> =
        liveData {
            try {
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                val user = supabase.auth.currentUserOrNull()
                emit(TaskResponse.Success(User(user?.email ?: "", user?.id ?: "")))
            } catch (e: Exception) {
                emit(TaskResponse.Failure(e.message ?: "Login failed"))
            }
        }

    fun signUp(
        email: String,
        password: String
    ): LiveData<TaskResponse<User>> =
        liveData {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                val user = supabase.auth.currentUserOrNull()
                emit(TaskResponse.Success(User(user?.email ?: "", user?.id ?: "")))
            } catch (e: Exception) {
                emit(TaskResponse.Failure(e.message ?: "Sign up failed"))
            }
        }

    fun forgotPassword(email: String): LiveData<TaskResponse<Any>> =
        liveData {
            try {
                supabase.auth.resetPasswordForEmail(email)
                emit(TaskResponse.Success(Any()))
            } catch (e: Exception) {
                emit(TaskResponse.Failure(e.message ?: "Password reset failed"))
            }
        }

    fun deleteUser(): LiveData<TaskResponse<Any>> =
        liveData {
            try {
                // Supabase does not support client-side account deletion with the anon key.
                // To fully delete the account, implement a Supabase Edge Function that uses
                // the service_role key to call supabase.auth.admin.deleteUser(userId).
                supabase.auth.signOut()
                emit(TaskResponse.Success(Any()))
            } catch (e: Exception) {
                emit(TaskResponse.Failure(e.message ?: "Delete account failed"))
            }
        }

    fun checkAlreadyLogged(): Boolean = supabase.auth.currentSessionOrNull() != null

    fun logOut() {
        sessionManager.logout()
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { supabase.auth.signOut() }
        }
    }
}
