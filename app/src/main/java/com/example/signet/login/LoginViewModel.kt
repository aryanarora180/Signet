package com.example.signet.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _firebaseAuth = Firebase.auth

    private val googleSignInSuccess: MutableLiveData<Boolean> = MutableLiveData()
    fun signInWithGoogle(task: Task<GoogleSignInAccount>): LiveData<Boolean> {
        googleSignInSuccess.value = null
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthWithGoogle(account)
            }
        } catch (e: ApiException) {
            googleSignInSuccess.value = false
        } finally {
            return googleSignInSuccess
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        _firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            googleSignInSuccess.value = it.isSuccessful
        }
    }

}