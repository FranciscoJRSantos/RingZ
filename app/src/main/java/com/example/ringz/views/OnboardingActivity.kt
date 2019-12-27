package com.example.ringz.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.ringz.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.fragment_login.*

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        auth = FirebaseAuth.getInstance()

        // Buttons
        register_button.setOnClickListener(this)
        login_button.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        val currentUser : FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun onRegisterClick(view: View?) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RegisterFragment()).commit()
    }

    fun onLoginClick(view: View?) {
        supportFragmentManager.beginTransaction().replace( R.id.fragment_container, LoginFragment()).commit()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.register_button -> onRegisterClick(v)
            R.id.login_button -> onLoginClick(v)
        }
    }
}

