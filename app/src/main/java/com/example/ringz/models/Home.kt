package com.example.ringz.models

import android.media.Ringtone
import android.media.RingtoneManager
import android.provider.Settings
import com.google.firebase.database.FirebaseDatabase

class Home(uuid: String, name: String) {
    var uuid: String = uuid
    var name: String? = null

    init {
        this.name = name
    }

    constructor() : this("", "")

    fun save() {
        val database = FirebaseDatabase.getInstance()
        val homesRef = database.getReference("homes")

        homesRef.child(uuid).setValue(this)
    }
}
