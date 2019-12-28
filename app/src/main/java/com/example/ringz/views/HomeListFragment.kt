package com.example.ringz.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ringz.R
import kotlinx.android.synthetic.main.fragment_home_list.*

class HomeListFragment : Fragment(), View.OnClickListener {
    private lateinit var mainActivity : MainActivity

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_home_list, container, false)

        mainActivity = this.activity as MainActivity

        return view
    }

    override fun onStart() {
        super.onStart()

        addHouse.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.addHouse -> mainActivity.renderFragment(AddHomeFragment())
        }

    }
}