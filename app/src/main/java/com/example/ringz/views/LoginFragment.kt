package com.example.ringz.views


import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() , View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var onboardingActivity: OnboardingActivity

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        onboardingActivity = (this.activity as OnboardingActivity?)!!
        auth = FirebaseAuth.getInstance()

        return view
    }

    override fun onStart() {
        super.onStart()

        login_register_button.setOnClickListener(this)
        confirm_login_button.setOnClickListener(this)
    }

    private fun signIn(email: String, password: String) {
        if (!validateLoginForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onboardingActivity) { task ->
                if (task.isSuccessful) {
                    val user : FirebaseUser? = auth.currentUser
                    onboardingActivity.updateUI(user)
                } else {
                    val e = task.exception
                    Log.e("login activity", "failed authentication", e)
                    Toast.makeText(onboardingActivity.baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    onboardingActivity.updateUI(null)
                }
            }
    }

    private fun validateLoginForm(): Boolean {
        var valid = true

        val email = login_email_field.text.toString()
        if (TextUtils.isEmpty(email)) {
            login_email_field.error = "Required."
            valid = false
        } else {
            login_email_field.error = null
        }

        val password = login_password_field.text.toString()
        if (TextUtils.isEmpty(password)) {
            login_password_field.error = "Required."
            valid = false
        } else {
            login_password_field.error = null
        }
        return valid
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_register_button -> onboardingActivity.onRegisterClick(v)
            R.id.confirm_login_button -> signIn(login_email_field.text.toString(), login_password_field.text.toString())
        }
    }

}

