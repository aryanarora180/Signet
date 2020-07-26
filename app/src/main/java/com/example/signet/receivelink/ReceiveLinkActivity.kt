package com.example.signet.receivelink

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.signet.R
import com.example.signet.helper.Link
import com.example.signet.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_receive_link.*
import java.util.*

class ReceiveLinkActivity : AppCompatActivity() {

    private val viewModel by viewModels<ReceiveLinkViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_link)

        if (viewModel.isUserSignedIn) {
            val intentText = intent.getStringExtra(Intent.EXTRA_TEXT)
            viewModel.getArticleDetails(intentText ?: "")

            viewModel.url.observe(this, Observer { url ->
                if (url != null) {
                    if (url.isNotEmpty())
                        link_url_text.text = url
                    else
                        showSnackbar(R.string.error_invalid_url)
                }
            })

            viewModel.metadata.observe(this, Observer { metadata ->
                if (metadata != null) {
                    setNotLoading()
                    link_date_text.text = viewModel.formatDate(Date().time)
                    Picasso.get().load(metadata.meta.image).into(link_featured_image)
                    link_title_text.text = metadata.meta.title

                    save_link_fab.setOnClickListener {
                        viewModel.saveUserLink(metadata).observe(this, Observer { saved ->
                            if(saved != null) {
                                if(saved)
                                    finish()
                                else
                                    showSnackbar(R.string.error_saving_link)
                            }
                        })
                    }
                }
            })

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setNotLoading() {
        link_loading_progress.visibility = View.GONE
        link_details_card.visibility = View.VISIBLE
        save_link_fab.visibility = View.VISIBLE
        save_link_fab.show()
    }

    private fun showSnackbar(stringId: Int) =
        Snackbar.make(receive_link_coordinator, stringId, Snackbar.LENGTH_SHORT).show()

}