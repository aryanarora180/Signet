package com.example.signet.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.signet.R
import com.example.signet.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val REQUEST_CODE_SIGN_IN: Int = 1
    private lateinit var _googleSignInClient: GoogleSignInClient
    private lateinit var _googleSignInOptions: GoogleSignInOptions

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Firebase.database.setPersistenceEnabled(true)

        _googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        _googleSignInClient = GoogleSignIn.getClient(this, _googleSignInOptions)
        login_button.setOnClickListener {
            startActivityForResult(_googleSignInClient.signInIntent, REQUEST_CODE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            viewModel.signInWithGoogle(GoogleSignIn.getSignedInAccountFromIntent(data))
                .observe(this, Observer { signedIn ->
                    if (signedIn != null) {
                        if (signedIn) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Snackbar.make(
                                login_coordinator,
                                getString(R.string.sign_in_failed),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        }
    }
}