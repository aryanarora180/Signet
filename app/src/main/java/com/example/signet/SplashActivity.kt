package com.example.signet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.signet.login.LoginActivity
import com.example.signet.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser!=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}