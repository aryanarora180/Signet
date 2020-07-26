package com.example.signet.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.signet.R
import com.example.signet.helper.LinkAdapter
import com.example.signet.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.get().load(GoogleSignIn.getLastSignedInAccount(this)?.photoUrl).into(profile_image)
        profile_image.setOnLongClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.sign_out_title))
                .setMessage(resources.getString(R.string.sign_out_body))
                .setNeutralButton(resources.getString(R.string.sign_out_negative)) { dialog, which ->
                    // Do nothing
                }
                .setPositiveButton(resources.getString(R.string.sign_out_positive)) { dialog, which ->
                    viewModel.signUserOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .show()
            return@setOnLongClickListener true
        }

        setLoading()
        viewModel.getSavedLinks().observe(this, Observer { links ->
            if (links != null) {
                setNotLoading()
                if (links.isEmpty()) {
                    setEmpty()
                } else {
                    setNotEmpty()
                    saved_links_recycler.adapter = LinkAdapter(links) { url ->
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browserIntent)
                    }
                }
            }
        })
    }

    private fun setEmpty() {
        saved_links_recycler.visibility = View.GONE
        no_links_empty.visibility = View.VISIBLE
    }

    private fun setNotEmpty() {
        saved_links_recycler.visibility = View.VISIBLE
        no_links_empty.visibility = View.GONE
    }

    private fun setLoading() {
        saved_links_progress.visibility = View.VISIBLE
        no_links_empty.visibility = View.GONE
        saved_links_recycler.visibility = View.GONE
    }

    private fun setNotLoading() {
        saved_links_progress.visibility = View.GONE
        no_links_empty.visibility = View.VISIBLE
        saved_links_recycler.visibility = View.VISIBLE
    }
}