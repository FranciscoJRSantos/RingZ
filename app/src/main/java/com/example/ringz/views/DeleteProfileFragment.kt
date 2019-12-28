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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.AuthCredential
import kotlinx.android.synthetic.main.fragment_delete_user.*


class DeleteProfileFragment : Fragment(), View.OnClickListener{
    private lateinit var mainActivity: MainActivity
    private lateinit var user: User

    override fun onStart() {
        super.onStart()

        confirm_delete_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.confirm_delete_button -> deleteAccount()
        }
    }

    private fun deleteAccount() {
        val current_user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(delete_email_field.text.toString(),delete_password_field.text.toString())
        current_user?.reauthenticate(credential)
        current_user!!.delete()
        user.deleteUser()
        mainActivity.logOut()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            mainActivity=this.activity as MainActivity
            user= mainActivity.user!!

            return inflater.inflate(R.layout.fragment_delete_user, container, false)
    }


}