package com.example.ringz.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater

import android.view.View
import android.widget.Toast
import com.example.ringz.R
import com.example.ringz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var onboardingActivity: OnboardingActivity
    private lateinit var usersRef : DatabaseReference

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        onboardingActivity = (this.activity as OnboardingActivity?)!!
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")

        return view
    }

    override fun onStart() {
        super.onStart()

        register_login_button.setOnClickListener(this)
        create_account_button.setOnClickListener(this)
    }


    private fun createAccount(email: String, password: String, username: String, nickname: String) {

        if (!validateRegisterForm()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password) .addOnCompleteListener(onboardingActivity) { task ->
                if (task.isSuccessful) {
                    val user : FirebaseUser? = auth.currentUser
                    onboardingActivity.updateUI(user)
                    if (user != null)
                        User(user.uid, username, email, nickname).save()
                } else {
                    Toast.makeText(onboardingActivity.baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
                    onboardingActivity.updateUI(null)
                }
            }
        return
    }

    private fun validateRegisterForm(): Boolean {
        var valid = true

        val email : String = register_email_field.text.toString()
        if (TextUtils.isEmpty(email)) {
            register_email_field.error = "Required."
            valid = false
        } else {
            register_email_field.error = null
        }

        val nickname: String = register_nickname_field.text.toString()
        if (TextUtils.isEmpty(nickname)) {
            register_nickname_field.error = "Required."
            valid = false
        } else {
            register_nickname_field.error = null
        }


        val password : String = register_password_field.text.toString()
        if (TextUtils.isEmpty(password)) {
            register_password_field.error = "Required."
            valid = false
        } else {
            register_password_field.error = null
        }

        val passwordConfirmation : String = register_confirm_password_field.text.toString()
        if (TextUtils.isEmpty(passwordConfirmation)) {
            register_confirm_password_field.error = "Required."
            valid = false
        } else {
            register_confirm_password_field.error = null
        }

        if(passwordConfirmation != password) {
            Toast.makeText(onboardingActivity.baseContext, "Passwords don't match.", Toast.LENGTH_SHORT).show()
            onboardingActivity.updateUI(null)
            valid = false
        }

        return valid
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.register_login_button -> onboardingActivity.onLoginClick(v)
            R.id.create_account_button -> createAccount(register_email_field.text.toString(),
                                                        register_password_field.text.toString(),
                                                        register_name_field.text.toString(),
                                                        register_nickname_field.text.toString())
        }
    }
}
