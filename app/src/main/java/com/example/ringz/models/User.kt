package com.example.ringz.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class User(uid: String, name: String, email: String, nickname: String) {
    var uid: String
    var name: String? = null
    var email: String? = null
    var nickname: String? = null
    var houseId: Integer? = null

    init {
        this.uid = uid
        this.name = name
        this.email = email
        this.nickname = nickname
    }

    fun save() {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.child(uid).setValue(this)
    }
}