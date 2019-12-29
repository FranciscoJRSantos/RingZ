package com.example.ringz.views


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.ringz.R
import com.example.ringz.models.Home
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var home: Home

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        mainActivity = this.activity as MainActivity
        home = mainActivity.home!!

        return view
    }

    override fun onStart() {
        super.onStart()

        val status = if (home.openStatus) "aberta" else "fechada"
        val statusText = "Neste momento, a sua casa encontra-se $status"

        status_text.text = statusText
    }


}
