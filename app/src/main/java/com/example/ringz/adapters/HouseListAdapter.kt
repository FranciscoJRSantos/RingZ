package com.example.ringz.adapters

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ringz.R
import com.example.ringz.models.Home
import com.example.ringz.views.MainActivity

class HouseListAdapter(private val context: Activity, private val listOfNames: List<String>, private val homeList: List<String>, private var listOfHouses: List<Home>)
    : ArrayAdapter<String>(context, R.layout.custom_list, listOfNames) {

    private lateinit var mainActivity : MainActivity

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        mainActivity = context as MainActivity

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView

        titleText.text = listOfNames[position]

        val ringButton = rowView.findViewById(R.id.ring_button) as Button
        ringButton.setOnClickListener {
            context.ringBell(homeList[position])
        }

        val removeButton = rowView.findViewById(R.id.remove_house_button) as Button
        removeButton.setOnClickListener {
            val newList: List<Home> = listOfHouses.minusElement(listOfHouses[position])
            val mainActivity: MainActivity = this.context as MainActivity
            mainActivity.user!!.updateHouseList(newList)
        }

        return rowView
    }
}