package com.example.signet.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.signet.LoginActivity
import com.example.signet.MainActivity
import com.example.signet.R
import com.example.signet.databinding.ActivityLoginBinding
import com.example.signet.databinding.SignInFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sign_in_fragment.*

class signInFragment : Fragment() {
    private val REQUEST_CODE_SIGN_IN: Int = 1
    private lateinit var _googleSignInClient: GoogleSignInClient
    private lateinit var _googleSignInOptions: GoogleSignInOptions

    private val viewModel by activityViewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("40056027635-kj0a59vseksq1q4sse48ao3jo9cgca9h.apps.googleusercontent.com")
            .requestEmail()
            .build()
        _googleSignInClient = GoogleSignIn.getClient(requireContext(), _googleSignInOptions)
        login_button.setOnClickListener {
            startActivityForResult(_googleSignInClient.signInIntent, REQUEST_CODE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            viewModel.signInWithGoogle(GoogleSignIn.getSignedInAccountFromIntent(data)).observe(viewLifecycleOwner, Observer { signedIn ->
                if(signedIn!=null) {
                    if (signedIn)
                    {
                        val intent = Intent(requireContext(),MainActivity::class.java)
                        startActivity(intent)
                    }

                    else{
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