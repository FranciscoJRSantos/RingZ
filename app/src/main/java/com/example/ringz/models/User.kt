package com.example.ringz.models

import android.util.Log
import com.google.firebase.database.FirebaseDatabase



class User(uid: String, name: String, email: String, nickname: String) {
    var uid: String = uid
    var name: String? = null
    var email: String? = null
    var nickname: String? = null
    var houseId: String? = null
    var houseList: List<Home> = emptyList()

    init {
        this.name = name
        this.email = email
        this.nickname = nickname
    }

    constructor() : this("", "", "", "")

    fun save() {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.child(uid).setValue(this)
    }

    fun attachHouse(houseId: String) : User {

        this.houseId = houseId

        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.child(uid).setValue(this)
        return this
    }

    fun deleteUser(){
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.child(uid).setValue(null)
    }

    fun removeHouse() {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        this.houseId = null
        usersRef.child(uid).setValue(this)
    }

    fun addHouseToVisit(visitingHouse: Home){
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")


        this.houseList = this.houseList.plus(visitingHouse)
        usersRef.child(uid).child("houseList").setValue(this.houseList)
    }

    fun updateHouseList(newList: List<Home>){
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")
        this.houseList = newList
        usersRef.child(uid).child("houseList").setValue(this.houseList)
    }
}