package com.example.signet.receivelink

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.signet.network.Api
import com.example.signet.network.LinkMetaData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class ReceiveLinkViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentUser = Firebase.auth.currentUser
    val isUserSignedIn = _currentUser != null

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String>
        get() = _url

    private val _metadata: MutableLiveData<LinkMetaData> = MutableLiveData()
    val metadata: LiveData<LinkMetaData>
        get() = _metadata

    fun getArticleDetails(intentText: String) {
        val p = Pattern.compile(
            "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        ).matcher(intentText)
        if(p.find()) {
            val url = intentText.substring(p.start(0), p.end(0))
            _url.value = url
            getArticleMetadata(url)
        } else {
            _url.value = ""
            return
        }
    }

    private fun getArticleMetadata(url: String) {
        viewModelScope.launch {
            try {
                val response = Api.retrofitService.getMetaData(url)
                _metadata.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _metadata.postValue(null)
            }
        }
    }

    fun formatDate(dateToFormat: Long) = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(dateToFormat))

}