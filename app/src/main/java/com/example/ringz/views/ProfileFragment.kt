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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.AuthCredential

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity
    private lateinit var user: User

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        mainActivity=this.activity as MainActivity
        user= mainActivity.user!!

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        nickname_field.text=user.nickname
        name_field.text=user.name
        email_field.text=user.email

        edit_account_button.setOnClickListener(this)
        save_account_button.setOnClickListener(this)
        delete_account_button.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.edit_account_button -> onEditClick(v)
            R.id.save_account_button -> onSaveClick(v)
            R.id.delete_account_button ->onDeleteClick(v)
        }
    }

    private fun onDeleteClick(v: View) {
        mainActivity.renderFragment(DeleteProfileFragment())
    }

    private fun onEditClick(v: View) {
        nickname_switcher.showNext()
        name_switcher.showNext()
        edit_profile_button_switcher.showNext()

    }

    private fun onSaveClick(view: View){
        val name=edit_name_field.text.toString()
        val nickname=edit_nickname_field.text.toString()
        if(!TextUtils.isEmpty(name)){
            user.name=name
        }
        if(!TextUtils.isEmpty(nickname)){
            user.nickname=nickname
        }
        user.save()

        name_field.text=user.name
        nickname_field.text=user.nickname
        nickname_switcher.showPrevious()
        name_switcher.showPrevious()
        edit_profile_button_switcher.showPrevious()

    }


}
