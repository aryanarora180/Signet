package com.example.signet.helper

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LinksRepository {

    private val LINKS_PATH = "links"

    private val uid = Firebase.auth.currentUser?.uid

    fun getUserLinksReference(): DatabaseReference? {
        return if(uid != null)
            Firebase.database.reference.child(LINKS_PATH).child(uid)
        else null
    }

}