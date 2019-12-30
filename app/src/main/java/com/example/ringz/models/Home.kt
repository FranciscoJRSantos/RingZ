package com.example.ringz.models

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class Home(uuid: String, name: String) {
    var uuid: String = uuid
    var name: String = name
    var openStatus: Boolean = true
    var visitorsNames: List<String> = emptyList()

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

    fun toggleState(isChecked : Boolean) : Home{
        val database = FirebaseDatabase.getInstance()
        val homesRef = database.getReference("homes")

        this.openStatus = isChecked
        homesRef.child(uuid).setValue(this)
        return this
    }

    fun addVisitorToHouse(visitingHouseUuid: String, visitingUser: String){
        val database = FirebaseDatabase.getInstance()
        val homeRef = database.getReference("homes")
        Log.e("uuid", homeRef.child(visitingHouseUuid).toString())


        this.visitorsNames = this.visitorsNames.plus(visitingUser)
        homeRef.child(visitingHouseUuid).child("visitorsNames").setValue(this.visitorsNames)
    }
}
