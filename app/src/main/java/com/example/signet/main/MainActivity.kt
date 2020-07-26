package com.example.signet.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.signet.R
import com.example.signet.helper.LinkAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = LinkAdapter()
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saved_links_recycler.adapter = adapter

        viewModel.getSavedLinks().observe(this, Observer { links ->
            if(links.isNullOrEmpty()) {
                saved_links_recycler.visibility = View.GONE
                no_links_empty.visibility = View.VISIBLE
            } else {
                saved_links_recycler.visibility = View.VISIBLE
                no_links_empty.visibility = View.GONE
                adapter.data = links
            }
        })
    }
}