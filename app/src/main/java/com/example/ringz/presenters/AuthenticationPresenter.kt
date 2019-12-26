package com.example.ringz.presenters

import androidx.fragment.app.Fragment
import com.example.ringz.models.User

class AuthenticationPresenter(_view: Fragment) {

    fun registerUser(email: String, name: String, nickname: String, password: String) {
        User(name, email, password, nickname).save()
    }
}