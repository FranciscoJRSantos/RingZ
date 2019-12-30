package com.example.ringz.views


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ringz.R
import com.example.ringz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import android.content.SharedPreferences
import android.util.Log
import com.example.ringz.models.Home
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_add_homevisitor.*


class AddVisitingHomeFragment: Fragment(), View.OnClickListener {
    private lateinit var mainActivity : MainActivity
    private lateinit var database: FirebaseDatabase
    private lateinit var homeRef : DatabaseReference
    private lateinit var user: User
    private var home: Home? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val  view = inflater.inflate(R.layout.fragment_add_homevisitor, container, false)
        mainActivity = this.activity as MainActivity
        user = mainActivity.user!!
        database = FirebaseDatabase.getInstance()
        homeRef = database.getReference("homes")
        return view
    }

    override fun onStart() {
        super.onStart()


        add_visitHouse_button.setOnClickListener(this)
        cancel_button.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id) {
            R.id.add_visitHouse_button -> addVisitHouse(house_code_field.text.toString())
            R.id.cancel_button -> mainActivity.renderFragment(HomeVisitorListFragment())
        }
    }

    private fun addVisitHouse(houseCode: String) {
        this.homeRef = homeRef.child(houseCode)

        val homeListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Listener Cancelled", databaseError.message)
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot) {
                home = databaseSnapshot.getValue(Home::class.java)
                Log.d("status",home!!.name)
                user.addHouseToVisit(home!!)

                home!!.addVisitorToHouse(home!!.uuid, user.name.toString())
            }
        }

        homeRef.addListenerForSingleValueEvent(homeListener)
    }
}