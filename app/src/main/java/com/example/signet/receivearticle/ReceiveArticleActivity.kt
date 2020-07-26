package com.example.signet.receivearticle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.signet.LoginActivity
import com.example.signet.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_receive_article.*
import java.util.*

class ReceiveArticleActivity : AppCompatActivity() {

    private val viewModel by viewModels<ReceiveArticleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_article)

        if (viewModel.isUserSignedIn) {
            val intentText = intent.getStringExtra(Intent.EXTRA_TEXT)
            viewModel.getArticleDetails(intentText ?: "")

            viewModel.url.observe(this, Observer { url ->
                if(url != null) {
                    if(url.isNotEmpty())
                        article_url_text.text = url
                    else
                        showSnackbar(R.string.error_invalid_url)
                }
            })

            viewModel.metadata.observe(this, Observer { metadata ->
                if (metadata != null) {
                    setNotLoading()
                    article_date_text.text = viewModel.formatDate(Date().time)
                    Picasso.get().load(metadata.meta.image).into(article_featured_image)
                    article_title_text.text = metadata.meta.title
                }
            })

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setNotLoading() {
        article_loading_progress.visibility = View.GONE
        article_details_card.visibility = View.VISIBLE
        save_article_fab.visibility = View.VISIBLE
        save_article_fab.show()
    }

    private fun showSnackbar(stringId: Int) = Snackbar.make(receive_article_coordinator, stringId, Snackbar.LENGTH_SHORT).show()

}