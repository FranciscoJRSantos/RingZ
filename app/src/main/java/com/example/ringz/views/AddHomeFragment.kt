package com.example.ringz.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.example.ringz.models.Home
import kotlinx.android.synthetic.main.fragment_add_home.*

class AddHomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_add_home, container, false)

        mainActivity = this.activity as MainActivity

        return view
    }

    override fun onStart() {
        super.onStart()

        add_house_button.setOnClickListener(this)
        cancel_button.setOnClickListener(this)
    }

    fun createHouse(name : String) {
        var home : Home = Home(mainActivity.user?.uid!!, name)
        home.save()
        mainActivity.user!!.attachHouse(home.uuid)
        mainActivity.renderFragment(HomeListFragment())
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.add_house_button -> createHouse(house_name_field.text.toString())
            R.id.cancel_button -> mainActivity.renderFragment(ProfileFragment())
        }
    }


}
