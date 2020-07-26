package com.example.signet.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.signet.helper.Link
import com.example.signet.helper.LinksRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LinksRepository(application)

    private val _savedLinks: MutableLiveData<List<Link>> = MutableLiveData(listOf())
    fun getSavedLinks(): LiveData<List<Link>> {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("links").child(uid)

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val linkList = mutableListOf<Link>()
                snapshot.children.forEach {
                    val link = it.getValue(Link::class.java)
                    if (link != null) {
                        linkList.add(link)
                        Log.d("Link","fetching")
                    }
                }

                _savedLinks.value = linkList
            }
        })

        return _savedLinks
    }

}