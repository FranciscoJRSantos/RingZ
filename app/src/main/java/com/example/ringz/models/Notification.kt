package com.example.ringz.models

import com.google.firebase.database.FirebaseDatabase

class Notification(private val uid: String, val title: String, val body: String) {

    constructor() : this("", "", "")

    fun save() {
        val database = FirebaseDatabase.getInstance()
        val homesRef = database.getReference("notifications")

        homesRef.child(uid).setValue(this)

    }
}