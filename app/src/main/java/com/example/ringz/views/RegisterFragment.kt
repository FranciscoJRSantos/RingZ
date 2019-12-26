package com.example.ringz.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater

import android.view.View
import com.example.ringz.R
import com.example.ringz.presenters.AuthenticationPresenter
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private var presenter: AuthenticationPresenter? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        presenter = AuthenticationPresenter(this)
        return view
    }

    fun initView() {
        create_account_button.setOnClickListener{ registerUser(view) }
    }

    private fun registerUser(view: View?) {
        return
    }
}
