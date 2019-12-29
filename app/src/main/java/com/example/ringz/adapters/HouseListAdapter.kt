package com.example.ringz.adapters

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ringz.R
import com.example.ringz.views.MainActivity

class HouseListAdapter(private val context: Activity, private val title: List<String>, private val description: List<Boolean>, private val homeList: List<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, title) {

    private lateinit var mainActivity : MainActivity

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        mainActivity = context as MainActivity

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]
        subtitleText.text = description[position].toString()

        val ringButton = rowView.findViewById(R.id.ring_button) as Button
        ringButton.setOnClickListener {
            context.ringBell(homeList[position])
        }

        return rowView
    }
}