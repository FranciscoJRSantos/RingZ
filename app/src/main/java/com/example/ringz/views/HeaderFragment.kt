package com.example.ringz.views


import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import com.example.ringz.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_header.*

/**
 * A simple [Fragment] subclass.
 */
class HeaderFragment : Fragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        mainActivity = this.activity as MainActivity

        return inflater.inflate(R.layout.fragment_header, container, false)
    }

    override fun onStart() {
        super.onStart()

        header_button.setOnClickListener(this)
    }

    private fun openDrawer() {
        mainActivity.drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.header_button -> openDrawer()
        }
    }


}
