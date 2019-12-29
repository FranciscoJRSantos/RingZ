package com.example.ringz.models

import com.google.firebase.database.FirebaseDatabase

class Home(uuid: String, name: String) {
    var uuid: String = uuid
    var name: String = name
    var openStatus: Boolean = true

    constructor() : this("", "")

    fun save() {
        val database = FirebaseDatabase.getInstance()
        val homesRef = database.getReference("homes")

        homesRef.child(uuid).setValue(this)
    }

    fun delete() {
        val database = FirebaseDatabase.getInstance()
        val homesRef = database.getReference("homes")

        homesRef.child(uuid).setValue(null)
    }

    fun toggleState(isChecked : Boolean) {
        val database = FirebaseDatabase.getInstance()
        val homesRef = database.getReference("homes")

        this.openStatus = isChecked
        homesRef.child(uuid).setValue(this)
    }
}
