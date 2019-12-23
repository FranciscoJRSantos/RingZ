package com.example.ringz.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ringz.R

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }

    fun onRegisterClick(view: View?) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RegisterFragment()).commit()
    }

    fun onLoginClick(view: View?) {
        supportFragmentManager.beginTransaction().replace( R.id.fragment_container, LoginFragment()).commit()
    }
}
