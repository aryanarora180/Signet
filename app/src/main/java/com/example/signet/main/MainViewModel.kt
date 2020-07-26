package com.example.signet.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.signet.helper.Link
import com.example.signet.helper.LinksRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LinksRepository()

    private val _savedLinks: MutableLiveData<List<Link>> = MutableLiveData()
    fun getSavedLinks(): LiveData<List<Link>> {
        repository.getUserLinksReference()?.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                // Do nothing
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val linkList = mutableListOf<Link>()
                snapshot.children.forEach {
                    val link = it.getValue(Link::class.java)
                    if (link != null) {
                        linkList.add(link)
                    }
                }
                _savedLinks.value = linkList
            }
        })

        return _savedLinks
    }

    fun signUserOut() {
        Firebase.auth.signOut()
    }

}