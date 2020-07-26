package com.example.signet.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.signet.helper.Link
import com.example.signet.helper.LinksRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LinksRepository()

    private val _savedLinks: MutableLiveData<List<Link>> = MutableLiveData(listOf())
    fun getSavedLinks(): LiveData<List<Link>> {


        return _savedLinks
    }

}