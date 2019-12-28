package com.example.ringz.views


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.example.ringz.models.Home
import kotlinx.android.synthetic.main.fragment_home.*





/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity
    private var home: Home? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        mainActivity = this.activity as MainActivity
        home = mainActivity.home

        return view
    }

    override fun onStart() {
        super.onStart()

        house_name.text = home?.name
        house_name_field.text = Editable.Factory.getInstance().newEditable(home?.name);
        house_code.text = home?.uuid

        edit_name_button.setOnClickListener(this)
        save_name_button.setOnClickListener(this)
    }

    private fun onHouseEditClick(view : View) {

        home?.name = house_name_field.text.toString()
        home?.save()

        house_name.text = home?.name
        house_name_field.text = Editable.Factory.getInstance().newEditable(home?.name);

        home_switcher.showPrevious()
        button_switcher.showPrevious()
        dismissKeyboard()
    }

    private fun onHouseNameClick(view : View) {
        home_switcher.showNext()
        button_switcher.showNext()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.edit_name_button -> onHouseNameClick(v)
            R.id.save_name_button -> onHouseEditClick(v)
        }
    }

    private fun dismissKeyboard() {
        val view: View? = mainActivity.currentFocus
        if (view != null) {
            val inputManager: InputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}
