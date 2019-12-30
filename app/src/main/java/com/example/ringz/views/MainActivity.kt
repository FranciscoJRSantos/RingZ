package com.example.ringz.views

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.example.ringz.models.Home
import com.example.ringz.models.Notification
import com.example.ringz.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef : DatabaseReference
    private lateinit var homeRef : DatabaseReference
    private lateinit var notificationsRef: DatabaseReference
    private lateinit var firebaseMessaging : FirebaseMessaging
    var user : User? = null
    var home : Home? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        firebaseMessaging = FirebaseMessaging.getInstance()
        userRef = database.getReference("users").child(auth.currentUser?.uid!!)
        homeRef = database.getReference("homes").child(auth.currentUser?.uid!!)
        notificationsRef = database.getReference("notifications")

        val headerButton = nav_view.getHeaderView(0).findViewById<Button>(R.id.nav_header_button)
        nav_view.setNavigationItemSelectedListener(this)
        headerButton.setOnClickListener(this)
        nav_logout.setOnClickListener(this)

        val toggle = ActionBarDrawerToggle( this, drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()

        renderFragment(LoadingFragment())

        val userListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                user = null
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot) {
                user = databaseSnapshot.getValue(User::class.java)
                hasLoaded()
            }
        }

        val homeListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot) {
                home = databaseSnapshot.getValue(Home::class.java)
                firebaseMessaging.subscribeToTopic(home?.uuid.toString())
                hasLoaded()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.header_container, HeaderFragment()).commit()

        homeRef.addListenerForSingleValueEvent(homeListener)
        userRef.addListenerForSingleValueEvent(userListener)
    }

    override fun onDestroy() {
        super.onDestroy()

        firebaseMessaging.unsubscribeFromTopic(home?.uuid.toString())
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
            R.id.nav_visit -> renderFragment(HomeVisitorListFragment())
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

    override fun onClick(v: View) {
        when(v.id) {
            R.id.nav_logout -> logOut()
            R.id.nav_header_button -> drawer_layout.closeDrawer(Gravity.LEFT)
        }
    }

    fun hasLoaded() {
        if (user != null && user?.houseId != null) {
            if (home != null) {
                renderFragment(HomeVisitorListFragment())
            }
        }
        else if (user != null) {
            renderFragment(HomeVisitorListFragment())
        }
    }

    fun ringBell(houseID: String) {
        Toast.makeText(this, "BZZZZZZZZ", Toast.LENGTH_LONG).show()

        val notification = Notification(houseID, user?.nickname.toString(), " quer entrar na tua casa")
        notification.save()
    }
}
