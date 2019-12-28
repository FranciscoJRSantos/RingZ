package com.example.ringz.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.example.ringz.models.Home
import com.example.ringz.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    public lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef : DatabaseReference
    private lateinit var homeRef : DatabaseReference
    var user : User? = null
    var home : Home? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("users").child(auth.currentUser?.uid!!)
        homeRef = database.getReference("homes").child(auth.currentUser?.uid!!)

        nav_view.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle( this, drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()

        val userListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                user = null
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot) {
                user = databaseSnapshot.getValue(User::class.java)
            }
        }

        val homeListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot) {
                home = databaseSnapshot.getValue(Home::class.java)
            }
        }

        homeRef.addListenerForSingleValueEvent(homeListener)
        userRef.addListenerForSingleValueEvent(userListener)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                renderFragment(ProfileFragment())
            }
            R.id.nav_home -> {
                if (user?.houseId != null) {
                    renderFragment(HomeFragment())
                } else {
                    renderFragment(HomeListFragment())
                }
            }
            R.id.nav_logout -> logOut()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun logOut() {
        auth.signOut()
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
    }

    fun renderFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
